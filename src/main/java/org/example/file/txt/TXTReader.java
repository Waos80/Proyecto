package org.example.file.txt;

import java.io.BufferedReader;

public class TXTReader {
    private final String path;
    private BufferedReader reader;

    public TXTReader(String path) {
        this.path = path;
        this.reader = null;
    }
}
