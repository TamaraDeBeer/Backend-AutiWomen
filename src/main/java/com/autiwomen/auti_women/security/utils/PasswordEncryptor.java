package com.autiwomen.auti_women.security.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Order(1)
public class PasswordEncryptor implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String[] passwords = {"Tamara24!", "Ariel24!", "Elsa24!", "Jane24!", "Belle24!", "Aurora24!", "Tiana24!", "Sarabi24!", "Nala24!", "Moana24!"};
        String[] usernames = {"Tamara", "Ariel", "Elsa", "Jane", "Belle", "Aurora", "Tiana", "Sarabi", "Nala", "Moana"};
        String[] emails = {"Tamara.debeer@hotmail.com", "Ariel@ariel.nl", "Elsa@Elsa.nl", "Jane@Jane.nl", "Belle@belle.nl", "Aurora@aurora.nl", "Tiana@tiana.nl", "Sarabi@sarabi.nl", "Nala@nala.nl", "Moana@moana.nl"};
        String[] dobs = {"1991-07-06", "2004-08-23", "2002-06-20", "2000-02-05", "1995-12-06", "1945-12-06", "1955-09-15", "1988-02-09", "1993-11-11", "1980-05-28"};
        int[] autismDiagnosesYears = {2020, 2010, 2018, 2015, 2020, 1963, 1980, 2019, 2022, 2000};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/data.sql"))) {
            writer.write("-- Delete existing users and authorities\n");
            writer.write("DELETE FROM users WHERE username IN (");
            for (int i = 0; i < usernames.length; i++) {
                writer.write(String.format("'%s'%s", usernames[i], (i < usernames.length - 1) ? ", " : ");\n"));
            }
            writer.write("DELETE FROM authorities WHERE username IN (");
            for (int i = 0; i < usernames.length; i++) {
                writer.write(String.format("'%s'%s", usernames[i], (i < usernames.length - 1) ? ", " : ");\n"));
            }

            writer.write("-- Insert users with encrypted passwords\n");
            writer.write("INSERT INTO users (username, password, email, apikey, enabled, name, gender, dob, autism_diagnoses, autism_diagnoses_year, profile_picture_url) VALUES\n");
            for (int i = 0; i < passwords.length; i++) {
                String encodedPassword = passwordEncoder.encode(passwords[i]);
                writer.write(String.format("('%s', '%s', '%s', 'random_api_key', true, '%s', 'Female', '%s', 'Ja', %d, 'http://localhost:1991/images/%s.jpg')%s\n",
                        usernames[i], encodedPassword, emails[i], usernames[i], dobs[i], autismDiagnosesYears[i], usernames[i], (i < passwords.length - 1) ? "," : ";"));
            }

            writer.write("-- Insert authorities for users\n");
            writer.write("INSERT INTO authorities (username, authority) VALUES\n");
            for (int i = 0; i < usernames.length; i++) {
                String role = usernames[i].equals("Tamara") ? "ROLE_ADMIN" : "ROLE_USER";
                writer.write(String.format("('%s', '%s')%s\n", usernames[i], role, (i < usernames.length - 1) ? "," : ";"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}