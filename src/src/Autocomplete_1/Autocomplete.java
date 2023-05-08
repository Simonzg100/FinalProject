package src.Autocomplete_1;

import src.Book;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autocomplete {
    private Node root;
    private Node rootName;
    private Node rootAuthor;
    private Node rootCategory;

    private Node getRootNode(Term.Type t) {
        if (t == Term.Type.NAME) {
            return rootName;
        } else if (t == Term.Type.AUTHOR) {
            return rootAuthor;
        } else if (t == Term.Type.CATEGORY){
            return rootCategory;
        }
        return null;
    }

    public Autocomplete() {
        this(new ArrayList<Book>());
    }

    public Autocomplete(ArrayList<Book> books) {
        root = new Node();
        rootName = new Node();
        rootAuthor = new Node();
        rootCategory = new Node();
        for (Book book : books) {
            insert(book.getName().toLowerCase(), new Term(book), Term.Type.NAME, rootName, book.getStockQuantity());
            insert(book.getAuthor().toLowerCase(), new Term(book), Term.Type.AUTHOR, rootAuthor,book.getStockQuantity());
            insert(book.getCategory().toLowerCase(), new Term(book), Term.Type.CATEGORY, rootCategory,book.getStockQuantity());
        }
    }

    public List<Book> search(String query, Term.Type type) {
        if(query == "") {
            return new ArrayList<>();
        }
        List<Book> books = allMatches(query, type);
        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : books) {
            switch (type) {
                case NAME:
                    if (book.getName().toLowerCase().contains(query.toLowerCase())) {
                        filteredBooks.add(book);
                    }
                    break;
                case AUTHOR:
                    if (book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                        filteredBooks.add(book);
                    }
                    break;
                case CATEGORY:
                    if (book.getCategory().toLowerCase().contains(query.toLowerCase())) {
                        filteredBooks.add(book);
                    }
                    break;
            }
        }

        Collections.sort(filteredBooks, (b1, b2) -> {
            return b2.getStockQuantity() - b1.getStockQuantity();
        });

        return filteredBooks;
    }

    private void insert(String name, Term term, Term.Type type, Node root, long weight) {

        if (name == null || name.isEmpty() || weight < 0 ) {
            return;
        }
        Node currentRoot = root;
        for (int i = 0; i < name.length(); i++) {
//            System.out.println("category:" + name);
//            System.out.println("name:" + term.getBook().getName());
            char currentLetter = name.charAt(i);
            Node nodeAfter = currentRoot.getNodeAfter(currentLetter);
            if (nodeAfter == null) {
                nodeAfter = new Node();
                currentRoot.setNodeAfter(nodeAfter, currentLetter);
            }
            //currentRoot.setPrefixes(currentRoot.getPrefixes() + 1);
            currentRoot = nodeAfter;
        }
        currentRoot.addTerm(term);
        //currentRoot.setWords(currentRoot.getWords() + 1);
        //currentRoot.setPrefixes(1);
    }

    private List<Book> allMatches(String prefix, Term.Type type) {
        List<Book> books = new ArrayList<>();
        for(Term t: getSuggestions(prefix, type)) {
            books.add(t.getBook());
        }
        return books;
    }

    /**
     * @param prefix
     * @return the root of the subTrie corresponding to the last character of
     *            the prefix. If the prefix is not represented  in the trie, return null
     */
    public Node getSubTrie(String prefix, Term.Type type) {
        Node currentNode = getRootNode(type);
        if (prefix == null) {
            return null;
        }
        prefix = prefix.toLowerCase();
        return getSubTrieRecursive(prefix, currentNode);
    }

    private Node getSubTrieRecursive(String prefix, Node currentRoot) {
        if (prefix.equals("")) {
            return currentRoot;
        }
        char currentLetter = prefix.charAt(0);
        Node nodeAfter = currentRoot.getNodeAfter(currentLetter);
        if (nodeAfter == null) { // Not found
            return null;
        }
        return getSubTrieRecursive(prefix.substring(1), nodeAfter);
    }

    public List<Term> getSuggestions(String prefix, Term.Type type) {
        List<Term> result = new ArrayList<Term>();
        prefix = prefix.toLowerCase();
        Node root = getSubTrie(prefix, type);
        if (root == null) {
            return result;
        }
        getSuggestionsRecursive(root, result);
        return result;
    }

    private void getSuggestionsRecursive(Node root, List<Term> result) {
        if (root.getTerm() != null) { // If the current node has a word attached, add it to result
            for(Term t: root.getTerm()) {
                result.add(t);
            }
        }
        for(Node n: root.getChildren().values()) {
            getSuggestionsRecursive(n, result);
        }
    }

}
