package csc351.backtracking;

import csc351.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class AllPathsBacktracking extends BackTracking<Integer> {

    private final HashMap<Integer, ArrayList<Graph.EdgeNode>> edges = new HashMap<>();

    public AllPathsBacktracking() {
        super.maxCandidates = 100;
    }

    public int constructCandidates(int a[], int k, Integer input, int[] c) {
        Graph graph = new Graph(false);
        boolean[] inSol = new boolean[maxCandidates];
        int last;

        for (int i = 1; i < maxCandidates; i++) {
            inSol[i] = false;
        }
        for (int i = 1; i < k; i++) {
            inSol[a[i]] = true;
        }
        int count;
        if (k == 1) {
            c[0] = 1;
            count = 1;
        } else {
            count = 0;
            last = a[k - 1];
            for (Graph.EdgeNode edgeNode : edges.getOrDefault(last, new ArrayList<>())) {
                if (!inSol[edgeNode.y]) {
                    c[count] = edgeNode.y;
                    count++;
                }
            }
        }
        return count;
    }

    protected boolean isSolution(int[] a, int k, Integer input) {
        return a[k] == input;
    }

    protected void processSolution(int a[], int k) {
        k++;
    }

    public static void main(String[] args) {

    }
}
