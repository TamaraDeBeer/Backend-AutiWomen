-- Wijzig de kolom 'text' in de 'forums' tabel naar VARCHAR(4000)
ALTER TABLE forums ALTER COLUMN text TYPE VARCHAR(4000);

-- Wijzig de kolom 'text' in de 'comments' tabel naar VARCHAR(2000)
ALTER TABLE comments ALTER COLUMN text TYPE VARCHAR(2000);