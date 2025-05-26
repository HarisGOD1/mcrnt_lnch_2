CREATE TABLE IF NOT EXISTS user_table1 (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username varchar(400) not null UNIQUE,
    password_hash varchar(400) not null
);
