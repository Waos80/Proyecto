package org.example.index;

import java.util.ArrayList;
import java.util.Comparator;

public class BSTree<T> implements Tree<T> {
    private TreeNode<T> root;
    private final Comparator<T> comparator;

    private TreeNode<T> findMinimum(TreeNode<T> root) {
        if (root.left == null) {
            return root;
        }
        return findMinimum(root.left);
    }

    private TreeNode<T> insert(TreeNode<T> root, T val) {
        if (root == null) {
            return new TreeNode<>(val);
        }

        if (comparator.compare(val, root.val) > 0) {
            root.right = insert(root.right, val);
        } else if (comparator.compare(val, root.val) < 0) {
            root.left = insert(root.left, val);
        }

        return root;
    }

    private TreeNode<T> delete(TreeNode<T> root, T val) {
        if (root == null) {
            return new TreeNode<>(val);
        }

        if (comparator.compare(val, root.val) > 0) {
            root.right = delete(root.right, val);
        } else if (comparator.compare(val, root.val) < 0) {
            root.left = delete(root.left, val);
        } else {
            if (root.right != null) {
                TreeNode<T> min = findMinimum(root.right);
                if (root.right == min) {
                    root = root.right;
                } else {
                    root.right.left = min.right;
                    min.right = root.right;
                    min.left = root.left;
                    root = min;
                }
            } else {
                root = root.left;
            }
        }

        return root;
    }

    private TreeNode<T> search(TreeNode<T> root, T val) {
        if (root == null) {
            return null;
        }

        if (comparator.compare(val, root.val) > 0) {
            return search(root.right, val);
        } else if (comparator.compare(val, root.val) < 0) {
            return search(root.left, val);
        } else {
            return root;
        }
    }

    public BSTree(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    public void insert(T val) {
        root = insert(root, val);
    }

    public void delete(T val) {
        root = delete(root, val);
    }

    public T search(T val) {
        TreeNode<T> s = search(root, val);
        if (s != null) {
            return s.val;
        }

        return null;
    }

    private void getBFS(TreeNode<T> root, ArrayList<ArrayList<T>> bfs, int level) {
        if (bfs.size() < level) {
            bfs.add(new ArrayList<>());
        }

        if (root == null) {
            bfs.get(level - 1).add(null);
            return;
        }

        bfs.get(level - 1).add(root.val);
        if (root.left != null || root.right != null) {
            getBFS(root.left, bfs, level + 1);
            getBFS(root.right, bfs, level + 1);
        }
    }

    public ArrayList<ArrayList<T>> getBFS() {
        ArrayList<ArrayList<T>> bfs = new ArrayList<>();
        getBFS(root, bfs, 1);
        return bfs;
    }

}
