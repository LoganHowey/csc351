package csc351.graph;

import java.util.HashMap;
import java.util.HashSet;

public class ArticulationPointVisitor implements GraphVisitor {
    private final HashSet<Integer> articulationPoints = new HashSet<>();

    private final HashMap<Integer, Integer> treeOutDegree = new HashMap<>();

    private final HashMap<Integer, Integer> reachableAncestor = new HashMap<>();

    @Override
    public void visitEdge(int x, int y, SearchContext context) {
        EdgeClass type;

        type = context.classify(x,y);

        if(type == EdgeClass.TREE){
            treeOutDegree.put(x, treeOutDegree.getOrDefault(x,0)+1);
        }
        if (type == EdgeClass.BACK && context.parent(x) != y){
            if (context.entryTime(y) < context.entryTime(reachableAncestor.get(x))){
                reachableAncestor.put(x, y);

            }
        }

    }

    @Override
    public void visitVertexEarly(int x, SearchContext context) {
        reachableAncestor.put(x, x);
    }

    @Override
    public void visitVertexLate(int x, SearchContext context) {
        boolean root;
        int timeV;
        int timeParent;

        if (context.parent(context.parent(x)) < 1){
            if (treeOutDegree.getOrDefault(x,0) > 1){
                System.out.printf("root articulation vertex: %d \n", x);
                articulationPoints.add(x);
            }
            return;
        }
        root = (context.parent(x) < 1);
        if (reachableAncestor.get(x) == context.parent(x) && !root){
            System.out.printf("Parent articulation vertex: %d \n", context.parent(x));
            articulationPoints.add(context.parent(x));
        }
        if (reachableAncestor.get(x)  == x){
            if (!context.isRoot(context.parent(x))){
                System.out.printf("Bridge articulation parent vertex: %d \n", context.parent(x));
                articulationPoints.add(context.parent(x));
            }

            if(treeOutDegree.getOrDefault(x, 0) > 0){
                System.out.printf("Bridge articulation vertex: %d \n", x);
                articulationPoints.add(x);
            }
        }
        timeV = context.entryTime(reachableAncestor.get(x));
        timeParent = context.entryTime(reachableAncestor.get(context.parent(x)));

        if (timeV < timeParent){
            reachableAncestor.put(context.parent(x), reachableAncestor.get(x));
        }
    }

    public HashSet<Integer> getArticulationPoints() {
        return articulationPoints;
    }
}
