--CREATE TYPE user_role AS ENUM ('DEFAULT_USER', 'ADMIN', 'THIRD_USER');

CREATE TABLE IF NOT EXISTS user_table (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username varchar(400) not null UNIQUE,
    password_hash varchar(400) not null,
    owned_repositories_id_list UUID[]
--    user_role_list user_role[]
);