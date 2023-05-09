package src.Autocomplete;

/**
 * @author Harry Smith
 */

public class Node {

    private Term term;
    private int words;
    private int prefixes;
    private Node[] references;

    /**
     * Initialize a Node with an empty string and 0 weight; useful for
     * writing tests.
     */
    public Node() {
        this.term = new Term("", 0);
        this.words = 0;
        this.prefixes = 0;
        this.references = null;
    }

    /**
     * Initialize a Node with the given query string and weight.
     * @throws IllegalArgumentException if query is null or if weight is negative.
     */
    public Node(String query) {
        if (query == null) {
            throw new IllegalArgumentException("The term shouldn't be null");
        }

        this.term = new Term(query, 0);
        this.words = 0;
        this.prefixes = 0;
        this.references = null;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public int getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(int prefixes) {
        this.prefixes = prefixes;
    }

    public Node[] getReferences() {
        return references;
    }

    public void setReferences(Node[] references) {
        this.references = references;
    }
}
