insert into sample_person(id,first_name,last_name,occupation,role) values (1,'Eula','Lane','Insurance Clerk','Worker')
insert into sample_person(id,first_name,last_name,occupation,role) values (2,'Barry','Rodriquez','Mortarman','Manager')
insert into sample_person(id,first_name,last_name,occupation,role) values (3,'Eugenia','Selvi','Beer Coil Cleaner','External')
insert into sample_person(id,first_name,last_name,occupation,role) values (4,'Alejandro','Miles','Scale Attendant','Worker')
insert into sample_person(id,first_name,last_name,occupation,role) values (5,'Cora','Tesi','Clinical Audiologist','Supervisor')

insert into application_user (id, username,name,hashed_password) values ('1','user','John Normal','$2a$10$xdbKoM48VySZqVSU/cSlVeJn0Z04XCZ7KZBjUBC00eKo5uLswyOpe')
insert into user_roles (user_id, roles) values ('1', 'COMUM')
insert into application_user (id, username, name, hashed_password) values ('2','admin','Emma Powerful','$2a$10$jpLNVNeA7Ar/ZQ2DKbKCm.MuT2ESe.Qop96jipKMq7RaUgCoQedV.')
insert into user_roles (user_id, roles) values ('2', 'COMUM')
insert into user_roles (user_id, roles) values ('2', 'ADMINISTRADOR')

-- Inserindo os equipamentos
INSERT INTO equipamentos (id, nome, imagem, descricao, ativo, data_equipamento) VALUES (1,'Torno Mecânico 500mm Modelo BV20L 220V - TTM520 - Tander', 'Torno_Mecanico_500mm.png', 'O Torno Mecânico Tander TTM520 é uma ferramenta utilizada por vários profissionais na confecção e acabamento de inúmeras peças metálicas, tais como: eixos, polias, pinos, roscas, peças cilíndricas internas e externas, cones, esferas, entre outros.', true, '2019-10-01 14:54:20.873')
INSERT INTO equipamentos (id, nome, imagem, descricao, ativo, data_equipamento) VALUES (2,'Processador Intel Core i9-7920X Skylake, Cache 16.5MB, 2.9GHz (4.3GHz Max Turbo), LGA 2066 - BX80673I97920X', 'Intel_Core_i9.png', 'Com esse processador inovador e incrível você desfruta ao máximo o verdadeiro potencial do seu computador e desfruta da mais pura velocidade. Maximize o seu desempenho seja trabalhando, jogando', true, '2019-10-01 15:00:20.873')
INSERT INTO equipamentos (id, nome, imagem, descricao, ativo, data_equipamento) VALUES (3,'Monitor, Dell, U2518D, UltraSharp, Preto e Suporte em Alumínio, 25', 'Monitor_Dell.png', 'Dê vida ao seu trabalho com uma tela de 25 polegadas quase sem bordas que conta com detalhes em cores vívidas.', false, '2018-10-01 10:00:20.000')
INSERT INTO equipamentos (id, nome, imagem, descricao, ativo, data_equipamento) VALUES (4,'Mouse Gamer Razer Deathadder Essential Óptico 5 Botões 4G 6.400 DPI', 'Mouse_Razer.png', 'Nada melhor do que um mouse gamer com tecnologia de ponta para qualificar seus comandos e aprimorar suas jogadas nos games.', true, '2017-10-01 09:00:20.000')
INSERT INTO equipamentos (id, nome, imagem, descricao, ativo, data_equipamento) VALUES (5,'All-in-One Media Keyboard', 'Teclado_Microsoft.png', 'O All-in-One Media Keyboard é o dispositivo ideal para sua sala ou home office. Com teclado em tamanho natural e trackpad multitoque integrado, é possível digitar, passar o dedo, arrastar, fazer zoom e clicar facilmente.', false, '2017-10-01 13:00:00.000')

--Inserindo os comentários
INSERT INTO comentarios (id,comentario, id_equipamento, id_perfil_usuario, data_comentario) VALUES (1,'Deverá fazer o download do aplicativo da razer para alterar a cor do mouse.', 4, 2, '2020-09-07 18:00:00.000')
INSERT INTO comentarios (id,comentario, id_equipamento, id_perfil_usuario, data_comentario) VALUES (2,'Problema de aquecimento no processador após 1 ano de uso', 2, 2, '2020-05-04 07:30:00.000')