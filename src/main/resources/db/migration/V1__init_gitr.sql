CREATE TABLE IF NOT EXISTS git_repository_list (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    gitr_name varchar(400) not null,
    gitr_owner_id UUID not null,
    publicity boolean,
    membersnames text[],
    gitrepositorydescription varchar(400),
    lastcommitgenerated varchar(400)
);
