package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as additional tests for the ConcreteEdgesGraph implementation.
 * 
 * Tests specific to the Graph interface should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */

    // Testing strategy for ConcreteEdgesGraph.toString():
    // - Test an empty graph.
    // - Test a graph with one vertex and no edges.
    // - Test a graph with two vertices and no edges.
    // - Test a graph with two vertices and one edge.
    // - Test a graph with three vertices and multiple edges.

    @Test 
    public void testConcreteEdgesGraphToString() {
        Graph<String> graph = emptyInstance();

        // Test empty graph
        assertEquals("Vertices:\nEdges:\n", graph.toString());

        // Test graph with one vertex
        graph.add("a");
        assertEquals("Vertices:\na\nEdges:\n", graph.toString());

        // Test graph with two vertices, no edges
        graph.add("b");
        assertEquals("Vertices:\na\nb\nEdges:\n", graph.toString());

        // Test graph with two vertices and one edge
        graph.set("a", "b", 1);
        assertEquals("Vertices:\na\nb\nEdges:\na -> b : 1\n", graph.toString());

        // Test graph with three vertices and two edges
        graph.add("c");
        graph.set("b", "c", 2);
        assertEquals("Vertices:\na\nb\nc\nEdges:\na -> b : 1\nb -> c : 2\n", graph.toString());
    }

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge:
    // - source.length(): 0, 1, >1
    // - target.length(): 0, 1, >1
    // - weight: 0, 1, >1
    //
    // Ensure correct behavior of:
    // - Edge creation
    // - Getters for source, target, and weight
    // - String representation of edges

    @Test
    public void testEdgeCreationAndGetters() {
        // Test typical edge
        Edge edge = new Edge("a", "b", 1);
        assertEquals("a", edge.getSource());
        assertEquals("b", edge.getTarget());
        assertEquals(1, edge.getWeight());

        // Test edge with empty target
        edge = new Edge("ab", "", 0);
        assertEquals("ab", edge.getSource());
        assertEquals("", edge.getTarget());
        assertEquals(0, edge.getWeight());

        // Test edge with larger weight
        edge = new Edge("a", "b", 100);
        assertEquals("a", edge.getSource());
        assertEquals("b", edge.getTarget());
        assertEquals(100, edge.getWeight());
    }

    @Test
    public void testEdgeToString() {
        // Test typical edge
        Edge edge = new Edge("a", "b", 1);
        assertEquals("a -> b : 1", edge.toString());

        // Test edge with empty target
        edge = new Edge("ab", "", 0);
        assertEquals("ab ->  : 0", edge.toString());

        // Test edge with larger weight
        edge = new Edge("a", "b", 100);
        assertEquals("a -> b : 100", edge.toString());
    }
}
