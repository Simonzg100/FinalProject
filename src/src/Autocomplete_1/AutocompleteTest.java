package src.Autocomplete_1;

import static org.junit.Assert.*;


import org.junit.Test;
import src.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AutocompleteTest {
    @org.junit.jupiter.api.Test
    void testSearchByName() {
        List<Book> books = new ArrayList<>();
        //books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        //books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        Autocomplete autocomplete = new Autocomplete((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.add(new Book("1984", "George Orwell", "Fiction", 8));
        //expected.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));

        List<Book> actual = autocomplete.search("1", Term.Type.NAME);

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < actual.size(); i++) {
            Book b1 = expected.get(i);
            Book b2 = expected.get(i);
            assertTrue(bookCompare(b1, b2));
        }
    }

    private boolean bookCompare(Book b1, Book b2) {
        if(b1 == null || b2 == null) {
            return false;
        }
        return b1.getName() == b2.getName() && b1.getAuthor() == b2.getAuthor()
                && b1.getStockQuantity() == b2.getStockQuantity()
                && b1.getIsbn() == b2.getIsbn()
                && b1.getCategory() == b2.getCategory();
    }

    @org.junit.jupiter.api.Test
    void testSearchByAuthor() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        Autocomplete autocomplete = new Autocomplete((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));

        List<Book> actual = autocomplete.search("h", Term.Type.AUTHOR);

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < actual.size(); i++) {
            Book b1 = expected.get(i);
            Book b2 = expected.get(i);
            assertTrue(bookCompare(b1, b2));
        }
    }

    @org.junit.jupiter.api.Test
    void testSearchByCategory() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        Autocomplete autocomplete = new Autocomplete((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.add(new Book("1984", "George Orwell", "Fiction", 8));
        expected.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        expected.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));

        List<Book> actual = autocomplete.search("f", Term.Type.CATEGORY);
        assertEquals(expected.size(), actual.size());

        assertEquals(actual.get(0).getName(), "To Kill a Mockingbird");
        assertEquals(actual.get(1).getName(), "1984");
        assertEquals(actual.get(2).getName(), "The Great Gatsby");

    }

    @org.junit.jupiter.api.Test
    void testSearchEmptyQuery() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        Autocomplete autocomplete = new Autocomplete((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.addAll(books);

        List<Book> actual = autocomplete.search("", Term.Type.NAME);

        assertEquals(new ArrayList<>(), actual);
    }
}

