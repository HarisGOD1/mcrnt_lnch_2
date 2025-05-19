CREATE TABLE IF NOT EXISTS git_repository_list (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    gitrepository varchar(400) not null,
    gitownername varchar(100) not null,
    publicity boolean,
    membersnames varchar(800),
    gitrepositorydescription varchar(400),
    lastcommitgenerated varchar(400)

);
