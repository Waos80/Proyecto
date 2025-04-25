package org.example;

public class Main {
    public static void main(String[] args) {
        //TODO
        /*
        1. Hacer la función que actualize la información de un contacto, donde:
        - Se toma el indice y se encuentra el contacto
        - Se solicitan todos los datos de nuevo tal que si se crease un nuevo contacto-
        - Se almacena una copia de los contenidos del archivo de contactos en líneas,
          donde cada una equivale a la información de un contacto.
        - Se busca en qué linea los indices coinciden.
        - Se reemplaza la linea de coincidencia con la nueva información.
        - Se reescriben los contenidos al archivo-
        2. Hacer posible la opción de importar un archivo csv.
           - Se leen los contenidos del archivo a importar sin tomar en cuenta sus índices.
           - Si la información de uno de los contactos ya se encuentra en el archivo de contactos,
             no se agrega y pasa al siguiente contacto.
        3. Realizar la opción de crear un índice, donde dependiendo de la opción que se eliga se genere
           el archivo de texto correspondiente.
        4. Cambiar el tipo de dato de la fecha a date. // Opcional

         */
        try {
            Application app = new Application();
            app.Start();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        /*
        try {

            ContactManager manager = new ContactManager();
            manager.createContact(
                    "Abel",
                    "Abe",
                   "abe@",
                   "Anderson",
                   "Address1",
                   "0002",
                   "2000"
            );

            manager.createContact(
                    "Peter",
                    "Pete",
                    "peter@",
                    "Anderson",
                    "Address2",
                    "0001",
                    "2001"
            );
            Indexer indexer = new Indexer();
            indexer.storeIndexOf(Contact.class.getFields()[5], manager.getContacts());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
         */
    }
}
