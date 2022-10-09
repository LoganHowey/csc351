package csc351.graph;

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
//      todo implement this

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
        // todo implement this
    }


    public PrimResult prim(int start) {
        // todo implement this
        return new PrimResult(new HashMap<>(), new HashMap<>());
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
