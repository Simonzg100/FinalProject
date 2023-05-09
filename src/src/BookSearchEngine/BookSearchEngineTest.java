package src.BookSearchEngine;//package src.BookSearchEngine;

import static org.junit.Assert.*;


import src.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookSearchEngineTest {
    @org.junit.jupiter.api.Test
    void testSearchByName() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        BookSearchEngine bookSearchEngine = new BookSearchEngine((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.add(new Book("1984", "George Orwell", "Fiction", 8));
        //expected.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));

        List<Book> actual = bookSearchEngine.search("19", Term.Type.NAME);

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < actual.size(); i++) {
            Book b1 = expected.get(i);
            Book b2 = actual.get(i);
            assertTrue(bookCompare(b1, b2));
        }
    }

    private boolean bookCompare(Book b1, Book b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return b1.getName().equals(b2.getName())
                && b1.getAuthor().equals(b2.getAuthor())
                && b1.getStockQuantity() == b2.getStockQuantity()
                && b1.getIsbn() == b2.getIsbn()
                && b1.getCategory().equals(b2.getCategory());
    }

    @org.junit.jupiter.api.Test
    void testSearchByAuthor() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        BookSearchEngine bookSearchEngine = new BookSearchEngine((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));

        List<Book> actual = bookSearchEngine.search("h", Term.Type.AUTHOR);

        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < actual.size(); i++) {
            Book b1 = expected.get(i);
            Book b2 = actual.get(i);
            assertTrue(bookCompare(b1, b2));
        }
    }

//    @org.junit.jupiter.api.Test
//    void testSearchByCategory() {
//        List<Book> books = new ArrayList<>();
//        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
//        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
//        books.add(new Book("1984", "George Orwell", "Fiction", 8));
//        BookSearchEngine bookSearchEngine = new BookSearchEngine((ArrayList<Book>) books);
//
//        List<Book> expected = new ArrayList<>();
//        expected.add(new Book("1984", "George Orwell", "Fiction", 8));
//        expected.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
//        expected.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
//
//        List<Book> actual = bookSearchEngine.search("f", Term.Type.CATEGORY);
//        assertEquals(expected.size(), actual.size());
//
//        assertEquals(actual.get(0).getName(), "To Kill a Mockingbird");
//        assertEquals(actual.get(1).getName(), "1984");
//        assertEquals(actual.get(2).getName(), "The Great Gatsby");
//
//    }

    @org.junit.jupiter.api.Test
    void testSearchEmptyQuery() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 10));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5));
        books.add(new Book("1984", "George Orwell", "Fiction", 8));
        BookSearchEngine bookSearchEngine = new BookSearchEngine((ArrayList<Book>) books);

        List<Book> expected = new ArrayList<>();
        expected.addAll(books);

        List<Book> actual = bookSearchEngine.search("", Term.Type.NAME);

        assertEquals(new ArrayList<>(), actual);
    }
}





