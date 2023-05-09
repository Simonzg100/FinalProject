
package src.BookSearchEngine;

import src.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookSearchEngine {
    private Node rootName;

    private AVLTree authorTree;

    private Node rootAuthor;
    private Node rootCategory;

    private ArrayList<Book> books;

    public BookSearchEngine() {
        this(new ArrayList<Book>());
    }

    private Node getRootNodeType(Term.Type t) {
        if (t == Term.Type.NAME) {
            return rootName;
        } else if (t == Term.Type.AUTHOR) {
            return rootAuthor;
        } else if (t == Term.Type.CATEGORY){
            return rootCategory;
        }
        return null;
    }

    public BookSearchEngine(ArrayList<Book> books) {
        rootName = new Node();
        rootAuthor = new Node();
        rootCategory = new Node();
        authorTree = new AVLTree();
        this.books = books;
        for (Book book : books) {
            insert(book.getName().toLowerCase(), new Term(book), Term.Type.NAME, rootName, book.getStockQuantity());
            //insert(book.getCategory().toLowerCase(), new Term(book), Term.Type.CATEGORY, rootCategory, book.getStockQuantity());
            authorTree.insert(book.getAuthor(), new Term(book));
            //insert(book.getAuthor().toLowerCase(), new Term(book), Term.Type.AUTHOR, rootAuthor, book.getStockQuantity());
        }
    }


    public List<Book> search(String query, Term.Type type) {
        if (query == "") {
            return new ArrayList<>();
        }
        List<Book> books = new ArrayList<>();

        if (type == Term.Type.AUTHOR) {
            List<Term> terms = authorTree.search(query);
            for(Term t: terms) {
                books.add(t.getBook());
            }
        } else {
            Node rootNode = getRootNodeType(type);
            Node currentNode = rootNode;
            query = query.toLowerCase();
            for (int i = 0; i < query.length(); i++) {
                char currentLetter = query.charAt(i);
                Node nextNode = currentNode.getNodeAfter(currentLetter);
                if (nextNode == null) {
                    return new ArrayList<>(); // No matches found.
                }
                currentNode = nextNode;
            }
            collectBooks(currentNode, books);
        }

        Collections.sort(books, (b1, b2) -> b2.getStockQuantity() - b1.getStockQuantity());
        return books;
    }

    private void collectBooks(Node currentNode, List<Book> books) {
        if (currentNode.getTerm() != null) {
            for (Term t : currentNode.getTerm()) {
                books.add(t.getBook());
            }
        }

        for (Node child : currentNode.getChildren().values()) {
            collectBooks(child, books);
        }
    }


    private void insert(String name, Term term, Term.Type type, Node root, long weight) {

        if (name == null || name.isEmpty() || weight < 0 ) {
            return;
        }
        Node currentRoot = root;
        for (int i = 0; i < name.length(); i++) {

            char currentLetter = name.charAt(i);
            Node nodeAfter = currentRoot.getNodeAfter(currentLetter);
            if (nodeAfter == null) {
                nodeAfter = new Node();
                currentRoot.setNodeAfter(nodeAfter, currentLetter);
            }

            currentRoot = nodeAfter;
        }
        currentRoot.addTerm(term);

    }

    public List<Book> fuzzySearch(String searchText, Term.Type searchType) {
        int maxDistance = 5;
        List<Book> fuzzySearchResults = new ArrayList<>();

        String searchTextLower = searchText.toLowerCase();
        for (Book book : this.books) {
            String textToSearchLower;

            if (searchType == Term.Type.NAME) {
                textToSearchLower = book.getName().toLowerCase();
            } else if (searchType == Term.Type.AUTHOR) {
                textToSearchLower = book.getAuthor().toLowerCase();
            } else { // searchType == Term.Type.CATEGORY
                textToSearchLower = book.getCategory().toLowerCase();
            }

            int[][] dp = new int[searchTextLower.length() + 1][textToSearchLower.length() + 1];
            for (int i = 0; i <= searchTextLower.length(); i++) {
                dp[i][0] = i;
            }
            for (int j = 0; j <= textToSearchLower.length(); j++) {
                dp[0][j] = j;
            }
            for (int i = 1; i <= searchTextLower.length(); i++) {
                for (int j = 1; j <= textToSearchLower.length(); j++) {
                    int cost = searchTextLower.charAt(i - 1) == textToSearchLower.charAt(j - 1) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }

            if (dp[searchTextLower.length()][textToSearchLower.length()] <= maxDistance) {
                fuzzySearchResults.add(book);
            }
        }

        return fuzzySearchResults;
    }


    private void collectAllBooks(Node currentNode, List<Book> books) {
        if (currentNode.getTerm() != null) {
            for (Term t : currentNode.getTerm()) {
                books.add(t.getBook());
            }
        }

        for (Node child : currentNode.getChildren().values()) {
            collectAllBooks(child, books);
        }
    }
}
