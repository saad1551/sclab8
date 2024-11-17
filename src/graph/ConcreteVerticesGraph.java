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
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   Represents a graph where each Vertex object in 'vertices' contains a vertex and its outgoing edges.
    // Representation invariant:
    //   - No two vertices in 'vertices' have the same source, i.e., vertices are unique.
    // Safety from rep exposure:
    //   - 'vertices' is private and final.
    //   - Only copies of vertex labels and edge mappings are exposed.

    /**
     * Create a new empty graph.
     */
    public ConcreteVerticesGraph() {
        checkRep();
    }

    /**
     * Check the representation invariant.
     * @throws AssertionError if the representation invariant is violated
     */
    private void checkRep() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            String label = vertex.getSource();
            assert vertexLabels.add(label) : "Duplicate vertex detected: " + label;
        }
    }

    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getSource().equals(vertex)) {
                return false; // Vertex already exists
            }
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = findOrCreateVertex(source);
        findOrCreateVertex(target); // Ensure target vertex exists

        int previousWeight = sourceVertex.getOutEdges().getOrDefault(target, 0);

        if (weight == 0) {
            sourceVertex.removeOutEdge(target);
        } else {
            sourceVertex.addOutEdge(target, weight);
        }

        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        Vertex vertexToRemove = findVertex(vertex);
        if (vertexToRemove == null) {
            return false;
        }

        vertices.remove(vertexToRemove);

        for (Vertex v : vertices) {
            v.removeOutEdge(vertex); // Remove all edges pointing to or from this vertex
        }

        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        Set<String> result = new HashSet<>();
        for (Vertex v : vertices) {
            result.add(v.getSource());
        }
        return result;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            Map<String, Integer> outEdges = v.getOutEdges();
            if (outEdges.containsKey(target)) {
                sources.put(v.getSource(), outEdges.get(target));
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Vertex sourceVertex = findVertex(source);
        return (sourceVertex != null) ? sourceVertex.getOutEdges() : new HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex v : vertices) {
            result.append(v).append("\n");
        }
        return result.toString();
    }

    private Vertex findVertex(String label) {
        for (Vertex v : vertices) {
            if (v.getSource().equals(label)) {
                return v;
            }
        }
        return null;
    }

    private Vertex findOrCreateVertex(String label) {
        Vertex vertex = findVertex(label);
        if (vertex == null) {
            vertex = new Vertex(label);
            vertices.add(vertex);
        }
        return vertex;
    }
}

/**
 * Represents a vertex in the graph.
 * 
 * <p>Mutable. The source is the vertex label, and outEdges maps target vertices to their weights.
 */
class Vertex {

    private final String source;
    private final Map<String, Integer> outEdges;

    // Abstraction function:
    //   Represents a vertex in a graph, where 'source' is the vertex label, and 'outEdges'
    //   is a map of edges with target vertices and their corresponding weights.
    // Representation invariant:
    //   - source is non-null.
    //   - outEdges does not contain null keys or values, and all weights are > 0.
    // Safety from rep exposure:
    //   - Fields are private and final where applicable.
    //   - outEdges is exposed only as a copy to prevent external modification.

    /**
     * Create a new vertex.
     * 
     * @param source the label of the vertex
     */
    Vertex(String source) {
        this.source = Objects.requireNonNull(source, "Source cannot be null");
        this.outEdges = new HashMap<>();
        checkRep();
    }

    private void checkRep() {
        assert source != null : "Source cannot be null";
        for (Map.Entry<String, Integer> entry : outEdges.entrySet()) {
            assert entry.getKey() != null : "Target cannot be null";
            assert entry.getValue() > 0 : "Weight must be positive";
        }
    }

    public String getSource() {
        return source;
    }

    public Map<String, Integer> getOutEdges() {
        return new HashMap<>(outEdges); // Return a copy to preserve encapsulation
    }

    public void addOutEdge(String target, int weight) {
        if (weight <= 0) throw new IllegalArgumentException("Weight must be positive");
        outEdges.put(target, weight);
        checkRep();
    }

    public void removeOutEdge(String target) {
        outEdges.remove(target);
        checkRep();
    }

    @Override
    public String toString() {
        if (outEdges.isEmpty()) {
            return source + " has no outgoing edges.";
        }
        StringBuilder result = new StringBuilder(source + " -> ");
        outEdges.forEach((target, weight) -> result.append(target).append(" (").append(weight).append("), "));
        result.setLength(result.length() - 2); // Remove trailing comma and space
        return result.toString();
    }
}
