package org.example.index;

import java.util.ArrayList;

public interface Tree<T> {
    void insert(T val);
    void delete(T val);
    T search(T val);
    ArrayList<ArrayList<T>> getBFS();
}
