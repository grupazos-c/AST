drop table if exists Autores;
drop table if exists Tags;
drop table if exists Posts;

create table Autores
    (
      Autor varchar(51) not NULL,
      Pwd varchar(255) not NULL,
      constraint primary key (Autor)
    );

create table Posts
    (
      Post_ID int AUTO_INCREMENT,
      Post varchar(281) not NULL,
      Autor varchar(51) not NULL,
      constraint foreign key (Autor) references Autores(Autor) ON DELETE CASCADE,
      constraint primary key (Post_ID)
    );

create table Tags
    (
      Post_ID int,
      Tag varchar(101),
      constraint foreign key (Post_ID) references Posts(Post_ID) ON DELETE CASCADE,
      constraint primary key (Post_ID, Tag)
    );


insert into Autores values ('Admins', 'A8E84C61-2AFA');
insert into Posts values ('1', 'Bienvenido al Foro de VigoCoffeeLovers','Admins');
insert into Tags values ('1', 'Bienvenido');
insert into Tags values ('1', 'HolaMundo');
insert into Tags values ('1', 'TextoDeEjemplo');
insert into Tags values ('1', 'NoMatlabAllowed');
