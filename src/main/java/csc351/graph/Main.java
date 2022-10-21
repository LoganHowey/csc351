package csc351.graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(false);
        graph.insertEdge(1, 2);
        graph.insertEdge(2, 3);
        graph.insertEdge(3, 4);
        graph.insertEdge(3, 5);
        graph.insertEdge(4, 5);

        graph.traverseDepthFirst(1, new PrintGraphVisitor());
    }
}
