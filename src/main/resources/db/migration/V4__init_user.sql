CREATE TABLE IF NOT EXISTS user_table (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username varchar(400) not null,
    password_hash varchar(400) not null

);
