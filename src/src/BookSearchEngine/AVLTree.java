package src.BookSearchEngine;


import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private AVLNode root;

    public AVLTree() {
        root = null;
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.getHeight();
    }

    private int balanceFactor(AVLNode node) {
        return node == null ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);
//        x.right = y;
//        y.left = T2;
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
//        y.height = Math.max(height(y.left), height(y.right)) + 1;
//        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
//        y.left = x;
//        x.right = T2;
//
//        x.height = Math.max(height(x.left), height(x.right)) + 1;
//        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public AVLNode insert(AVLNode node, String key, Term term) {
        if (node == null) {
            return new AVLNode(key, term);
        }

        int comparison = key.compareTo(node.getKey());

        if (comparison < 0) {
            node.setLeft(insert(node.getLeft(), key, term));
            //node.left = insert(node.left, key, term);
        } else if (comparison > 0) {
            node.setRight(insert(node.getRight(), key, term));
            //node.right = insert(node.right, key, term);
        } else {
            node.getTerms().add(term);
            //node.terms.add(term);
            return node;
        }

        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        //node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        if (balance > 1 && key.compareTo(node.getLeft().getKey()) < 0) {
            return rotateRight(node);
        }

        if (balance < -1 && key.compareTo(node.getRight().getKey()) > 0) {
            return rotateLeft(node);
        }

        if (balance > 1 && key.compareTo(node.getLeft().getKey()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            //node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && key.compareTo(node.getRight().getKey()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            //node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void insert(String key, Term term) {
        root = insert(root, key, term);
    }

    public List<Term> search(String key) {
        AVLNode currentNode = root;
        while (currentNode != null) {
            int comparison = key.compareTo(currentNode.getKey());
            if (comparison < 0) {
                currentNode = currentNode.getLeft();
            } else if (comparison > 0) {
                currentNode = currentNode.getRight();
            } else {
                return currentNode.getTerms();
            }
        }
        return new ArrayList<>();
    }

}
