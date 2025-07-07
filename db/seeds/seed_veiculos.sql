SET client_encoding = 'UTF8';

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE tipo_veiculo RESTART IDENTITY CASCADE;

INSERT INTO tipo_veiculo (id, tipo) VALUES
(1, 'Carro'),
(2, 'Carro'),
(3, 'Carro'),
(4, 'Moto'),
(5, 'Moto');

SELECT setval('tipo_veiculo_id_seq', (SELECT MAX(id) FROM tipo_veiculo));

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE carro RESTART IDENTITY CASCADE;

INSERT INTO carro (id, categoria, motorizacao, potencia, transmissao) VALUES
(1, 'Carro de luxo', 'Híbrido PHEV', '160', 'Automático'),
(2, 'Sedan', 'Combustão - flex', '120', 'Automático'),
(3, 'Hatch', 'Combustão - gasolina', '80', 'Manual');


-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE moto RESTART IDENTITY CASCADE;

INSERT INTO moto (id, categoria, cilindrada, qtd_marcha, tipo_motor, tipo_partida) VALUES
(4, 'Lazer', 150, 3, '4 tempos', 'pedal'),
(5, 'Off-road', 180, 4, 'Elétrico de corrente contínua', 'Elétrica');


-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE fabricante RESTART IDENTITY CASCADE;

INSERT INTO fabricante (id, nome) VALUES
(1, 'Fiat'),
(2, 'BMW'),
(3, 'Volkswagen'),
(4, 'Honda');

SELECT setval('fabricante_id_seq', (SELECT MAX(id) FROM fabricante));

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE modelo RESTART IDENTITY CASCADE;

INSERT INTO modelo (id, nome, fabricante_id) VALUES
(1, 'CG 160 Titan', 4),
(2, 'Virtus', 3),
(3, 'X6', 2),
(4, 'Civic', 4),
(5, 'PCX', 4);

SELECT setval('modelo_id_seq', (SELECT MAX(id) FROM modelo));

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE modelo_veiculo RESTART IDENTITY CASCADE;

INSERT INTO modelo_veiculo (id, ano_fabricacao, foto_modelo, permite_test_drive, preco_base, qtd_estoque, qtd_estoque_venda, modelo_id, tipo_veiculo_id) VALUES
(1, '2024', NULL, 'Sim', 250.00, 3, 2, 3, 3),
(2, '2021', NULL, 'Sim', 180.00, 1, 1, 4, 1),
(3, '2015', NULL, 'Sim', 130.00, 3, 1, 2, 2),
(4, '2014', NULL, 'Não', 90.00, 1, 1, 1, 4),
(5, '2015', NULL, 'Não', 100.00, 2, 1, 5, 4);

SELECT setval('modelo_veiculo_id_seq', (SELECT MAX(id) FROM modelo_veiculo));

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE acessorio RESTART IDENTITY CASCADE;

INSERT INTO acessorio (id, descricao) VALUES
(1, 'Ar-condicionado'),
(2, 'Sistema de som multimídia'),
(3, 'Direção hidráulica'),
(4, 'Computador de bordo'),
(5, 'Travas elétricas'),
(6, 'Vidros elétricos');

SELECT setval('acessorio_id_seq', (SELECT MAX(id) FROM acessorio));

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE veiculo RESTART IDENTITY CASCADE;

INSERT INTO veiculo (id, chassi, condicao, cor, fotos, garantia, preco_atual, vendido, concessionaria_id, modelo_veiculo_id) VALUES
(1, '12345678910', 'Novo', 'Branco', NULL, 'Em vigência', 120.00, false, 7, 3),
(2, '1112131718', 'Novo', 'Preto', NULL, 'Garantia de fábrica', 150.00, false, 8, 2),
(3, '1920212223', 'Usado', 'Vermelho', NULL, 'Expirada', 90.00, false, 5, 5),
(4, '2425262830', 'Usado', 'Preto', NULL, 'Expirada', 110.00, false, 6, 4),
(5, '3132343536', 'Novo', 'Preto', NULL, 'Garantia de fábrica', 300.00, false, 6, 1),
(6, '3738394041', 'Novo', 'Preto', NULL, 'Em vigência', 70.00, false, 8, 2),
(7, '4243444546', 'Novo', 'Vermelho', NULL, 'Garantia de concessionária', 100.00, true, 5, 3);

SELECT setval('veiculo_id_seq', (SELECT MAX(id) FROM veiculo));

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE veiculo_usado RESTART IDENTITY CASCADE;

INSERT INTO veiculo_usado (id, contato_proprietario, data_ultima_revisao, documentacao, laudo_vistoria, manutencao, quilometragem, sinistro_acidente) VALUES
(3, '32998234556', '2024-06-04', 'Regular', '100% Aprovado', 'Em dia', 85, 'Acidente leve (Pequenos danos, como arranhões ou amassados)'),
(4, '32998236784', '2025-01-20', 'Aguardando renovação', 'Aprovado com Observações', 'Manutenção corretiva realizada (conserto de algum defeito)', 120, 'Acidente moderado (Danos maiores, mas sem comprometimento estrutural)');


-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE veiculo_novo RESTART IDENTITY CASCADE;

INSERT INTO veiculo_novo (id) VALUES
(1),
(2),
(5),
(6),
(7);

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE veiculo_tem_acessorio RESTART IDENTITY CASCADE;

INSERT INTO veiculo_tem_acessorio (id, acessorio_id, veiculo_id) VALUES
(1,1,1),
(2,2,1),
(3,3,1),
(4,2,2),
(5,3,2),
(6,4,3);

SELECT setval('veiculo_tem_acessorio_id_seq', (SELECT MAX(id) FROM veiculo_tem_acessorio));