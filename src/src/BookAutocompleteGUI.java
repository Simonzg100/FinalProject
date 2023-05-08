
package src;

import src.Autocomplete_1.Autocomplete;
import src.Autocomplete_1.Term;
import src.DataProcessor.DataProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookAutocompleteGUI extends JFrame {
    private Autocomplete autocomplete;
    private JTextField searchTextField;
    private JRadioButton authorRadioButton;
    private JRadioButton categoryRadioButton;
    private JRadioButton nameRadioButton;
    private ButtonGroup radioGroup;
    private JTextArea resultsTextArea;

    public BookAutocompleteGUI(ArrayList<Book> books) {
        createGUIComponents();
        initializeAutocomplete(books);
    }

    private void createGUIComponents() {
        setTitle("Book Search Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        searchTextField = new JTextField(20);
        mainPanel.add(searchTextField, BorderLayout.NORTH);

        JPanel radioPanel = new JPanel();
        radioGroup = new ButtonGroup();
        nameRadioButton = new JRadioButton("Name");
        authorRadioButton = new JRadioButton("Author");
        categoryRadioButton = new JRadioButton("Category");
        radioGroup.add(nameRadioButton);
        radioGroup.add(authorRadioButton);
        radioGroup.add(categoryRadioButton);
        radioPanel.add(nameRadioButton);
        radioPanel.add(authorRadioButton);
        radioPanel.add(categoryRadioButton);
        nameRadioButton.setSelected(true);
        mainPanel.add(radioPanel, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        mainPanel.add(searchButton, BorderLayout.SOUTH);

        resultsTextArea = new JTextArea();
        resultsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);
        getContentPane().add(mainPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeAutocomplete(List<Book> books) {
        autocomplete = new Autocomplete((ArrayList<Book>) books);
    }

    private void performSearch() {
        String searchText = searchTextField.getText();
        Term.Type searchType = Term.Type.NAME;
        if (authorRadioButton.isSelected()) {
            searchType = Term.Type.AUTHOR;
        } else if (categoryRadioButton.isSelected()) {
            searchType = Term.Type.CATEGORY;
        }

        List<Book> searchResults = autocomplete.search(searchText, searchType);
        displaySearchResults(searchResults);
    }


    private void displaySearchResults(List<Book> searchResults) {
        resultsTextArea.setText("");
        resultsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        for (Book book : searchResults) {
            resultsTextArea.append("Book name: " + book.getName() + "\n");
            resultsTextArea.append("Author: " + book.getAuthor() + "\n");
            resultsTextArea.append("Category: " + book.getCategory() + "\n");
            resultsTextArea.append("-------------------------------------------------\n");
        }
        resultsTextArea.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataProcessor dataProcessor = new DataProcessor();
                dataProcessor.initialization();
                ArrayList<Book> books = dataProcessor.getBooksList();

                BookAutocompleteGUI gui = new BookAutocompleteGUI(books);
                gui.setVisible(true);
            }
        });
    }
}
