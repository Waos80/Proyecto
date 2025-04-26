package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            Application app = new Application();
            app.Start();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
