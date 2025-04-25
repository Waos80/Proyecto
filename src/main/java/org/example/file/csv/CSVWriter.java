package org.example.file.csv;

import org.example.contact.Contact;

import java.io.*;
import java.util.ArrayList;

public class CSVWriter {
    private final String path;
    private BufferedWriter writer;

    public CSVWriter(final String path) {
        this.path = path;
        this.writer = null;
    }

    public void WriteContact(final Contact contact) {
        try {
            writer = new BufferedWriter(new FileWriter(path, true));

            writer.write(Integer.toString(contact.id));
            writer.write(',');
            writer.write(contact.name);
            writer.write(',');
            writer.write(contact.lastName);
            writer.write(',');
            writer.write(contact.username);
            writer.write(',');
            writer.write(contact.phoneNumber);
            writer.write(',');
            writer.write(contact.email);
            writer.write(',');
            writer.write(contact.address);
            writer.write(',');
            writer.write(String.valueOf(contact.birthDate.toEpochDay()*24*60));
            writer.newLine();

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateContacts(ArrayList<Contact> contacts) {
        try {
            writer = new BufferedWriter(new FileWriter(path, false));
            writer.write("");
            writer.close();

            for (Contact contact : contacts) {
                WriteContact(contact);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
