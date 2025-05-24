SET client_encoding = 'UTF8';

-- Limpa a tabela e reinicia o ID serial
TRUNCATE TABLE veiculo RESTART IDENTITY CASCADE;

-- Insere os dados atualizados
INSERT INTO veiculo (
    chassi,
    condicao,
    cor,
    fotos,
    garantia,
    preco_atual,
    vendido,
    concessionaria_id,
    modelo_veiculo_id
) VALUES
('12345678910', 'Novo', 'Branco', NULL, 'Em vigência', 120.00, FALSE, NULL, NULL),
('1112131718', 'Novo', 'Preto', NULL, 'Garantia de fábrica', 150.00, FALSE, NULL, NULL),
('1920212223', 'Usado', 'Vermelho', NULL, 'Expirada', 90.00, FALSE, NULL, NULL),
('2425262830', 'Usado', 'Preto', NULL, 'Expirada', 110.00, FALSE, NULL, NULL),
('3132343536', 'Novo', 'Branco', NULL, 'Garantia de fábrica', 140.00, TRUE, NULL, NULL),
('3738394041', 'Novo', 'Preto', NULL, 'Em vigência', 70.00, FALSE, NULL, NULL),
('4243444546', 'Novo', 'Vermelho', NULL, 'Garantia de concessionária', 100.00, TRUE, NULL, NULL);
