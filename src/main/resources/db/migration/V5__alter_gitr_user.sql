ALTER TABLE git_repository_list
ADD COLUMN gitrowner_id UUID;
ALTER TABLE git_repository_list
ADD CONSTRAINT fk_gitrs_user FOREIGN KEY (gitrowner_id) REFERENCES user_table(id) ON DELETE CASCADE;

ALTER TABLE user_table
ADD COLUMN ownedrepositories UUID[];


