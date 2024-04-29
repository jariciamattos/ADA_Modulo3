insert into usuario (account_expired, account_locked, active, credentials_expired, password, roles, username) VALUES
(false,false,true,false,'$2a$10$tuN1t648KML2o7652M5iLeFEfmqB1njHAxGKx3e/pCB6xbQk87aRu','ADMIN','admin');

insert into usuario (account_expired, account_locked, active, credentials_expired, password, roles, username) VALUES
(false,false,true,false,'$2a$10$8DNw3PGbMx/2Z1142hVux.klV2VTgICiExKnrqKFKb9jjr2kaCEtC','FUNCIONARIO','func');

insert into usuario (account_expired, account_locked, active, credentials_expired, password, roles, username) VALUES
(false,false,true,false,'$2a$10$H92YvSb9/DS0XJDym6UslO8OEZPLD0.AmHJUonfCLG/d.Bq33VQp2','CLIENTE','jaricia');

insert into cliente (cpf, data_nascimento, nome, status, id) values ('01494086760', '1980-04-05', 'Jaricia Lucena', 'ATIVO', 3);

insert into conta(saldo, cliente_id) values (300, 3);