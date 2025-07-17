SELECT setval('public.adm_empresa_gerencia_concessionaria_id_seq', GREATEST((SELECT MAX(id) FROM public.adm_empresa_gerencia_concessionaria), 0) + 1, false);
SELECT setval('public.agendamento_test_drive_id_seq', GREATEST((SELECT MAX(id) FROM public.agendamento_test_drive), 0) + 1, false);
SELECT setval('public.gestor_concessionaria_id_seq', GREATEST((SELECT MAX(id) FROM public.gestor_concessionaria), 0) + 1, false);
SELECT setval('public.itens_venda_id_seq', GREATEST((SELECT MAX(id) FROM public.itens_venda), 0) + 1, false);
SELECT setval('public.fabricante_id_seq', GREATEST((SELECT MAX(id) FROM public.fabricante), 0) + 1, false);
SELECT setval('public.acessorio_id_seq', GREATEST((SELECT MAX(id) FROM public.acessorio), 0) + 1, false);
SELECT setval('public.modelo_id_seq', GREATEST((SELECT MAX(id) FROM public.modelo), 0) + 1, false);
SELECT setval('public.pessoa_id_seq', GREATEST((SELECT MAX(id) FROM public.pessoa), 0) + 1, false);
SELECT setval('public.modelo_veiculo_id_seq', GREATEST((SELECT MAX(id) FROM public.modelo_veiculo), 0) + 1, false);
SELECT setval('public.venda_id_seq', GREATEST((SELECT MAX(id) FROM public.venda), 0) + 1, false);
SELECT setval('public.veiculo_tem_acessorio_id_seq', GREATEST((SELECT MAX(id) FROM public.veiculo_tem_acessorio), 0) + 1, false);
SELECT setval('public.veiculo_id_seq', GREATEST((SELECT MAX(id) FROM public.veiculo), 0) + 1, false);
SELECT setval('public.tipo_veiculo_id_seq', GREATEST((SELECT MAX(id) FROM public.tipo_veiculo), 0) + 1, false);

