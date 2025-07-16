import os
import pandas as pd
from sqlalchemy import create_engine, inspect
import networkx as nx

# --- Configurações do Banco de Dados ---
usuario = 'postgres'
senha = '123'
host = 'localhost'
porta = '5432'
banco = 'concessweb'

# --- Conexão com o Banco de Dados ---
try:
    engine = create_engine(f'postgresql+psycopg2://{usuario}:{senha}@{host}:{porta}/{banco}')
    print("Conexão com o banco de dados estabelecida com sucesso!")
except Exception as e:
    print(f"Erro ao conectar ao banco de dados: {e}")
    exit()

# --- Obter Nomes das Tabelas ---
query_get_tables = """
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
"""
df_tables = pd.read_sql(query_get_tables, engine)

# --- Verificar Tabelas com Dados Ausentes ---
tables_missing_data = []
print("Verificando quais tabelas possuem dados...")
for table in df_tables['table_name'].tolist():
    df_result = pd.read_sql(f'SELECT EXISTS (SELECT 1 FROM {table} LIMIT 1) AS existe', engine)
    if df_result['existe'][0]:
        print(f'{table} tem dados.')
    else:
        tables_missing_data.append(table)

print("\n--- Resumo de Tabelas ---")
for table in tables_missing_data:
    print(f'{table} não tem dados.')
if not tables_missing_data:
    print("Todas as tabelas possuem dados.")

# --- Obter Chaves Estrangeiras para Determinar a Ordem de Inserção ---
inspector = inspect(engine)
foreign_keys = {}
for table_name in inspector.get_table_names():
    fks = inspector.get_foreign_keys(table_name)
    foreign_keys[table_name] = [fk['referred_table'] for fk in fks]

# print("\n--- Chaves Estrangeiras Encontradas ---")
# print(foreign_keys)

# --- Criar Grafo de Dependências ---
G = nx.DiGraph()
for table, deps in foreign_keys.items():
    for dep in deps:
        G.add_edge(dep, table)

# --- Determinar Ordem Topológica para Inserção ---
try:
    insertion_order = list(nx.topological_sort(G))
    # Filtrar apenas as tabelas que estão faltando dados
    insertion_order = [table for table in insertion_order if table in tables_missing_data]

    print("\n--- Ordem sugerida para inserção de dados (considerando tabelas vazias) ---")
    if insertion_order:
        for i, table in enumerate(insertion_order, 1):
            print(f"{i:02d}: {table}")
    else:
        print("Nenhuma tabela com dados ausentes para sugerir ordem de inserção.")

except nx.NetworkXUnfeasible:
    print("\nErro: O grafo de dependências contém um ciclo. Não é possível determinar uma ordem topológica para inserção.")
except Exception as e:
    print(f"\nErro ao determinar a ordem de inserção: {e}")

# --- Inserir Dados nas Tabelas na Ordem Sugerida ---
print("\n--- Iniciando Inserção de Dados ---")
for table in insertion_order:
    csv_file = os.path.join('.', f'{table}.csv')

    if not os.path.isfile(csv_file):
        print(f"Arquivo para tabela '{table}' não encontrado ({csv_file}), pulando.")
        continue

    print(f"Lendo arquivo {csv_file} e inserindo na tabela {table}...")

    try:
        df = pd.read_csv(csv_file)
        df.to_sql(table, engine, if_exists='append', index=False)
        print(f"Dados inseridos na tabela {table} com sucesso.")
    except Exception as e:
        print(f"Erro ao inserir dados na tabela {table}: {e}")

print("\nProcesso de inserção de dados concluído.")
