package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: 
 * - You MUST NOT add constructors, fields, or non-@Test methods to this class.
 * - Your tests MUST only obtain Graph instances by calling `emptyInstance()`.
 * - Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    /*
     * Testing Strategy:
     * - Vertex does not exist, vertex exists.
     * - Edge does not exist, edge exists.
     * - Number of vertices: 0, > 0.
     * - Number of edges: 0, 1, > 1.
     * - Weight: 0, > 0.
     * - Number of sources: 0, > 0.
     * - Number of targets: 0, > 0.
     */

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        // Ensure assertions are enabled with VM argument: -ea
        assert false;
    }

    @Test
    public void testInitialVerticesEmpty() {
        // Ensure a new graph has no vertices
        assertEquals("Expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    // Tests for `add` method
    @Test
    public void testAdd() {
        Graph<String> graph = emptyInstance();

        // Add a new vertex
        assertTrue(graph.add("a"));
        assertTrue(graph.vertices().contains("a"));

        // Try adding the same vertex again
        assertFalse(graph.add("a"));

        // Add another vertex
        assertTrue(graph.add("b"));
        assertTrue(graph.vertices().contains("b"));
    }

    // Tests for `set` method
    @Test
    public void testSet() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");

        // Add a new edge
        assertEquals(0, graph.set("a", "b", 1));
        assertEquals(Map.of("a", 1), graph.sources("b"));

        // Modify the edge's weight
        assertEquals(1, graph.set("a", "b", 2));
        assertEquals(Map.of("a", 2), graph.sources("b"));

        // Add another edge
        assertEquals(0, graph.set("a", "c", 3));
        assertEquals(Map.of("a", 3), graph.sources("c"));

        // Remove an edge
        assertEquals(2, graph.set("a", "b", 0));
        assertFalse(graph.sources("b").containsKey("a"));
    }

    // Tests for `remove` method
    @Test
    public void testRemove() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.add("d");

        graph.set("a", "b", 1);
        graph.set("a", "c", 2);
        graph.set("b", "c", 3);

        // Remove a vertex and its edges
        assertTrue(graph.remove("a"));
        assertFalse(graph.vertices().contains("a"));
        assertFalse(graph.sources("b").containsKey("a"));
        assertFalse(graph.sources("c").containsKey("a"));

        // Try removing a non-existent vertex
        assertFalse(graph.remove("a"));

        // Remove another vertex
        assertTrue(graph.remove("b"));
        assertFalse(graph.vertices().contains("b"));
        assertFalse(graph.sources("c").containsKey("b"));

        // Remove a vertex without edges
        assertTrue(graph.remove("d"));
        assertFalse(graph.vertices().contains("d"));
    }

    // Tests for `vertices` method
    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();

        // Empty graph
        assertEquals(Collections.emptySet(), graph.vertices());

        // Single vertex
        graph.add("a");
        assertEquals(Collections.singleton("a"), graph.vertices());

        // Multiple vertices
        graph.add("b");
        assertEquals(Set.of("a", "b"), graph.vertices());
    }

    // Tests for `sources` method
    @Test
    public void testSources() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");

        graph.set("a", "b", 1);
        graph.set("a", "c", 2);
        graph.set("b", "c", 3);

        // Non-existent vertex
        assertEquals(Collections.emptyMap(), graph.sources("d"));

        // Vertex with no sources
        assertEquals(Collections.emptyMap(), graph.sources("a"));

        // Vertex with one source
        assertEquals(Map.of("a", 1), graph.sources("b"));

        // Vertex with multiple sources
        assertEquals(Map.of("a", 2, "b", 3), graph.sources("c"));
    }

    // Tests for `targets` method
    @Test
    public void testTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");

        graph.set("a", "b", 1);
        graph.set("a", "c", 2);
        graph.set("b", "c", 3);

        // Non-existent vertex
        assertEquals(Collections.emptyMap(), graph.targets("d"));

        // Vertex with no targets
        assertEquals(Collections.emptyMap(), graph.targets("c"));

        // Vertex with one target
        assertEquals(Map.of("c", 3), graph.targets("b"));

        // Vertex with multiple targets
        assertEquals(Map.of("b", 1, "c", 2), graph.targets("a"));
    }
}
