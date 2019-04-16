drop PROCEDURE if exists countPosts;

delimiter //

CREATE PROCEDURE countPosts(out contador int)
BEGIN

  select count(*) into contador FROM Posts;

END//

delimiter ;

declare @cantidad int;
call countPosts(@cantidad);
select @cantidad;
