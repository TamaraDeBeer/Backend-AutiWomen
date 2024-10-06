-- Wijzig de kolom 'text' in de 'forums' tabel naar VARCHAR(4000)
ALTER TABLE forums ALTER COLUMN text TYPE VARCHAR(4000);

-- Wijzig de kolom 'text' in de 'comments' tabel naar VARCHAR(2000)
ALTER TABLE comments ALTER COLUMN text TYPE VARCHAR(2000);

-- Een unieke constraint om ervoor te zorgen dat er geen dubbele gebruikersnamen zijn
ALTER TABLE users ADD CONSTRAINT unique_username UNIQUE (username);

ALTER TABLE forums ALTER COLUMN comments_count SET DEFAULT 0;
ALTER TABLE forums ALTER COLUMN likes_count SET DEFAULT 0;
ALTER TABLE forums ALTER COLUMN views_count SET DEFAULT 0;

-- Insert a new user
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url)
VALUES ('testuser', 'encoded_password', 'testuser@example.com', 'random_user_api_key', true, 'Test User', 'Female', '1990-01-01', 'Ja', 2005, 'http://example.com/images/profile.jpg'),
('testadmin', 'encoded_password', 'testadmin@example.com', 'random_admin_api_key', true, 'Test Admin', 'Female', '1990-01-01', 'Ja', 2005, 'http://example.com/images/profile.jpg');

-- Insert the corresponding authority for the user
INSERT INTO authorities (username, authority)
VALUES ('testuser', 'ROLE_USER'),
('testadmin', 'ROLE_ADMIN');

-- Insert a profile
INSERT INTO profiles (id, username, bio, name, date)
VALUES (5000, 'testuser', 'Ik ben een test user', 'test user', '2024-10-06'),
       (5001, 'testadmin', 'Ik ben een test admin', 'test admin', '2024-10-06');

-- Insert a forum
INSERT INTO forums (id, name, age, title, text, date, topic)
VALUES (1000, 'testuser', '18', 'Dit is een test forum', 'banaan', '2024-10-06', 'test topic'),
         (1001, 'testadmin', '18', 'Dit is een test forum', 'banaan', '2024-10-06', 'test topic'),
            (1002, 'testuser', '18', 'Dit is een test forum', 'banaan', '2024-10-06', 'test topic'),
            (1003, 'testadmin', '18', 'Dit is een test forum', 'banaan', '2024-10-06', 'test topic');

-- Insert a comment
INSERT INTO comments (id, forum_id, name, text, date)
VALUES (2000, 1000, 'testuser', 'Dit is een test comment', '2024-10-06'),
         (2001, 1000, 'testadmin', 'Dit is een test comment', '2024-10-06');

-- Insert a like
INSERT INTO likes (forum_id, user_id)
VALUES (1000, 'testuser'),
       (1000, 'testadmin');

-- Insert a view
INSERT INTO views (forum_id, user_id)
VALUES (1000, 'testuser'),
       (1000, 'testadmin');