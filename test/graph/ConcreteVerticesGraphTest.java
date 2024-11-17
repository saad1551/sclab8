package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as additional tests for the ConcreteVerticesGraph implementation.
 * 
 * Tests specific to the Graph interface should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    // Testing strategy for ConcreteVerticesGraph.toString():
    // - Test an empty graph.
    // - Test a graph with one vertex and no edges.
    // - Test a graph with two vertices and no edges.
    // - Test a graph with two vertices and one edge.
    // - Test a graph with three vertices and multiple edges.

    @Test
    public void testConcreteVerticesGraphToString() {
        Graph<String> graph = emptyInstance();

        // Test empty graph
        assertEquals("", graph.toString());

        // Test graph with one vertex
        graph.add("a");
        assertEquals("a -> \n", graph.toString());

        // Test graph with two vertices, no edges
        graph.add("b");
        assertEquals("a -> \nb -> \n", graph.toString());

        // Test graph with two vertices and one edge
        graph.set("a", "b", 1);
        assertEquals("a -> b : 1\nb -> \n", graph.toString());

        // Test graph with three vertices and multiple edges
        graph.add("c");
        graph.set("b", "c", 2);
        assertEquals("a -> b : 1\nb -> c : 2\nc -> \n", graph.toString());
    }

    /*
     * Testing Vertex...
     */

    // Testing strategy for Vertex:
    // - Test a vertex with no edges.
    // - Test a vertex with one outgoing edge.
    // - Test a vertex with multiple outgoing edges.

    @Test
    public void testVertexCreationAndEdges() {
        // Test vertex with no edges
        Vertex vertex = new Vertex("a");
        assertEquals("a", vertex.getSource());
        assertTrue(vertex.getOutEdges().isEmpty());

        // Test vertex with one outgoing edge
        vertex.addOutEdge("b", 1);
        assertTrue(vertex.getOutEdges().containsKey("b"));
        assertEquals(1, vertex.getOutEdges().get("b").intValue());

        // Test vertex with multiple outgoing edges
        vertex.addOutEdge("c", 2);
        assertTrue(vertex.getOutEdges().containsKey("c"));
        assertEquals(2, vertex.getOutEdges().get("c").intValue());
        assertEquals("a -> b : 1\na -> c : 2\n", vertex.toString());
    }

    @Test
    public void testVertexAddOutEdge() {
        Vertex vertex = new Vertex("a");

        // Add first outgoing edge
        vertex.addOutEdge("b", 1);
        assertTrue(vertex.getOutEdges().containsKey("b"));
        assertEquals(1, vertex.getOutEdges().get("b").intValue());

        // Add second outgoing edge
        vertex.addOutEdge("c", 2);
        assertTrue(vertex.getOutEdges().containsKey("c"));
        assertEquals(2, vertex.getOutEdges().get("c").intValue());
    }

    @Test
    public void testVertexRemoveOutEdge() {
        Vertex vertex = new Vertex("a");
        vertex.addOutEdge("b", 1);
        vertex.addOutEdge("c", 2);

        // Remove an existing outgoing edge
        vertex.removeOutEdge("b");
        assertFalse(vertex.getOutEdges().containsKey("b"));
        assertTrue(vertex.getOutEdges().containsKey("c"));
        assertEquals(2, vertex.getOutEdges().get("c").intValue());

        // Remove another existing outgoing edge
        vertex.removeOutEdge("c");
        assertFalse(vertex.getOutEdges().containsKey("c"));
    }

    @Test
    public void testVertexToString() {
        Vertex vertex = new Vertex("a");

        // Test vertex with no outgoing edges
        assertEquals("a -> \n", vertex.toString());

        // Test vertex with one outgoing edge
        vertex.addOutEdge("b", 1);
        assertEquals("a -> b : 1\n", vertex.toString());

        // Test vertex with multiple outgoing edges
        vertex.addOutEdge("c", 2);
        assertEquals("a -> b : 1\na -> c : 2\n", vertex.toString());
    }
}
