ALTER TABLE git_repository_list
ADD COLUMN gitr_owner_id UUID REFERENCES user_table (id);

ALTER TABLE user_table
ADD COLUMN ownedrepositories UUID[];

ALTER TABLE git_repository_list
ADD CONSTRAINT fk_gitrs_user FOREIGN KEY (gitr_owner_id) REFERENCES user_table(id) ON DELETE CASCADE;

