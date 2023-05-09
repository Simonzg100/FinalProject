package src;

import java.util.HashMap;

public class UnionFind {

    private int count;

    private HashMap<String, String> parent;

    public UnionFind(int n) {
        parent = new HashMap<>(n);
    }

    public void add(String s) {
        if (!parent.containsKey(s)) {
            parent.put(s, s);
            count++;
        }
    }


    public boolean union(String p, String q) {
        String rootP = find(p);
        String rootQ = find(q);

        if (rootP.equals(rootQ))
            return false;

        parent.put(rootQ, rootP);

        count--;

        return true;
    }

    public boolean connected(String p, String q) {
        String rootP = find(p);
        String rootQ = find(q);
        return rootP.equals(rootQ);
    }

    public String find(String x) {
        System.out.println("Finding: " + x);
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        System.out.println("Found parent: " + parent.get(x));
        return parent.get(x);
    }

    public int count() {
        return count;
    }
}
