/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices, edges) = a graph where 'vertices' is the set of all vertices and 'edges' contains all edges between vertices with specific weights.
    // Representation invariant:
    //   - For every edge in 'edges', both edge.getSource() and edge.getTarget() are in 'vertices'.
    //   - No two edges in 'edges' have the same source and target.
    // Safety from rep exposure:
    //   - 'vertices' and 'edges' are private and final.
    //   - Methods return copies of collections to avoid exposing internal references.

    /**
     * Create a new empty graph.
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }

    /**
     * Check the representation invariant.
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) : "Source vertex not in vertices";
            assert vertices.contains(edge.getTarget()) : "Target vertex not in vertices";
        }

        // Ensure no duplicate edges
        Set<String> edgeDescriptions = new HashSet<>();
        for (Edge edge : edges) {
            String edgeDescription = edge.toString();
            assert edgeDescriptions.add(edgeDescription) : "Duplicate edge detected: " + edgeDescription;
        }
    }

    @Override
    public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {
        // Add vertices if they don't exist
        vertices.add(source);
        vertices.add(target);

        // Check for existing edge and remove it
        int previousWeight = 0;
        Iterator<Edge> edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                previousWeight = edge.getWeight();
                edgeIterator.remove();
                break;
            }
        }

        // Add new edge if weight is non-zero
        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }

        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        boolean removed = vertices.remove(vertex);

        if (removed) {
            edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        }

        checkRep();
        return removed;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices); // Return a copy to preserve encapsulation
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Graph:\n");
        result.append("Vertices:\n").append(vertices).append("\n");
        result.append("Edges:\n");
        for (Edge edge : edges) {
            result.append(edge).append("\n");
        }
        return result.toString();
    }
}

/**
 * Represents an edge in a graph.
 * 
 * <p>Immutable. Weight must be positive. Source and target must be non-null strings.
 * This class is internal to the representation of ConcreteEdgesGraph.
 */
class Edge {

    private final String source;
    private final String target;
    private final int weight;

    // Abstraction function:
    //   AF(source, target, weight) = an edge from 'source' to 'target' with weight 'weight' in the graph.
    // Representation invariant:
    //   - weight > 0
    //   - source != null
    //   - target != null
    // Safety from rep exposure:
    //   - All fields are private and final.

    /**
     * Create a new edge.
     * 
     * @param source the source vertex
     * @param target the target vertex
     * @param weight the weight of the edge
     */
    public Edge(String source, String target, int weight) {
        this.source = Objects.requireNonNull(source, "Source cannot be null");
        this.target = Objects.requireNonNull(target, "Target cannot be null");
        if (weight <= 0) throw new IllegalArgumentException("Weight must be positive");
        this.weight = weight;
        checkRep();
    }

    private void checkRep() {
        assert source != null : "Source must not be null";
        assert target != null : "Target must not be null";
        assert weight > 0 : "Weight must be positive";
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " -> " + target + " : " + weight;
    }
}
