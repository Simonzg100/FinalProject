package src;
import src.Book;
import src.BookSearchEngine.BookSearchEngine;
import src.BookSearchEngine.Term;
import src.DataProcessor.DataProcessor;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookSearchEngineGUI extends JFrame {
    private BookSearchEngine bookSearchEngine;
    private JTextField searchTextField;
    private JRadioButton authorRadioButton;
    private JRadioButton categoryRadioButton;
    private JRadioButton nameRadioButton;
    private ButtonGroup radioGroup;
    private JEditorPane resultsEditorPane;
    private JEditorPane fuzzyResultsEditorPane;

    public BookSearchEngineGUI(ArrayList<Book> books) {
        createGUIComponents();
        initializeBookSearchEngine(books);
    }


    private void createGUIComponents() {
        setTitle("Book Search Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        searchTextField = new JTextField(20);
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        mainPanel.add(searchTextField, BorderLayout.NORTH);

        JPanel radioPanel = new JPanel();
        radioGroup = new ButtonGroup();
        nameRadioButton = new JRadioButton("Name");
        authorRadioButton = new JRadioButton("Author");
        //categoryRadioButton = new JRadioButton("Category");
        radioGroup.add(nameRadioButton);
        radioGroup.add(authorRadioButton);
        //radioGroup.add(categoryRadioButton);
        radioPanel.add(nameRadioButton);
        radioPanel.add(authorRadioButton);
        //radioPanel.add(categoryRadioButton);
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


        resultsEditorPane = new JEditorPane();
        resultsEditorPane.setContentType("text/html");
        resultsEditorPane.setEditable(false);
        resultsEditorPane.addHyperlinkListener(createHyperlinkListener());
        JScrollPane scrollPane = new JScrollPane(resultsEditorPane);

        JLabel regularSearchLabel = new JLabel("Regular Search");
        regularSearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel regularSearchPanel = new JPanel(new BorderLayout());
        regularSearchPanel.add(regularSearchLabel, BorderLayout.NORTH);
        regularSearchPanel.add(scrollPane, BorderLayout.CENTER);

        fuzzyResultsEditorPane = new JEditorPane();
        fuzzyResultsEditorPane.setContentType("text/html");
        fuzzyResultsEditorPane.setEditable(false);
        fuzzyResultsEditorPane.addHyperlinkListener(createHyperlinkListener());
        JScrollPane fuzzyScrollPane = new JScrollPane(fuzzyResultsEditorPane);

        JLabel fuzzySearchLabel = new JLabel("Fuzzy Search");
        fuzzySearchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel fuzzySearchPanel = new JPanel(new BorderLayout());
        fuzzySearchPanel.add(fuzzySearchLabel, BorderLayout.NORTH);
        fuzzySearchPanel.add(fuzzyScrollPane, BorderLayout.CENTER);

        JPanel resultsPanel = new JPanel(new GridLayout(1, 2));
        resultsPanel.add(regularSearchPanel);
        resultsPanel.add(fuzzySearchPanel);

        getContentPane().add(mainPanel, BorderLayout.NORTH);
        getContentPane().add(resultsPanel, BorderLayout.CENTER);
    }


    private void initializeBookSearchEngine(List<Book> books) {
        bookSearchEngine = new BookSearchEngine((ArrayList<Book>) books);
    }


    private void performSearch() {
        String searchText = searchTextField.getText();


        Term.Type searchType = Term.Type.NAME;
        if (authorRadioButton.isSelected()) {
            searchType = Term.Type.AUTHOR;
        }

        List<Book> searchResults = bookSearchEngine.search(searchText, searchType);
        List<Book> fuzzySearchResults = bookSearchEngine.fuzzySearch(searchText, searchType);

        displaySearchResults(searchResults, fuzzySearchResults);
    }


    private void displaySearchResults(List<Book> searchResults, List<Book> fuzzySearchResults) {
        String regularResultsHtml = generateResultsHTML(searchResults);
        String fuzzyResultsHtml = generateResultsHTML(fuzzySearchResults);

        resultsEditorPane.setText(regularResultsHtml);
        resultsEditorPane.setCaretPosition(0);

        fuzzyResultsEditorPane.setText(fuzzyResultsHtml);
        fuzzyResultsEditorPane.setCaretPosition(0);
    }

    private String generateResultsHTML(List<Book> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");

        for (Book book : results) {
            sb.append("Book name: " + book.getName() + "<br>");
            sb.append("Author: " + book.getAuthor() + "<br>");
            sb.append("Category: " + book.getCategory() + "<br>");
            sb.append("URL: <a href=\"" + book.getUrl() + "\">" + book.getUrl() + "</a><br>");
            sb.append("-------------------------------------------------<br>");
        }

        sb.append("</body></html>");
        return sb.toString();
    }

    private HyperlinkListener createHyperlinkListener() {
        return new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataProcessor dataProcessor = new DataProcessor();
                dataProcessor.initialization();
                ArrayList<Book> books = dataProcessor.getBooksList();

                BookSearchEngineGUI gui = new BookSearchEngineGUI(books);
                gui.setVisible(true);
            }
        });
    }
}