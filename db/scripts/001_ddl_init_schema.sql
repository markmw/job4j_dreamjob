CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    visible boolean,
    city_id integer
);

CREATE TABLE IF NOT EXISTS candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    visible boolean,
    city_id integer,
    photo bytea
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  email varchar(255) UNIQUE,
  password TEXT
);