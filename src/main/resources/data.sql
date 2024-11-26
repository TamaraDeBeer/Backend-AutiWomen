-- Delete existing users and authorities
DELETE FROM users WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
DELETE FROM authorities WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
-- Insert users with encrypted passwords
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url) VALUES
('Tamara', '$2a$10$8vszDM./o8X3t7g67BdY/O5wSM.tIKMpgvuE2qSMqeHF0o8er59Li', 'Tamara.debeer@hotmail.com', 'random_api_key', true, 'Tamara', 'Female', '1991-07-06', 'Ja', 2020, 'http://localhost:1991/images/Tamara.jpg'),
('Ariel', '$2a$10$jmGv2gAZe27JAkuvz4WlpuGkjCkWmknU4p9hlzrulp6Y5ykmMAPGG', 'Ariel@ariel.nl', 'random_api_key', true, 'Ariel', 'Female', '2004-08-23', 'Ja', 2010, 'http://localhost:1991/images/Ariel.jpg'),
('Elsa', '$2a$10$7xot3EaOoSGFJzVODdPV/.x8EOODEsAQhn9qZHZx1Utik/vFa/qcC', 'Elsa@Elsa.nl', 'random_api_key', true, 'Elsa', 'Female', '2002-06-20', 'Ja', 2018, 'http://localhost:1991/images/Elsa.jpg'),
('Jane', '$2a$10$03PUspFaaBR4FxzXo4aHIOqiOa7wXCNK.0CrrqTAwTAt5zxxztgsK', 'Jane@Jane.nl', 'random_api_key', true, 'Jane', 'Female', '2000-02-05', 'Ja', 2015, 'http://localhost:1991/images/Jane.jpg'),
('Belle', '$2a$10$yqbDIaWsOEmlasaUbGXdlu35v1ABDweUcQxndlSFW1h3vk4bj56zC', 'Belle@belle.nl', 'random_api_key', true, 'Belle', 'Female', '1995-12-06', 'Ja', 2020, 'http://localhost:1991/images/Belle.jpg'),
('Aurora', '$2a$10$N67cdlI4.eg7YAz6lSaRO.IesEYt.Zj3QRrpjVrsHbQ1cfV.lBa7a', 'Aurora@aurora.nl', 'random_api_key', true, 'Aurora', 'Female', '1945-12-06', 'Ja', 1963, 'http://localhost:1991/images/Aurora.jpg'),
('Tiana', '$2a$10$/JJETcVKgQ9hiFZZQcKuXuCD1iTAeZAE0mQ9LLiNHLJfgaNbZKxVS', 'Tiana@tiana.nl', 'random_api_key', true, 'Tiana', 'Female', '1955-09-15', 'Ja', 1980, 'http://localhost:1991/images/Tiana.jpg'),
('Sarabi', '$2a$10$M5z44A/nWPKwS3UPHPTE6uMryV0z35wPMDHmlZzSJusPfiYkqUTRO', 'Sarabi@sarabi.nl', 'random_api_key', true, 'Sarabi', 'Female', '1988-02-09', 'Ja', 2019, 'http://localhost:1991/images/Sarabi.jpg'),
('Nala', '$2a$10$5DIrRuLOionukX0ARCRucOrfZTDjFSe97v7XEcpaZBJpol/EQ9Xwe', 'Nala@nala.nl', 'random_api_key', true, 'Nala', 'Female', '1993-11-11', 'Ja', 2022, 'http://localhost:1991/images/Nala.jpg'),
('Moana', '$2a$10$oHALm5b8wH7iM1jPi34VYunDFFPWAkf3ZAQq/foRcCU2/EoZyVrMS', 'Moana@moana.nl', 'random_api_key', true, 'Moana', 'Female', '1980-05-28', 'Ja', 2000, 'http://localhost:1991/images/Moana.jpg');
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
