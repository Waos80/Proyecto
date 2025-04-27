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
            this.contacts = contacts;
            writer.UpdateContacts(contacts);
            return true;
        }

        return false;
    }

    private void UpdateContactList() {
        contacts = reader.GetContacts();
        if (contacts.isEmpty()) {
            id_count = 1;
        }
        else {
            for (Contact contact : contacts) {
                if (id_count < contact.id) {
                    id_count = contact.id;
                }
            }
        }
    }

    public ContactManager() {
        id_count = 1;
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
        contact.email = email.toLowerCase();
        contact.lastName = lastName;
        contact.address = address;
        contact.phoneNumber = phoneNumber;
        contact.birthDate = birthDate;

        return RegisterContact(contact);
    }

    public boolean DeleteContact(int id) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.id == id) {
                return UnregisterContact(id);
            }
        }

        return false;
    }

    public Contact GetContact(int idx) {
        if (idx > contacts.size() || idx < 1) {
            return null;
        }
        return contacts.get(idx - 1);
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

    public Contact GetContactByID(int id) {
        for (Contact contact : contacts) {
            if (contact.id == id) {
                return contact;
            }
        }
        return null;
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

    /*
        Función que actualize la información de un contacto, donde:
        - Se toma el indice y se encuentra el contacto
        - Se solicitan todos los datos de nuevo tal que si se crease un nuevo contacto-
        - Se almacena una copia de los contenidos del archivo de contactos en líneas,
          donde cada una equivale a la información de un contacto.
        - Se busca en qué linea los indices coinciden.
        - Se reemplaza la linea de coincidencia con la nueva información.
        - Se reescriben los contenidos al archivo.
        Se retorna false en caso que no se encuentre el contacto a actualizar
    */
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

    /*     Función que importa los contactos de un archivo csv.
           - Se leen los contenidos del archivo y se registra cada contacto al archivo principal.
           - Si la información de uno de los contactos ya se encuentra en el archivo de contactos,
             no se agrega y pasa al siguiente contacto.
     */
    public boolean ImportContacts(final String path) {
        ArrayList<String> importContents;
        CSVReader importReader = new CSVReader(path);

        importContents = importReader.GetFileContents();
        if (importContents == null) {
            return false;
        }

        for (String line : importContents) {
            String[] fields = line.split(",");
            int start = 0;
            for (char ch : fields[0].toCharArray()){
                if (!Character.isDigit(ch)) {
                    start++;
                }
            }
            id_count = Integer.parseInt(fields[0].substring(start));
            CreateContact(
                    fields[1],
                    fields[3],
                    fields[5],
                    fields[2],
                    fields[6],
                    fields[4],
                    LocalDate.ofEpochDay(Long.parseLong(fields[7]))
            );
        }
        return true;
    }
}