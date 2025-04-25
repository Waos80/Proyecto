package org.example.file.txt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TXTWriter {
    private final String path;
    private BufferedWriter writer;

    public TXTWriter(String path) {
        this.path = path;
        try {
            this.writer = new BufferedWriter(new FileWriter(path));
            this.writer.write("");
            this.writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(int idx) {
        try {
            writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(Integer.toString(idx));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String msg) {
        try {
            writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(msg);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
