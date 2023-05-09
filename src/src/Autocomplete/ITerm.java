package src.Autocomplete;

import java.util.Comparator;

/**
 * @author ericfouh
 */
public interface ITerm
    extends Comparable<ITerm> {

    /**
     * Compares the two terms in descending order by weight.
     * 
     * @return comparator Object
     */
    public static Comparator<ITerm> byReverseWeightOrder() {
        return new Comparator<ITerm>() {
            @Override
            public int compare(ITerm o1, ITerm o2) {
                return (int) (o2.getWeight() - o1.getWeight());
            }
        };
    }


    /**
     * Compares the two terms in lexicographic order but using only the first r
     * characters of each query.
     * 
     * @param r
     * @return comparator Object
     */
    public static Comparator<ITerm> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("The length of prefix should be positive");
        }
        return new Comparator<ITerm>() {
            @Override
            public int compare(ITerm o1, ITerm o2) {
                int res = 0;
                int minLength = Math.min(o1.getTerm().length(), o2.getTerm().length());
                for (int i = 0; i < Math.min(r, minLength); i++) {
                    res += o1.getTerm().charAt(i) - o2.getTerm().charAt(i);
                }
                return res;
            }
        };
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(ITerm that);


    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString();

    // Required getters.
    public long getWeight();
    public String getTerm();

    // Required setters (mostly for autograding purposes)
    public void setWeight(long weight);
    public String setTerm(String term);

}
