package org.example.index;

import java.util.ArrayList;
import java.util.Comparator;

public class AVLTree<T> implements Tree<T> {
    private int size;
    private TreeNode<T> root;
    private final Comparator<T> comparator;

    private TreeNode<T> insertToTree(TreeNode<T> root, T val) {
        if (root == null) {
            size++;
            return new TreeNode<>(val);
        }


        if (comparator.compare(val, root.val) > 0) {
            root.right = insertToTree(root.right, val);
        } else if (comparator.compare(val, root.val) < 0) {
            root.left = insertToTree(root.left, val);
        }

        return root;
    }

    public int balance(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        return height(root.right, 0) - height(root.left, 0);
    }

    private TreeNode<T> rotateLeft(TreeNode<T> root) {
        TreeNode<T> pivot = root.right;
        root.right = pivot.left;
        pivot.left = root;
        return pivot;
    }

    private TreeNode<T> rotateRight(TreeNode<T> root) {
        TreeNode<T> pivot = root.left;
        root.left = pivot.right;
        pivot.right = root;
        return pivot;
    }


    private TreeNode<T> balanceTree(TreeNode<T> root) {
        int balance = balance(root);
        if (balance > 1) {
            if (balance(root.right) < 0) {
                root.right = rotateRight(root.right);
                root = rotateLeft(root);
            } else {
                root = rotateLeft(root);
            }
        }

        if (balance < -1) {
            if (balance(root.left) > 0) {
                root.left = rotateLeft(root.left);
                root = rotateRight(root);
            } else {
                root = rotateRight(root);
            }
        }

        return root;
    }

    private TreeNode<T> findMinimum(TreeNode<T> root) {
        if (root.left == null) {
            return root;
        }
        return findMinimum(root.left);
    }

    private TreeNode<T> deleteTreeNode(TreeNode<T> root, T val) {
        if (root == null) {
            return null;
        }

        if (comparator.compare(val, root.val) > 0) {
            root.right = deleteTreeNode(root.right, val);
        } else if (comparator.compare(val, root.val) < 0) {
            root.left = deleteTreeNode(root.left, val);
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
            size--;
        }

        return root;
    }

    private int height(TreeNode<T> root, int level) {
        if (root == null) {
            return level;
        }

        return Math.max(height(root.left, level + 1), height(root.right, level + 1));
    }

    public AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T val) {
        root = insertToTree(root, val);
        root = balanceTree(root);
    }


    public void delete(T val) {
        root = deleteTreeNode(root, val);
        root = balanceTree(root);
    }

    private TreeNode<T> searchInTree(TreeNode<T> root, T val) {
        if (root == null) {
            return null;
        }

        if (comparator.compare(val, root.val) > 0) {
            return searchInTree(root.right, val);
        } else if (comparator.compare(val, root.val) < 0) {
            return searchInTree(root.left, val);
        } else {
            return root;
        }
    }

    public T search(T val) {
        TreeNode<T> result = searchInTree(root, val);
        return result != null ? result.val : null;
    }

    private void getBFS(TreeNode<T> root, ArrayList<ArrayList<T>> bfs, int level) {
        if (bfs.size() < level) {
            bfs.add(new ArrayList<>());
        }

        if (root != null && (root.left == null && root.right == null)) {
            bfs.get(level - 1).add(root.val);
        } else if (root != null) {
            bfs.get(level - 1).add(root.val);
            getBFS(root.left, bfs, level + 1);
            getBFS(root.right, bfs, level + 1);
        } else {
            bfs.get(level - 1).add(null);
        }
    }

    public ArrayList<ArrayList<T>> getBFS() {
        ArrayList<ArrayList<T>> bfs = new ArrayList<>();
        getBFS(root, bfs, 1);
        return bfs;
    }
}
