package src.Autocomplete_1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import src.Book;

public class Node {
    public Map<Character, Node> children;
    private ArrayList<Term> term;


    public Node(Book book) {
        children = new HashMap<>();
        term = new ArrayList<>();
        term.add(new Term(book));
    }

    public Node(Term term) {
        children = new HashMap<>();
        this.term = new ArrayList<>();
        this.term.add(term);
    }

    public ArrayList<Term> getTerm() {
        return this.term;
    }

    public Node() {
        children = new HashMap<>();
        this.term = new ArrayList<>();
    }

    public void addTerm(Term term) {
        this.term.add(term);
    }

    public Node getNodeAfter(char letter) {
        return children.get(letter);
    }


    public void setNodeAfter(Node n, char letter) {
        this.children.put(letter, n);
    }

    public Map<Character, Node> getChildren() {
        return children;
    }
}
