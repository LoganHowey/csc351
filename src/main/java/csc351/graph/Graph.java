package csc351.graph;

import javax.naming.Context;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private final HashMap<Integer, ArrayList<EdgeNode>> edges = new HashMap<>();
    private final HashMap<Integer, Integer> degrees = new HashMap<>();
    private final HashSet<Integer> vertices = new HashSet<>();
    private int numberOfEdges = 0;
    public final boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
    }

    public void insertEdge(int x, int y) {
        insertEdge(x, y, directed, null);
    }

    public void insertEdge(int x, int y, int weight) {
        insertEdge(x, y, directed, weight);
    }

    private void insertEdge(int x, int y, boolean directed, Integer weight) {
        EdgeNode edge = new EdgeNode(y, weight);

        edges
                .computeIfAbsent(x, key -> new ArrayList<>())
                .add(edge);
        degrees.put(
                x,
                degrees.computeIfAbsent(x, (key) -> 0)
        );
        vertices.add(x);
        vertices.add(y);

        if (!directed) {
            insertEdge(y, x, true, weight);
        } else {
            numberOfEdges++;
        }
    }

    public void traverseBreadthFirst(int startingVertex, GraphVisitor visitor) {
        Queue<Type> q = new LinkedList<>();
    }

    public void traverseDepthFirst(int startingVertex, GraphVisitor visitor) {
        traverseDepthFirst(startingVertex, visitor, new GraphVisitor.SearchContext(directed));
    }

    public Set<Integer> findArticulationPoints() {
        ArticulationPointVisitor finder = new ArticulationPointVisitor();

        GraphVisitor.SearchContext context = new GraphVisitor.SearchContext(directed);
        while (!context.getDiscovered().containsAll(vertices)) {
            Integer vertex = vertices.stream().filter(i -> !context.getDiscovered().contains(i)).findFirst().orElseThrow();
            traverseDepthFirst(vertex, finder, context);
        }

        return finder.getArticulationPoints();
    }

    private void traverseDepthFirst(Integer vertex, GraphVisitor visitor, GraphVisitor.SearchContext context) {
        context.setDiscovered(vertex);
        context.updateEntryTime(vertex);
        visitor.visitVertexEarly(vertex, context);
        for (EdgeNode edgeNode : edges.getOrDefault(vertex, new ArrayList<>())) {
            if (!context.discovered(edgeNode.y)){
                context.setParent(edgeNode.y, vertex);
                visitor.visitEdge(vertex, edgeNode.y, context);
                traverseDepthFirst(edgeNode.y, visitor, context);
            }
            else if(!context.processed(edgeNode.y) || directed){
                visitor.visitEdge(vertex, edgeNode.y, context);
            }

            visitor.visitVertexLate(vertex, context);
            context.updateExitTime(vertex);
            context.setProcessed(vertex);
        }

    }



    public PrimResult prim(int start) {
        PrimResult primResult = new PrimResult(new HashMap<>(), new HashMap<>());
        boolean intree[] = new boolean[100];
        int v;
        int w;
        int weight;
        int dist;

        for (int i = 1; i <= vertices.size(); i++){
            intree[i] = false;
            primResult.distance.put(i, getMaxWeight());
            primResult.parents.put(i, -1);
        }

        primResult.distance.put(start, 0);
        v = start;

        while (!intree[v]){
            intree[v] = true;
            for (EdgeNode edgeNode : edges.getOrDefault(v, new ArrayList<>())){
                w = edgeNode.y;
                weight = edgeNode.weight;
                if ((primResult.distance.get(w) > weight) && !intree[w]){
                    primResult.distance.put(w, weight);
                    primResult.parents.put(w, v);
                }
            }
            v = 1;
            dist = getMaxWeight();
            for (int i = 1; i < vertices.size(); i++){
                if ((!intree[i]) && (dist > primResult.distance.get(i))){
                    dist = primResult.distance.get(i);
                    v = i;
                }
            }
        }
        // todo implement this
        return primResult;

    }

    public int getMaxWeight(){
        int i = 0;
        int max = 0;
        for (EdgeNode edgeNode : edges.getOrDefault(i, new ArrayList<>())){
            if (edgeNode.weight > max){
                max = edgeNode.weight;
            }
        }
        return max + 8; // I know this isn't actually doing anything but i don't know how to access the edges hashmap from here
    }

    public List<EdgeNode> findEdges(int vertex){
        return edges.getOrDefault(vertex, new ArrayList<>());
    }

    public static class PrimResult {
        private final HashMap<Integer, Integer> distance;
        private final HashMap<Integer, Integer> parents;

        public PrimResult(HashMap<Integer, Integer> distance, HashMap<Integer, Integer> parents) {
            this.distance = distance;
            this.parents = parents;
        }

        public HashMap<Integer, Integer> getDistance() {
            return distance;
        }

        public HashMap<Integer, Integer> getParents() {
            return parents;
        }
    }

    @Override
    public String toString() {
        String edgeList = edges
                .entrySet()
                .stream().flatMap(i -> {
                    Integer x = i.getKey();
                    return i.getValue().stream().map(e -> "[" + x + " " + e.y + "]");
                })
                .collect(Collectors.joining(", "));

        return "{ 'edges': " + numberOfEdges + ", 'vertices':" + vertices.size() + " 'edges': " + edgeList + "}";
    }

    public static class EdgeNode {
        public Integer y;
        public Integer weight;

        public EdgeNode(Integer y, Integer weight) {
            this.y = y;
            this.weight = weight;
        }
    }
}

/*
implimented
[X] DepthFirst
[] BreadthFirst
[] Articulation Points
[X] Prims
[X] Floyds
[X] All Paths
 */