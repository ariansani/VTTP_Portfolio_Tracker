USE portfoliotracker;

INSERT INTO
    user (username, password, is_active,created_at)
VALUES
('arian', sha1('p@ssw0rd123'), true, current_timestamp());

select * from user;