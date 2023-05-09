package src.BookSearchEngine;

import java.util.ArrayList;
import java.util.List;

public class AVLNode {
    private String key;
    private List<Term> terms;
    private AVLNode left;
    private AVLNode right;
    private int height;

    public AVLNode(String key, Term term) {
        this.key = key;
        this.terms = new ArrayList<>();
        this.terms.add(term);
        left = null;
        right = null;
        height = 1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}