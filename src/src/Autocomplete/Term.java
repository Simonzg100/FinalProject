package src.Autocomplete;

import java.util.ArrayList;

public class Term implements ITerm{

    private String term;
    private long weight;
    public ArrayList<String> isbnList;


    /**
     * Initialize a Term with a given query String and weight
     */
    public Term(String term, long weight) {
        if (term == null) {
            throw new IllegalArgumentException("The term shouldn't be null");
        }

        this.term = term;
        this.weight = weight;

        this.isbnList = new ArrayList<>();
    }

    public int compareTo(Term that) {
        return this.term.compareTo(that.getTerm());
    }

    @Override
    public String getTerm() {
        return this.term;
    }

    @Override
    public void setWeight(long weight) {

    }

    @Override
    public String setTerm(String term) {
        return this.term = term;
    }

    @Override
    public int compareTo(ITerm that) {
        return 0;
    }

    @Override
    public String toString() {
        return this.term + '\t' + this.weight;
    }

    @Override
    public long getWeight() {
        return 0;
    }

    public void addBook(String isbn) {
        if (!this.isbnList.contains(isbn)) this.isbnList.add(isbn);
    }

}
