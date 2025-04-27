package org.example;

import org.example.contact.Contact;
import org.example.contact.ContactManager;
import org.example.index.Indexer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Application {
    private final Scanner scanner;
    private final ContactManager manager;
    private final Indexer indexer;

    private void AddContact() {
        String name = "";
        while (name.isEmpty()) {
            System.out.println("Ingrese el nombre del contacto:");
            name = scanner.nextLine();
        }

        String username = "";
        while (username.isEmpty()) {
            System.out.println("Ingrese el nombre de usuario del contacto:");
            username = scanner.nextLine();
        }

        String email = "";
        while (email.isEmpty()) {
            System.out.println("Ingrese el correo del contacto:");
            email = scanner.nextLine();
        }

        String lastName = "";
        while (lastName.isEmpty()) {
            System.out.println("Ingrese el apellido del contacto:");
            lastName = scanner.nextLine();
        }

        String address = "";
        while (address.isEmpty()) {
            System.out.println("Ingrese la dirección del contacto:");
            address = scanner.nextLine();
        }

        String phoneNumber = "";
        while (phoneNumber.isEmpty()) {
            System.out.println("Ingrese el número de teléfono del contacto:");
            phoneNumber = scanner.nextLine();
        }

        int year = 0;
        while (year <= 0) {
            System.out.println("Ingrese el año de nacimiento del contacto:");
            year = scanner.nextInt();
            scanner.nextLine();
        }

        int month = 0;
        while (1 > month || month > 12) {
            System.out.println("Ingrese el mes de nacimiento del contacto:");
            month = scanner.nextInt();
            scanner.nextLine();
        }

        int day = 0;
        while (1 > day || day > 31) {
            System.out.println("Ingrese el día de nacimiento del contacto:");
            day = scanner.nextInt();
            scanner.nextLine();
        }

        boolean success = manager.CreateContact(
                name,
                username,
                email,
                lastName,
                address,
                phoneNumber,
                LocalDate.of(year, month, day)
        );

        if (!success) {
            System.out.println("El contacto ingresado ya existe");
        }
        else {
            System.out.println("¡El contacto ha sido creado con éxito!");
        }
    }

    private void DeleteContact() {
        int id;
        boolean success = false;
        System.out.println("Ingrese el id del contacto a eliminar:");
        id = scanner.nextInt();
        scanner.nextLine();
        success = manager.DeleteContact(id);
        if (success) {
            System.out.println("El contacto #" + id + " ha sido eliminado");
        } else {
            System.out.println("El id ingresado no corresponde a un contacto, inténtelo de nuevo");
        }
    }

    private void UpdateContact() {
        int id;
        System.out.println("Ingrese el id del contacto: ");
        id = scanner.nextInt();
        scanner.nextLine();

        if (!manager.SearchContact(id)) {
            System.out.println("El id ingresado no corresponde a un contacto, inténtelo de nuevo");
            return;
        }

        String name = "";
        while (name.isEmpty()) {
            System.out.println("Ingrese el nombre del contacto:");
            name = scanner.nextLine();
        }

        String username = "";
        while (username.isEmpty()) {
            System.out.println("Ingrese el nombre de usuario del contacto:");
            username = scanner.nextLine();
        }

        String email = "";
        while (email.isEmpty()) {
            System.out.println("Ingrese el correo del contacto:");
            email = scanner.nextLine();
        }

        String lastName = "";
        while (lastName.isEmpty()) {
            System.out.println("Ingrese el apellido del contacto:");
            lastName = scanner.nextLine();
        }

        String address = "";
        while (address.isEmpty()) {
            System.out.println("Ingrese la dirección del contacto:");
            address = scanner.nextLine();
        }

        String phoneNumber = "";
        while (phoneNumber.isEmpty()) {
            System.out.println("Ingrese el número de teléfono del contacto:");
            phoneNumber = scanner.nextLine();
        }

        int year = 0;
        while (year <= 0) {
            System.out.println("Ingrese el año de nacimiento del contacto:");
            year = scanner.nextInt();
            scanner.nextLine();
        }

        int month = 0;
        while (1 > month || month > 12) {
            System.out.println("Ingrese el mes de nacimiento del contacto:");
            month = scanner.nextInt();
            scanner.nextLine();
        }

        int day = 0;
        while (1 > day || day > 31) {
            System.out.println("Ingrese el día de nacimiento del contacto:");
            day = scanner.nextInt();
            scanner.nextLine();
        }

        Contact contact = new Contact(-1);
        contact.name = name;
        contact.username = username;
        contact.email = email;
        contact.lastName = lastName;
        contact.address = address;
        contact.phoneNumber = phoneNumber;
        contact.birthDate = LocalDate.of(year, month, day);
        if (!manager.UpdateContact(id, contact)) {
            System.out.println("El id ingresado no corresponde a un contacto, inténtelo de nuevo");
        }
        else {
            System.out.println("Contacto #" + id + " ha sido actualizado");
        }

    }

    private void SearchContact() {
        if (manager.GetContacts().isEmpty()) {
            System.out.println("Por el momento no hay contactos registrados que buscar");
            return;
        }

        System.out.println("Seleccione un campo por el que desee buscar el contacto:\n" +
                "1. ID\n" +
                "2. Nombre\n" +
                "3. Nombre de usuario\n" +
                "4. Correo\n" +
                "5. Apellido\n" +
                "6. Dirección\n" +
                "7. Número de teléfono\n" +
                "8. Fecha de nacimiento");

        int option = scanner.nextInt();
        scanner.nextLine();
        if (option < 1 || option > 7) {
            System.out.println("El campo seleccionado no existe");
            return;
        }

        ArrayList<Contact> contacts = manager.GetContacts();
        Contact res = null;
        switch (option) {
            case 1:
                System.out.println("Ingrese el id del contacto que desea buscar: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.id == id) {
                        res = c;
                        break;
                    }
                }

                break;
            case 2:
                System.out.println("Ingrese el nombre del contacto que desea buscar: ");
                String name = scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.name.equals(name)) {
                        res = c;
                        break;
                    }
                }
                break;
            case 3:
                System.out.println("Ingrese el nombre de usuario del contacto que desea buscar: ");
                String userName = scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.username.equals(userName)) {
                        res = c;
                        break;
                    }
                }
                break;
            case 4:
                System.out.println("Ingrese el correo del contacto que desea buscar: ");
                String email = scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.email.equals(email)) {
                        res = c;
                        break;
                    }
                }
                break;
            case 5:
                System.out.println("Ingrese el apellido del usuario del contacto que desea buscar: ");
                String lastName = scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.lastName.equals(lastName)) {
                        res = c;
                        break;
                    }
                }
                break;
            case 6:
                System.out.println("Ingrese la dirección del contacto que desea buscar: ");
                String address = scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.address.equals(address)) {
                        res = c;
                        break;
                    }
                }
                break;
            case 7:
                System.out.println("Ingrese el número de teléfono del contacto que desea buscar: ");
                String phoneNumber = scanner.nextLine();
                for (Contact c : contacts) {
                    if (c.phoneNumber.equals(phoneNumber)) {
                        res = c;
                        break;
                    }
                }
                break;
            case 8:
                int year = 0;
                while (year <= 0) {
                    System.out.println("Ingrese el año de nacimiento del contacto:");
                    year = scanner.nextInt();
                    scanner.nextLine();
                }

                int month = 0;
                while (1 > month || month > 12) {
                    System.out.println("Ingrese el mes de nacimiento del contacto:");
                    month = scanner.nextInt();
                    scanner.nextLine();
                }

                int day = 0;
                while (1 > day || day > 31) {
                    System.out.println("Ingrese el día de nacimiento del contacto:");
                    day = scanner.nextInt();
                    scanner.nextLine();
                }
                LocalDate date = LocalDate.of(year, month, day);
                for (Contact c : contacts) {
                    if (c.birthDate == date) {
                        res = c;
                        break;
                    }
                }
                break;
        }

        if (res == null) {
            System.out.println("El contacto buscado no existe");
        } else {
            PrintContact(res);
        }
    }

    private void CreateIndex() {
        System.out.println("Seleccione un campo:\n" +
                "1. ID\n" +
                "2. Nombre\n" +
                "3. Apellido\n" +
                "4. Nombre de usuario\n" +
                "5. Número de teléfono\n" +
                "6. Correo\n" +
                "7. Dirección");

        int option = scanner.nextInt();
        scanner.nextLine();
        if (option < 1 || option > 7) {
            System.out.println("El campo seleccionado no existe");
        } else {
            indexer.StoreIndexOf(Contact.class.getFields()[option - 1].getName(), manager.GetContacts());
            System.out.println("El indice ha sido creado");
        }
    }

    private void PrintContact(Contact contact) {
        System.out.println("Contacto #" + contact.id);
        System.out.println("\tNombre: " + contact.name);
        System.out.println("\tApellido: " + contact.lastName);
        System.out.println("\tNombre de usuario: " + contact.username);
        System.out.println("\tCorreo: " + contact.email);
        System.out.println("\tNúmero de teléfono: " + contact.phoneNumber);
        System.out.println("\tFecha de nacimiento: " + contact.birthDate);
    }

    private void PrintContacts() {
        ArrayList<Contact> contacts = manager.GetContacts();
        if (contacts.isEmpty()) {
            System.out.println("Por el momento no hay contactos registrados que mostrar");
            return;
        }
        for (Contact contact : contacts) {
            PrintContact(contact);
        }
    }

    private void ImportContacts() {
        String path = "";
        while (path.isEmpty()) {
            System.out.println("Indique la dirección donde se encuentra el archivo .csv a importar:");
            path = scanner.nextLine();
        }

        if (!manager.ImportContacts(path)) {
            System.out.println("Error al intentar leer el archivo, intentelo de nuevo");
        } else {
            System.out.println(path + " ha sido importado con exito");
        }
    }

    private void ImportIndex() {
        System.out.println("Indique la dirección donde se encuentra el archivo .txt a importar:");
        String path = scanner.nextLine();
        if (!indexer.Import(path, manager)) {
            System.out.println("Ocurrió un error al intentar importar el índice");
            return;
        }
        System.out.println("El indice ha sido importado");
    }

    public Application() throws IllegalAccessException {
        this.scanner = new Scanner(System.in);
        this.manager = new ContactManager();
        this.indexer = new Indexer();

    }

    public void Start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Seleccione una opción:\n" +
                    "1. Registrar contacto\n" +
                    "2. Eliminar un contacto existente\n" +
                    "3. Actualizar la información de un contacto\n" +
                    "4. Buscar un contacto\n" +
                    "5. Crear un índice\n" +
                    "6. Visualizar contactos\n" +
                    "7. Importar contactos\n" +
                    "8. Importar indice\n" +
                    "9. Salir");
            int option = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (option) {
                    case 1:
                        AddContact();
                        break;
                    case 2:
                        DeleteContact();
                        break;
                    case 3:
                        UpdateContact();
                        break;
                    case 4:
                        SearchContact();
                        break;
                    case 5:
                        CreateIndex();
                        break;
                    case 6:
                        PrintContacts();
                        break;
                    case 7:
                        ImportContacts();
                        break;
                    case 8:
                        ImportIndex();
                        break;
                    case 9:
                        exit = true;
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("La información ingresada es incorrecta, intentelo de nuevo");
            }
        }
    }
}
