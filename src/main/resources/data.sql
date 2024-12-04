-- Delete existing users and authorities
DELETE FROM users WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
DELETE FROM authorities WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
-- Insert users with encrypted passwords
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url) VALUES
('Tamara', '$2a$10$HB/pjkyuRST9xgcVn4e9Lux2P5uhe4fszf2d0aVFA979jRGMKi9vu', 'Tamara.debeer@hotmail.com', 'random_api_key', true, 'Tamara', 'Female', '1991-07-06', 'Ja', 2020, 'http://localhost:1991/images/Tamara.jpg'),
('Ariel', '$2a$10$ZXw/1N7KZiZ.fYY7Kap/Gu7K647Q83WzfMG/4Q4qGfB.IRSMRzqCq', 'Ariel@ariel.nl', 'random_api_key', true, 'Ariel', 'Female', '2004-08-23', 'Ja', 2010, 'http://localhost:1991/images/Ariel.jpg'),
('Elsa', '$2a$10$h/M5EfP4Cll27oxSu57i/.bMSbJRfbIFhgX4t7RwbVBLdGh5biW4e', 'Elsa@Elsa.nl', 'random_api_key', true, 'Elsa', 'Female', '2002-06-20', 'Ja', 2018, 'http://localhost:1991/images/Elsa.jpg'),
('Jane', '$2a$10$L0jOG97jHzarGmg5w7Ys0u6iLgLI5o4aynK9.yTrFhJ1kCRChD3We', 'Jane@Jane.nl', 'random_api_key', true, 'Jane', 'Female', '2000-02-05', 'Ja', 2015, 'http://localhost:1991/images/Jane.jpg'),
('Belle', '$2a$10$D/lNsoOi4f0e/GKQ9uerPusOYWa7BAE/V4UHFxLYxkfW2QVhRYghi', 'Belle@belle.nl', 'random_api_key', true, 'Belle', 'Female', '1995-12-06', 'Ja', 2020, 'http://localhost:1991/images/Belle.jpg'),
('Aurora', '$2a$10$f8upXtMr8w3p5wxbOV1hj.Tlo6u1z0eKZnchKmBvIAERa.7IF3SCe', 'Aurora@aurora.nl', 'random_api_key', true, 'Aurora', 'Female', '1945-12-06', 'Ja', 1963, 'http://localhost:1991/images/Aurora.jpg'),
('Tiana', '$2a$10$N9Rns0Vwdf9sqKUHYlRk3.OzmBLYsYfqbKVqhLuJcG0z5ptx1H4b.', 'Tiana@tiana.nl', 'random_api_key', true, 'Tiana', 'Female', '1955-09-15', 'Ja', 1980, 'http://localhost:1991/images/Tiana.jpg'),
('Sarabi', '$2a$10$Z3lyzJuGChaeO/bdKl0Jp.SYXnUxfSnpeu.kI8.ecO2fS4i6C1ape', 'Sarabi@sarabi.nl', 'random_api_key', true, 'Sarabi', 'Female', '1988-02-09', 'Ja', 2019, 'http://localhost:1991/images/Sarabi.jpg'),
('Nala', '$2a$10$032J6FCejnAFwCY9PBi5EebE1BDFZhwklpSWSifR65prnvFVt0QoS', 'Nala@nala.nl', 'random_api_key', true, 'Nala', 'Female', '1993-11-11', 'Ja', 2022, 'http://localhost:1991/images/Nala.jpg'),
('Moana', '$2a$10$dL/lz0AcKnb/ycxUtaxP7ecpnntujiZ2uXkXYdBeFXfOsP1bCqgii', 'Moana@moana.nl', 'random_api_key', true, 'Moana', 'Female', '1980-05-28', 'Ja', 2000, 'http://localhost:1991/images/Moana.jpg');
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
