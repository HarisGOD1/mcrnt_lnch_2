CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- translate id from [Long?] to [UUID?] type
ALTER TABLE git_repository_list
DROP COLUMN id;

ALTER TABLE git_repository_list
ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

ALTER TABLE entity_msg
DROP COLUMN id;

ALTER TABLE entity_msg
ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

