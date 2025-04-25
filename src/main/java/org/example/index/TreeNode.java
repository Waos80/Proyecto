package org.example.index;

public class TreeNode<T> {
    public T val;
    public TreeNode<T> left;
    public TreeNode<T> right;

    public TreeNode(T value) {
        this.val = value;
        this.left = null;
        this.right = null;
    }
}
