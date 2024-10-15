-- Delete existing users and authorities
DELETE FROM users WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
DELETE FROM authorities WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
-- Insert users with encrypted passwords
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url) VALUES
('Tamara', '$2a$10$PWH/3QF2tnR7zts99t9hl.je0bq3r3YAfbFqeZbod9IlCQUsmq3DO', 'Tamara.debeer@hotmail.com', 'random_api_key', true, 'Tamara', 'Female', '1991-07-06', 'Ja', 2020, 'http://localhost:1991/images/Tamara.jpg'),
('Ariel', '$2a$10$twl4.3SVqoAUxOfD5JbyWeWBWShnOmd.BMdxH0AGuHaN8qwPR7g0.', 'Ariel@ariel.nl', 'random_api_key', true, 'Ariel', 'Female', '2004-08-23', 'Ja', 2010, 'http://localhost:1991/images/Ariel.jpg'),
('Elsa', '$2a$10$USDe0tMUTGNbXiazepwCdO4LhDD9CudTinyvkJOVODtiAppRDRTGC', 'Elsa@Elsa.nl', 'random_api_key', true, 'Elsa', 'Female', '2002-06-20', 'Ja', 2018, 'http://localhost:1991/images/Elsa.jpg'),
('Jane', '$2a$10$F2LTpk80z0dquONy5ORLCe15nh0pwdbv2Lc1hZRM8vNAek.6jaSBW', 'Jane@Jane.nl', 'random_api_key', true, 'Jane', 'Female', '2000-02-05', 'Ja', 2015, 'http://localhost:1991/images/Jane.jpg'),
('Belle', '$2a$10$YsVqE0oviorr1rkNEENzl.VJ0e/AM0Ta5QHwsBdjsEppz7B2lfGVS', 'Belle@belle.nl', 'random_api_key', true, 'Belle', 'Female', '1995-12-06', 'Ja', 2020, 'http://localhost:1991/images/Belle.jpg'),
('Aurora', '$2a$10$CyZukI5aEeGsQlXze8WJI.rseyG56PongaP5D9yDhiT0o2uNjH4qa', 'Aurora@aurora.nl', 'random_api_key', true, 'Aurora', 'Female', '1945-12-06', 'Ja', 1963, 'http://localhost:1991/images/Aurora.jpg'),
('Tiana', '$2a$10$qNg5z1WMlciwlhkcBlqDVOKgR3RY2s3oMUmuH02XFO3ewj1GSy1E.', 'Tiana@tiana.nl', 'random_api_key', true, 'Tiana', 'Female', '1955-09-15', 'Ja', 1980, 'http://localhost:1991/images/Tiana.jpg'),
('Sarabi', '$2a$10$kVEUTjG4JbcitymQpI41jewgPXXloB5UagSZkODNugUa9NSsk4Ai2', 'Sarabi@sarabi.nl', 'random_api_key', true, 'Sarabi', 'Female', '1988-02-09', 'Ja', 2019, 'http://localhost:1991/images/Sarabi.jpg'),
('Nala', '$2a$10$jpgX5sslxClPnoxLSuCGFer.FHtImOUWZkBfn9SvDCQoEK8WiN9wO', 'Nala@nala.nl', 'random_api_key', true, 'Nala', 'Female', '1993-11-11', 'Ja', 2022, 'http://localhost:1991/images/Nala.jpg'),
('Moana', '$2a$10$1tIdYbgzPD.obnLNwxo3EOPyE2lMYfhxSLsMDMyu1YtDorjP2/Qa2', 'Moana@moana.nl', 'random_api_key', true, 'Moana', 'Female', '1980-05-28', 'Ja', 2000, 'http://localhost:1991/images/Moana.jpg');
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
