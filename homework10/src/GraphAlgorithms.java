import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start passed in is null, "
                    + "cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph passed in is null, "
                    + "cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start is not found in "
                    + "the graph");
        }
        List<Vertex<T>>  search = new ArrayList<>();
        Queue<Vertex<T>> level = new LinkedList<>();
        level.add(start);
        while (!level.isEmpty()) {
            if (!search.contains(level.peek())) {
                search.add(level.peek());
                for (VertexDistance<T> side
                        : graph.getAdjList().get(level.peek())) {
                    level.add(side.getVertex());
                }
            } else {
                level.remove();
            }
        }
        return search;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start passed in is null, "
                    + "cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph passed in is null, "
                    + "cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start is not found in"
                    + "the graph");
        }
        List<Vertex<T>> traverse = new ArrayList<>();
        dfsRecursive(start, graph, traverse);
        return traverse;
    }

    /**
     * Recursive helper method for depth first search
     *
     * @param <T> the generic typing of the data
     * @param currVertex the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param traverse the list to add the vertex to
     */
    private static <T> void dfsRecursive(
            Vertex<T> currVertex, Graph<T> graph, List<Vertex<T>> traverse) {
        traverse.add(currVertex);
        for (VertexDistance<T> side : graph.getAdjList().get(currVertex)) {
            if (!traverse.contains(side.getVertex())) {
                dfsRecursive(side.getVertex(), graph, traverse);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start passed in is null, "
                    + "cannot be null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph passed in is null, "
                    + "cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start is not found in"
                    + "the graph");
        }
        Map<Vertex<T>, Integer> cloud = new HashMap<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            //MAX_VALUE represents infinity
            cloud.put(vertex, Integer.MAX_VALUE);
        }
        cloud.put(start, 0);
        //confused as to why putting an if statement in the
        //for-each loop saying:
        //...
        //if (vertex == start) {
        //    cloud.put(start, 0);
        //} else {
        // ...
        //does not do the same thing as the code above.
        PriorityQueue<VertexDistance<T>> pQ = new PriorityQueue<>();
        pQ.add(new VertexDistance<>(start, 0));
        Set<Vertex<T>> alreadyVisited = new HashSet<>();
        //checks both terminating conditions
        while (!pQ.isEmpty() && !alreadyVisited.equals(graph.getVertices())) {
            VertexDistance<T> removed = pQ.remove();
            Vertex<T> u = removed.getVertex();
            if (!alreadyVisited.contains(u)) {
                alreadyVisited.add(u);
                int w = removed.getDistance();
                for (VertexDistance<T> side : graph.getAdjList().get(u)) {
                    Vertex<T> vertex = side.getVertex();
                    if (cloud.get(vertex) > (w + side.getDistance())) {
                        cloud.put(vertex, (w + side.getDistance()));
                        pQ.add(new VertexDistance<>(
                                vertex, (w + side.getDistance())));
                    }
                }
            }
        }
        return cloud;
    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph passed in is null, "
                    + "cannot be null");
        }
        DisjointSet<Vertex<T>> disSet = new DisjointSet<>(graph.getVertices());
        Set<Edge<T>> mST = new LinkedHashSet<>();
        PriorityQueue<Edge<T>> pQ = new PriorityQueue<>(graph.getEdges());
        //graph.getVertices().size() - 1 is multiplied by 2 because
        //it is undirected meaning it can go both ways on said graph
        while (!pQ.isEmpty()
                && (mST.size() != (2 * (graph.getVertices().size() - 1)))) {
            Edge<T> removed = pQ.remove();
            if (!disSet.find(removed.getV()).equals(
                    disSet.find(removed.getU()))) {
                disSet.union(removed.getU(), removed.getV());
                mST.add(removed);
                mST.add(new Edge<>(removed.getV(),
                        removed.getU(), removed.getWeight()));
            }
        }
        //checks to see if mST is valid
        if (mST.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mST;
    }
}
