package org.example.file.csv;

import org.example.contact.Contact;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CSVReader {
    private final String path;
    private BufferedReader reader;

    private Contact GetContactFromFields(String[] fields) {
        try {
            Contact contact = new Contact(Integer.parseInt(fields[0]));
            contact.name = fields[1];
            contact.lastName = fields[2];
            contact.username = fields[3];
            contact.phoneNumber = fields[4];
            contact.email = fields[5];
            contact.address = fields[6];
            contact.birthDate = LocalDate.ofEpochDay(Long.parseLong(fields[7]));
            return contact;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public CSVReader(final String path) {
        this.path = path;
        this.reader = null;
    }

    public ArrayList<String> GetFileContents() {
        try {
            reader = new BufferedReader(new FileReader(path));
            ArrayList<String> contents = new ArrayList<>(reader.lines().toList());
            reader.close();
            return contents;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Contact> GetContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            for (String line : reader.lines().toList()) {
                String[] fields = line.split(",");
                Contact contact = GetContactFromFields(fields);
                if (contact == null) {
                    throw new IOException();
                }
                contacts.add(contact);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }

        return contacts;
    }
}
