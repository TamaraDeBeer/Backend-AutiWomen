-- Delete existing users and authorities
DELETE FROM users WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
DELETE FROM authorities WHERE username IN ('Tamara', 'Ariel', 'Elsa', 'Jane', 'Belle', 'Aurora', 'Tiana', 'Sarabi', 'Nala', 'Moana');
-- Insert users with encrypted passwords
INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url) VALUES
('Tamara', '$2a$10$fMmYpSdKaPtOwBJfgUZBJe6Vu5SMXVuJNqt4NkuCLKptjLEuRQp1i', 'Tamara.debeer@hotmail.com', 'random_api_key', true, 'Tamara', 'Female', '1991-07-06', 'Ja', 2020, 'http://localhost:1991/images/Tamara.jpg'),
('Ariel', '$2a$10$iYquRqfooXF7RLoqO1asiupkEAH1TioKJGMJMYs8MIHvTr2Ox1xzu', 'Ariel@ariel.nl', 'random_api_key', true, 'Ariel', 'Female', '2004-08-23', 'Ja', 2010, 'http://localhost:1991/images/Ariel.jpg'),
('Elsa', '$2a$10$AHgb3oi1FLANl7ZBvujGq.ctoy/pnDya/aDLm6RMtQoie8B/71fq6', 'Elsa@Elsa.nl', 'random_api_key', true, 'Elsa', 'Female', '2002-06-20', 'Ja', 2018, 'http://localhost:1991/images/Elsa.jpg'),
('Jane', '$2a$10$PqT96usW4p7Gd4BztMtTludjclOs7PqnCDL0NK96SLjq/4SspjqmG', 'Jane@Jane.nl', 'random_api_key', true, 'Jane', 'Female', '2000-02-05', 'Ja', 2015, 'http://localhost:1991/images/Jane.jpg'),
('Belle', '$2a$10$1KPANlzNGdoQJG77jyg2Nug8Bq5FdoqTFCgt.IM/BFQULAYro7HC.', 'Belle@belle.nl', 'random_api_key', true, 'Belle', 'Female', '1995-12-06', 'Ja', 2020, 'http://localhost:1991/images/Belle.jpg'),
('Aurora', '$2a$10$AgMdOadb92r49gmnnvsOVOOVRGuM4TjFcc8hZc5NJ34rFTe9eVzwO', 'Aurora@aurora.nl', 'random_api_key', true, 'Aurora', 'Female', '1945-12-06', 'Ja', 1963, 'http://localhost:1991/images/Aurora.jpg'),
('Tiana', '$2a$10$mZZvv7ol5eJjQ/iVlyW5tecYr19AEojEnncx06Xr3iocgF3lqodxS', 'Tiana@tiana.nl', 'random_api_key', true, 'Tiana', 'Female', '1955-09-15', 'Ja', 1980, 'http://localhost:1991/images/Tiana.jpg'),
('Sarabi', '$2a$10$Nf8W1YjL3XsPnrIcwC.sdeAxBAyG2ycm8XDjRAos1sv1/lUwBhKxe', 'Sarabi@sarabi.nl', 'random_api_key', true, 'Sarabi', 'Female', '1988-02-09', 'Ja', 2019, 'http://localhost:1991/images/Sarabi.jpg'),
('Nala', '$2a$10$SVmTLoEix9IE5t.Wwf9Y9eVOpPADH.cVf1iF9K7xx3OWhVAV8xfdy', 'Nala@nala.nl', 'random_api_key', true, 'Nala', 'Female', '1993-11-11', 'Ja', 2022, 'http://localhost:1991/images/Nala.jpg'),
('Moana', '$2a$10$iDAFmOvdd1LKHmkgHw0uW.s9h/PXZn6svdWzANcz5QpgEmg8UcP66', 'Moana@moana.nl', 'random_api_key', true, 'Moana', 'Female', '1980-05-28', 'Ja', 2000, 'http://localhost:1991/images/Moana.jpg');
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
