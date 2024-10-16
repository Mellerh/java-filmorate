CREATE TABLE IF NOT EXISTS USERS (
  "users_id" integer PRIMARY KEY,
  "email" varchar,
  "login" varchar,
  "name" varchar,
  "birthday" timestamp
);

CREATE TABLE IF NOT EXISTS FRIENDSHIP (
  "user_id" integer,
  "friend_id" integer,
--  "status" varchar
);

CREATE TABLE IF NOT EXISTS FILM_USER_LIKES (
  "film_id" integer,
  "user_id" integer
);

CREATE TABLE IF NOT EXISTS FILMS (
  "film_id" integer PRIMARY KEY,
  "name" varchar,
  "description" varchar,
  "releaseDate" timestamp,
  "duration" integer,
  "mpa_id" integer
);

CREATE TABLE IF NOT EXISTS GENRES (
  "genre_id" integer PRIMARY KEY,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS GENRE_FILM (
  "genre_id" integer,
  "film_id" integer
);

CREATE TABLE IF NOT EXISTS MOTION_PICTURE_ASSOCIATION (
  "mpa_id" integer PRIMARY KEY,
  "name" varchar
);


// исправить названия таблиц и ссылок на ключи
ALTER TABLE "friendship" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("friend_id") REFERENCES "user" ("id");

ALTER TABLE "film_user_likes" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("id");

ALTER TABLE "film_user_likes" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "film" ADD FOREIGN KEY ("mpa_id") REFERENCES "motion_picture_association" ("mpa_id");

ALTER TABLE "genre_film" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "genre_film" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("id");
