MERGE INTO mpa (name)
    VALUES ('G'),
           ('PG'),
           ('PG-13'),
           ('R'),
           ('NC-17');

MERGE INTO genres (name)
    VALUES ('Комедия'),
           ('Драма'),
           ('Мультфильм'),
           ('Триллер'),
           ('Документальный'),
           ('Боевик');
select * from users;
