package src.BookSearchEngine;

import src.Book;
import java.util.Comparator;


public class Term implements Comparable<Term> {
    private Book book;

    public Term(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public enum Type {
        NAME, AUTHOR, CATEGORY
    }

    public int getWeight() {
        return book.getStockQuantity();
    }


    public static Comparator<Term> byReverseWeightOrder() {
        return new Comparator<Term>() {
            public int compare(Term a, Term b) {

                if (a.getWeight() == b.getWeight()) {
                    return 0;
                } else if (a.getWeight() > b.getWeight()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

    }

    @Override
    /**
     * rewrite the compareTo method to order the book by their stock quantity reverse
     */
    public int compareTo(Term that) {
        return Integer.compare(that.book.getStockQuantity(), this.book.getStockQuantity());
    }
}