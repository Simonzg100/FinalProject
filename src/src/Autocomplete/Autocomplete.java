package src.Autocomplete;

import src.Book;
import src.DataProcessor.DataProcessor;
import src.Tuple;

import java.util.*;

public class Autocomplete implements IAutocomplete{
    private Node[] nodeArr;
    private int k;
    private DataProcessor dp;

    public Autocomplete() {
        Node rootBook = new Node();
        Node rootAuthor = new Node();
        Node rootCategory = new Node();
        this.nodeArr = new Node[]{rootBook, rootAuthor, rootCategory};
    }



    @Override
    public void addWord(Book book, long weight) {
        String bookName = book.getName();
        String author = book.getAuthor();
        String category = book.getCategory();

        String[] strArr = {bookName, author, category};

        for (int i = 0; i < strArr.length; i++) {
            char[] charArray = strArr[i].replaceAll("[^a-zA-Z]", "").strip().toLowerCase().toCharArray();
            int[] arr = new int[charArray.length];

            Node temp;
            temp = this.nodeArr[i];

            for (int j = 0; j < charArray.length; j++) {
                arr[j] = charArray[j] - 97;
                if (arr[j] > 25 || arr[j] < 0) {
                    return;
                }
            }

            this.nodeArr[i].setPrefixes(this.nodeArr[i].getPrefixes() + 1);


            for (int j = 0; j < arr.length; j++) {

                Node[] nodeArr = temp.getReferences();

                if (nodeArr == null) {
                    nodeArr = new Node[26];
                }

                if (nodeArr[arr[j]] == null) {
                    nodeArr[arr[j]] = new Node();
                }

                Node n = nodeArr[arr[j]];
                n.setPrefixes(n.getPrefixes() + 1);

                if (j == arr.length - 1) {
                    if (n.getWords() == 1) {
                        n.getTerm().addBook(book.getIsbn());
                    } else {
                        n.setWords(1);
                        n.setTerm(new Term(strArr[i].trim(), 0));
                    }
                }

                nodeArr[arr[j]] = n;
                temp.setReferences(nodeArr);

                if (j == 0) {
                    this.nodeArr[i] = temp;
                }
                temp = n;
            }
        }

    }

    @Override
    public void buildTrie(String filename, int k) {
        Node rootBook = new Node();
        Node rootAuthor = new Node();
        Node rootCategory = new Node();
        this.nodeArr = new Node[]{rootBook, rootAuthor, rootCategory};
        this.k = k;
        this.dp = new DataProcessor();
        this.dp.initialization();
        for (Book b : this.dp.getBooksList()) {
            this.addWord(b, 0);
        }
    }

    @Override
    public Node getSubTrie(String prefix, int indicator) {
        char[] arr = prefix.trim().toLowerCase().toCharArray();
        Node temp = this.nodeArr[indicator];
        for (int i = 0; i < arr.length; i++) {
            Node[] nodeArr = temp.getReferences();
            int locator = arr[i] - 97;

            if (locator < 0 || locator > 25) {
                return null;
            }

            if (nodeArr == null) {
                return null;
            }

            if (nodeArr[locator] == null) {
                return null;
            }

            temp = temp.getReferences()[locator];
        }
        return temp;
    }

    @Override
    public int countPrefixes(String prefix, int indicator) {
        Node n = this.getSubTrie(prefix, indicator);
        if (n == null) {
            return 0;
        } else {
            return n.getPrefixes();
        }
    }

    @Override
    public List<ITerm> getSuggestions(String prefix, int indicator) {
        ArrayList<ITerm> iTermArrayList = new ArrayList<>();
        Node n = this.getSubTrie(prefix, indicator);
        if (n != null) {
            Queue<Node> q = new LinkedList<>();
            q.add(n);
            while (!q.isEmpty()) {
                Node temp = q.poll();
                if (temp.getWords() == 1) {
                    iTermArrayList.add(temp.getTerm());
                }

                if (temp.getReferences() != null) {
                    for (int i = 0; i < 26; i++) {
                        if (temp.getReferences()[i] != null) {
                            q.add(temp.getReferences()[i]);
                        }
                    }
                }
            }
        }

        if (iTermArrayList.size() == 0) return getAutocorrect(prefix, indicator);
        return iTermArrayList;
    }

    private List<ITerm> getAutocorrect (String input, int indicator) {
        List<Tuple<ITerm, Integer>> termDistances = new ArrayList<>();
        input = input.replaceAll("[^a-zA-Z]", "").strip().toLowerCase();
        Queue<Node> q = new LinkedList<>();
        for (int i = input.length(); i > 0; i--) {
            Node temp = this.getSubTrie(input.substring(0,i), indicator);
            if (temp == null) continue;
            if (temp.getPrefixes()!= 0) {
                q.add(temp);
                break;
            }
        }
        while (!q.isEmpty()) {
            Node temp = q.poll();
            if (temp.getWords() == 1) {
                int distance = calculateLevenshteinDistance(input, temp.getTerm().getTerm());
                termDistances.add(new Tuple<ITerm, Integer>(temp.getTerm(), distance));
            }

            if (temp.getReferences() != null) {
                for (int i = 0; i < 26; i++) {
                    if (temp.getReferences()[i] != null) {
                        q.add(temp.getReferences()[i]);
                    }
                }
            }
        }
        termDistances.sort(Comparator.comparingInt(Tuple::getRight));

        ArrayList<ITerm> iTermArrayList = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (i > termDistances.size()) break;
            iTermArrayList.add(termDistances.get(i).getLeft());
        }

        return iTermArrayList;

    }

    private int calculateLevenshteinDistance(String a, String b) {
        // Implementation of the Levenshtein distance
        a = a.replaceAll("[^a-zA-Z]", "").strip().toLowerCase();
        b = b.replaceAll("[^a-zA-Z]", "").strip().toLowerCase();

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j; // Min. operations = j
                } else if (j == 0) {
                    dp[i][j] = i; // Min. operations = i
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(a.charAt(i - 1), b.charAt(j - 1)),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[a.length()][b.length()];
    }

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
}
