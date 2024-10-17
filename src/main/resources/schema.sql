CREATE TABLE IF NOT EXISTS users (
  user_id INT PRIMARY KEY,
  email VARCHAR,
  login VARCHAR,
  name VARCHAR,
  birthday TIMESTAMP
);

-- соединительная таблица
CREATE TABLE IF NOT EXISTS friendship (
  user_id INT,
  friend_id INT,
  status VARCHAR
);

-- соединительная таблица
CREATE TABLE IF NOT EXISTS film_user_likes (
  film_id INT,
  user_id INT
);

CREATE TABLE IF NOT EXISTS mpa (
  mpa_id INT PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE IF NOT EXISTS films (
  film_id INT PRIMARY KEY,
  name VARCHAR,
  description VARCHAR,
  releaseDate TIMESTAMP,
  duration INT,
  mpa_id INT
);

CREATE TABLE IF NOT EXISTS genres (
  genre_id INT PRIMARY KEY,
  name VARCHAR
);

-- соединительная таблица
CREATE TABLE IF NOT EXISTS genre_film (
  genre_id INT,
  film_id INT
);



ALTER TABLE friendship ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE friendship ADD FOREIGN KEY (friend_id) REFERENCES users (user_id);

ALTER TABLE film_user_likes ADD FOREIGN KEY (film_id) REFERENCES films (film_id);

ALTER TABLE film_user_likes ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE films ADD FOREIGN KEY (mpa_id) REFERENCES mpa (mpa_id);

ALTER TABLE genre_film ADD FOREIGN KEY (genre_id) REFERENCES genres (genre_id);

ALTER TABLE genre_film ADD FOREIGN KEY (film_id) REFERENCES films (film_id);