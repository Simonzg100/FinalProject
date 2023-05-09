package src.Autocomplete;

import src.Book;

import java.util.List;

/**
 * @author ericfouh
 */
public interface IAutocomplete {

    /**
     * Adds a new word with its associated weight to the Trie.
     * If the word contains an invalid character, simply do nothing.
     * 
     * @param book the book to be added to the Tries
     * @param weight the weight of the word
     */
    public void addWord(Book book, long weight);


    /**
     * Initializes the Trie
     *
     * @param filename the file to read all the autocomplete data from each line
     *                 contains a word and its weight This method will call the
     *                 addWord method
     * @param k the maximum number of suggestions that should be displayed 
     * @return the root of the Trie You might find the readLine() method in
     *         BufferedReader useful in this situation as it will allow you to
     *         read a file one line at a time.
     */
    public void buildTrie(String filename, int k);


    /**
     * @param prefix
     * @return the root of the subTrie corresponding to the last character of
     *         the prefix. If the prefix is not represented in the trie, return null.
     */
    public Node getSubTrie(String prefix, int indicator);


    /**
     * @param prefix
     * @return the number of words that start with prefix.
     */
    public int countPrefixes(String prefix, int indicator);


    /**
     * This method should not throw an exception.
     * Make sure that you preserve encapsulation by returning a list of copies of the
     * original Terms; otherwise, the user might be able to change the structure of your
     * Trie based on the values returned.
     *
     * @param prefix
     * @return a List containing all the ITerm objects with query starting with
     *         prefix. Return an empty list if there are no ITerm object starting
     *         with prefix.
     */
    public List<ITerm> getSuggestions(String prefix, int indicator);

}
