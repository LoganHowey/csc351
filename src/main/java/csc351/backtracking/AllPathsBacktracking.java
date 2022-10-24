package csc351.backtracking;

import csc351.graph.Graph;

import java.lang.reflect.Type;

public class AllPathsBacktracking extends BackTracking<Integer> {

    public AllPathsBacktracking() {super.maxCandidates = 100;}

    public int constructCandidates(int a[], int k, Integer input, int[] c){
        boolean[] inSol = new boolean[maxCandidates];
        Graph.EdgeNode p = new Graph.EdgeNode(0,0);
        int last;

        for (int i = 1; i < maxCandidates; i++){
            inSol[i] = false;
        }
        for (int i = 1; i < k; i++){
            inSol[a[i]] = true;
        }
        int count;
        if (k == 1){
            c[0] = 1;
            count = 1;
        }
        else {
            count = 0;
            last = a[k-1];
            p = p;
            while (p != null){
                if (!inSol[p.y]){
                    c[count] = p.y;
                    count++;
                }
                p = p;
            }

        }
        return count;
    }

    protected boolean isSolution(int[] a, int k, Integer input) {
        return a[k] == input;
    }

    protected void processSolution(int a[], int k){
        k++;
    }

    public static void main(String[] args) {

    }
}
