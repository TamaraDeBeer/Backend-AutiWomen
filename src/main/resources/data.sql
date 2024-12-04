-- Delete existing users and authorities
DELETE FROM users WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
DELETE FROM authorities WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
-- Insert users with encrypted passwords
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url) VALUES
('Tamara', '$2a$10$I3dFPEJCQdRqmRk6Fp7N4.cdtg3YlrdkXw6FGXdzcwO1f3gPLv/jC', 'Tamara.debeer@hotmail.com', 'random_api_key', true, 'Tamara', 'Female', '1991-07-06', 'Ja', 2020, 'http://localhost:1991/images/Tamara.jpg'),
('Ariel', '$2a$10$x.cUNnc0R/jA76xsSBlwFuiqjKlVkeKFSZBKE1wsMVLwgPRmxGRLy', 'Ariel@ariel.nl', 'random_api_key', true, 'Ariel', 'Female', '2004-08-23', 'Ja', 2010, 'http://localhost:1991/images/Ariel.jpg'),
('Elsa', '$2a$10$ykwxF/6Oxs4NizhJ7piN0.5zNb0WSvzbmVZ4YhX7PsupDfLSXvTHq', 'Elsa@Elsa.nl', 'random_api_key', true, 'Elsa', 'Female', '2002-06-20', 'Ja', 2018, 'http://localhost:1991/images/Elsa.jpg'),
('Jane', '$2a$10$5Sd9JHmiPjH.J/XTYUg5wOGa9Q.PGhVWR6ANr7rEIQUNSNIyreyAW', 'Jane@Jane.nl', 'random_api_key', true, 'Jane', 'Female', '2000-02-05', 'Ja', 2015, 'http://localhost:1991/images/Jane.jpg'),
('Belle', '$2a$10$kSjlgJVVn6skSWg5/tWIyuWemURfBchZV3L48SrM8NHsVUHIWrA1a', 'Belle@belle.nl', 'random_api_key', true, 'Belle', 'Female', '1995-12-06', 'Ja', 2020, 'http://localhost:1991/images/Belle.jpg'),
('Aurora', '$2a$10$zQtl5vVxcRATJ.yEYOIf4ehof7FdTDmslzwcHBAr.B8Ku0O36QhRK', 'Aurora@aurora.nl', 'random_api_key', true, 'Aurora', 'Female', '1945-12-06', 'Ja', 1963, 'http://localhost:1991/images/Aurora.jpg'),
('Tiana', '$2a$10$UJqY.vaujE3MiX7FpRIwjOlxY7ajSpNmtW6KfLgbDde/Eqy3LbDq.', 'Tiana@tiana.nl', 'random_api_key', true, 'Tiana', 'Female', '1955-09-15', 'Ja', 1980, 'http://localhost:1991/images/Tiana.jpg'),
('Sarabi', '$2a$10$c2spQ40SmJq1hSoB08nG0O0Na0hukBIIuf0Iq4ZH23smeG/c2eMNS', 'Sarabi@sarabi.nl', 'random_api_key', true, 'Sarabi', 'Female', '1988-02-09', 'Ja', 2019, 'http://localhost:1991/images/Sarabi.jpg'),
('Nala', '$2a$10$TEWNE9nDA0yK1QokcLcFkussvukph6KJLUtpTAmQFfyWzeZnRqdra', 'Nala@nala.nl', 'random_api_key', true, 'Nala', 'Female', '1993-11-11', 'Ja', 2022, 'http://localhost:1991/images/Nala.jpg'),
('Moana', '$2a$10$FYpht4NXRoXmwlsLx.G5FOPBLLZI.2FA.r9B/Or4A/R2o8fha4TW2', 'Moana@moana.nl', 'random_api_key', true, 'Moana', 'Female', '1980-05-28', 'Ja', 2000, 'http://localhost:1991/images/Moana.jpg');
-- Insert authorities for users
INSERT INTO authorities (username, authority) VALUES
('Tamara', 'ROLE_ADMIN'),
('Ariel', 'ROLE_USER'),
('Elsa', 'ROLE_USER'),
('Jane', 'ROLE_USER'),
('Belle', 'ROLE_USER'),
('Aurora', 'ROLE_USER'),
('Tiana', 'ROLE_USER'),
('Sarabi', 'ROLE_USER'),
('Nala', 'ROLE_USER'),
('Moana', 'ROLE_USER');
