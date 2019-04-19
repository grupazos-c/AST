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

insert into Autores values ('Pablo', '1234');
insert into Posts values ('2', 'Mirad mi nuevo Sticker','Pablo');
insert into Tags values ('2', 'Sticker');
insert into Tags values ('2', 'WhatsApp');
insert into Posts values ('3', 'Que bueno es este foro por dios','Pablo');
insert into Tags values ('3', ':)');

insert into Autores values ('sam', '1234');
insert into Posts values ('4', 'Por lo menos no está programado en MATLAB....','sam');
insert into Tags values ('4', 'NoMatlabAllowed');
insert into Tags values ('4', 'Funny');
insert into Posts values ('5', 'Los ingenieros de Discord utilizan el foro de VigoCoffeeLovers','sam');
insert into Tags values ('5', 'Hashtag');
insert into Tags values ('5', 'Tecnología');

insert into Autores values ('Roi', '1234');
insert into Posts values ('6', 'Limonada','Roi');
insert into Tags values ('6', 'Limonada');


insert into Autores values ('Proverbio', '1234');
insert into Posts values ('7', 'El que busca un amigo sin defectos se queda sin amigos.','Proverbio');
insert into Tags values ('7', 'Proverbio');
insert into Posts values ('8', 'Lo pasado ha huido, lo que esperas está ausente, pero el presente es tuyo.','Proverbio');
insert into Tags values ('8', 'Proverbio');
insert into Posts values ('9', 'La gente se arregla todos los días el cabello. ¿Por qué no el corazón?','Proverbio');
insert into Tags values ('9', 'Proverbio');
insert into Posts values ('10', 'Si lo que vas a decir no es más bello que el silencio: no lo digas.','Proverbio');
insert into Tags values ('10', 'Proverbio');
insert into Posts values ('11', 'La primera vez que me engañes, será culpa tuya; la segunda vez, la culpa será mía.','Proverbio');
insert into Tags values ('11', 'Proverbio');
insert into Autores values ('MR', '1234');
insert into Posts values ('12', 'Muchas tardes y buenas gracias','MR');
insert into Tags values ('12', 'Proverbio');
insert into Posts values ('13', 'Muy españoles, mucho españoles','MR');
insert into Tags values ('13', 'Proverbio');
insert into Posts values ('14', 'Son emprendedores, hacen cosas','MR');
insert into Tags values ('14', 'Proverbio');
insert into Posts values ('15', 'Por que un plato, es un plato y un vaso, un vaso','MR');
insert into Tags values ('15', 'Proverbio');
insert into Posts values ('16', 'Es el alcalde el que quiere que los vecinos sean el alcalde y los vecinos los que quieren que el alcalde sea el alcalde','MR');
insert into Tags values ('16', 'Proverbio');
insert into Posts values ('17', 'Esto no es como el agua que cae del cielo sin que se sepa exactamente porqué','MR');
insert into Tags values ('17', 'Proverbio');
insert into Posts values ('18', 'Is very dificult todo esto','MR');
insert into Tags values ('18', 'Proverbio');
insert into Posts values ('19', 'España es un gran país y los españoles muy españoles y mucho español','MR');
insert into Tags values ('19', 'Proverbio');
insert into Posts values ('20', 'Un libro abierto es un cerebro que habla; cerrado un amigo que espera; olvidado, un alma que perdona; destruido, un corazón que llora.','Proverbio');
insert into Tags values ('20', 'Proverbio');
insert into Posts values ('21', 'Quien no comprende una mirada tampoco comprenderá una larga explicación.','Proverbio');
insert into Tags values ('21', 'Proverbio');
insert into Posts values ('22', 'La paciencia es un árbol de raíz amarga pero de frutos muy dulces.','Proverbio');
insert into Tags values ('22', 'Proverbio');
insert into Posts values ('23', 'Somos sentimientos y tenemos personas','MR');
insert into Tags values ('23', 'Proverbio');
insert into Posts values ('24', 'Si te caes siete veces, levántate ocho.','Proverbio');
insert into Tags values ('24', 'Proverbio');
insert into Posts values ('26', 'Las grandes almas tienen voluntades; las débiles tan solo deseos.','Proverbio');
insert into Tags values ('26', 'Proverbio');
insert into Posts values ('25', 'Antes de poner en duda el buen juicio de tu mujer, fíjate con quien se ha casado ella.','Proverbio');
insert into Tags values ('25', 'Proverbio');








