
CREATE DATABASE db_contatos;

USE db_contatos;

CREATE TABLE tbl_contatos (
	id int NOT NULL auto_increment,
    nome varchar (45) NOT NULL,
    telefone long,
    email varchar (45) NOT NULL,
    favorito boolean,
    foto varchar (200) NOT NULL,
    primary key (id)
);


 INSERT INTO tbl_contatos(id, nome, telefone, email, favorito, foto)values
 (0,"Maria da Silva",11970707070, "maria@teste.com", false,"https://images.pexels.com/photos/4006576/pexels-photo-4006576.jpeg?auto=compress&cs=tinysrgb&w=1600"),
 (0, "Jose Maria", 11950505050,  "jose@teste.com", false,  "https://images.pexels.com/photos/7533347/pexels-photo-7533347.jpeg?auto=compress&cs=tinysrgb&w=1600"),
 (0, "Maria Jose", 11920202020, "maria@teste.com", false, "https://images.pexels.com/photos/1848565/pexels-photo-1848565.jpeg?auto=compress&cs=tinysrgb&w=1600");


Select * from tbl_contatos;
