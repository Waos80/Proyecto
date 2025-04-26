package org.example.index;

import org.example.contact.Contact;
import org.example.contact.ContactManager;
import org.example.file.txt.TXTWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Indexer {
    private final Map<Field, AVLTree<Contact>> avl_map;
    private final Map<Field, BSTree<Contact>> bst_map;

    private Comparator<Contact> getComparator(Field field) {
        String fieldTypeName = field.getType().getTypeName();
        if (fieldTypeName.contains("String")) {
            String fieldName = field.getName();
            switch (fieldName) {
                case "name" -> {
                    return Comparator.comparing(o -> o.name);
                }
                case "lastName" -> {
                    return Comparator.comparing(o -> o.lastName);
                }
                case "username" -> {
                    return Comparator.comparing(o -> o.username);
                }
                case "phoneNumber" -> {
                    return Comparator.comparing(o -> o.phoneNumber);
                }
                case "email" -> {
                    return Comparator.comparing(o -> o.email);
                }
                case "address" -> {
                    return Comparator.comparing(o -> o.address);
                }
            }
        }
        else if (fieldTypeName.contains("int")){
            String fieldName = field.getName();
            if (fieldName.equals("id")) {
                return Comparator.comparing(o -> o.id);
            }
        }

        return null;
    }

    private Comparator<Contact> getComparator(String fieldName) {
        switch (fieldName) {
            case "name" -> {
                return Comparator.comparing(o -> o.name);
            }
            case "lastName" -> {
                return Comparator.comparing(o -> o.lastName);
            }
            case "username" -> {
                return Comparator.comparing(o -> o.username);
            }
            case "phoneNumber" -> {
                return Comparator.comparing(o -> o.phoneNumber);
            }
            case "email" -> {
                return Comparator.comparing(o -> o.email);
            }
            case "address" -> {
                return Comparator.comparing(o -> o.address);
            }
        }

        return null;
    }

    private BSTree<Contact> GetBSTree(Field field) throws IllegalAccessException {
        return new BSTree<>(getComparator(field));
    }

    private AVLTree<Contact> GetAVLTree(Field field) throws IllegalAccessException {
        return new AVLTree<>(getComparator(field));
    }

    public Indexer() throws IllegalAccessException {
        this.bst_map = new HashMap<>();
        this.avl_map = new HashMap<>();
        for (Field field : Contact.class.getFields()) {
            this.avl_map.put(field, GetAVLTree(field));
            this.bst_map.put(field, GetBSTree(field));
        }
    }

    public AVLTree<Contact> GetAVL(Field field) {
        return avl_map.get(field);
    }

    public BSTree<Contact> GetBST(Field field) {
        return bst_map.get(field);
    }

    public void StoreIndexOf(Field field, ArrayList<Contact> contacts) {
        BSTree<Contact> bst = bst_map.get(field);
        if (bst == null) {
            return;
        }

        for (Contact c : contacts){
            bst.insert(c);
        }

        ArrayList<ArrayList<Contact>> bst_bfs = bst.getBFS();
        TXTWriter writer = new TXTWriter(field.getName() + "-bst.txt");
        for (int i = 0; i < bst_bfs.size(); i++) {
            ArrayList<Contact> l = bst_bfs.get(i);
            int size = l.size();

            for (int j = 0; j < size; j++) {
                Contact contact = l.get(j);
                if (contact != null) {
                    writer.write(contact.id);
                } else {
                    writer.write("null");
                }

                if (i != bst_bfs.size() - 1 || j != size - 1) {
                    writer.write(",");
                }
            }
        }

        AVLTree<Contact> avl = avl_map.get(field);
        if (avl == null) {
            return;
        }

        for (Contact c : contacts){
            avl.insert(c);
        }

        ArrayList<ArrayList<Contact>> avl_bfs = avl.getBFS();
        writer = new TXTWriter(field.getName() + "-avl.txt");

        for (int i = 0; i < avl_bfs.size(); i++) {
            ArrayList<Contact> l = avl_bfs.get(i);
            int size = l.size();
            for (int j = 0; j < size; j++) {
                Contact contact = l.get(j);
                if (contact != null) {
                    writer.write(contact.id);
                }
                else {
                    writer.write("null");
                }

                if (i != avl_bfs.size() - 1 || j != size - 1) {
                    writer.write(",");
                }
            }
        }
    }

    public boolean Import(String path, ContactManager manager) {
        File file = new File(path);
        String[] name = file.getName().split("-");
        String treeType = name[1].split("\\.")[0];

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));

        } catch (FileNotFoundException e) {
            return false;
        }
        String[] idxs = null;
        try {
            idxs = reader.readLine().split(",");
        } catch (IOException e) {
            return false;
        }

        if (treeType.equals("avl")) {
                AVLTree<Contact> tree = new AVLTree<>(getComparator(name[0]));
                for (String idx : idxs) {
                    if (!idx.equals("null")) {
                        tree.insert(manager.GetContact(Integer.parseInt(idx)));
                    }
                }
            try {
                avl_map.put(Contact.class.getField(name[0]), tree);
            } catch (NoSuchFieldException e) {
                return false;
            }
            return true;
            } else if (treeType.equals("bst")) {
                BSTree<Contact> tree = new BSTree<>(getComparator(name[0]));
                for (String idx : idxs) {
                    if (!idx.equals("null")) {
                        tree.insert(manager.GetContact(Integer.parseInt(idx)));
                    }
                }
                try {
                    bst_map.put(Contact.class.getField(name[0]), tree);
                } catch (NoSuchFieldException e) {
                    return false;
                }
                return true;
            }
        return false;
    }
}
