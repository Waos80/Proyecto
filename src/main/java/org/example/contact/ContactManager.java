package org.example.contact;

import org.example.file.csv.CSVReader;
import org.example.file.csv.CSVWriter;

import java.time.LocalDate;
import java.util.ArrayList;

public class ContactManager {
    private static int id_count;
    private final CSVReader reader;
    private final CSVWriter writer;
    private ArrayList<Contact> contacts;

    private boolean RegisterContact(Contact contact) {
        if (!SearchContact(contact)) {
            writer.WriteContact(contact);
            contacts.add(contact);
            id_count++;
            return true;
        }

        return false;
    }

    private boolean UnregisterContact(final int id) {
        int line = -1;
        int count = 0;
        ArrayList<Contact> contacts = reader.GetContacts();
        for (Contact contact : contacts) {
            if (contact.id == id) {
                line = count;
                break;
            }
            count++;
        }

        if (line >= 0) {
            contacts.remove(line);
            writer.UpdateContacts(contacts);
            return true;
        }

        return false;
    }

    private void UpdateContactList() {
        contacts = reader.GetContacts();
        if (contacts.isEmpty()) {
            id_count = 0;
        }
        else {
            id_count = contacts.getLast().id;
        }
    }

    public ContactManager() {
        id_count = 0;
        this.reader = new CSVReader("./contacts.csv");
        this.writer = new CSVWriter("./contacts.csv");
        this.contacts = new ArrayList<>();

        UpdateContactList();
    }

    public boolean CreateContact(
            String name,
            String username,
            String email,
            String lastName,
            String address,
            String phoneNumber,
            LocalDate birthDate
    ) {
        Contact contact = new Contact(id_count);
        contact.name = name;
        contact.username = username;
        contact.email = email;
        contact.lastName = lastName;
        contact.address = address;
        contact.phoneNumber = phoneNumber;
        contact.birthDate = birthDate;

        return RegisterContact(contact);
    }

    public boolean DeleteContact(int idx) {
        if (idx < 0 || contacts.size() - 1 < idx) {
            return false;
        }

        return UnregisterContact(contacts.get(idx).id);
    }

    public Contact GetContact(int idx) {
        if (idx > contacts.size() - 1 || idx < 0) {
            return null;
        }
        return contacts.get(idx);
    }

    public ArrayList<Contact> GetContacts() {
        return contacts;
    }

    public boolean SearchContact(int id) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.id == id) {
                return true;
            }
        }
        return false;
    }

    public boolean SearchContact(Contact contact) {
        for (Contact c : contacts) {
            if (c.name.equals(contact.name) &&
                c.lastName.equals(contact.lastName) &&
                c.username.equals(contact.username) &&
                c.phoneNumber.equals(contact.phoneNumber) &&
                c.email.equals(contact.email) &&
                c.birthDate.equals(contact.birthDate) &&
                c.address.equals(contact.address))
            {
                return true;
            }
        }
        return false;
    }

    public boolean UpdateContact(int id, Contact newContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.id == id) {
                newContact.id = contact.id;
                contacts.set(i, newContact);
                writer.UpdateContacts(contacts);
                return true;
            }
        }
        return false;
    }

    public boolean ImportContacts(final String path) {
        ArrayList<Contact> importContacts;
        CSVReader importReader = new CSVReader(path);
        importContacts = importReader.GetContacts();
        if (importContacts == null) {
            return false;
        }

        for (Contact contact : importContacts) {
            if (!CreateContact(
                    contact.name,
                    contact.username,
                    contact.email,
                    contact.lastName,
                    contact.address,
                    contact.phoneNumber,
                    contact.birthDate
            )) {
                return false;
            }
        }
        return true;
    }
}