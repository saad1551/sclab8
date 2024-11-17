package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * <p>Testing strategy:
 * - `empty()`:
 *   - Verify that the returned graph is empty using `vertices()`.
 *   - Test behavior with different types of vertex labels as per Problem 3.2.
 */
public class GraphStaticTest {

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        // Ensure assertions are enabled with VM argument: -ea
        assert false;
    }

    @Test
    public void testEmptyVerticesEmpty() {
        // Verify that an empty graph has no vertices
        Graph<String> emptyGraph = Graph.empty();
        assertEquals("Expected empty graph to have no vertices",
                Collections.emptySet(), emptyGraph.vertices());
    }

    @Test
    public void testEmptyBehaviorWithDifferentVertexLabelTypes() {
        // Test empty graph with Integer vertex labels
        Graph<Integer> intGraph = Graph.empty();
        assertEquals("Expected empty graph to have no vertices", 
                Collections.emptySet(), intGraph.vertices());
        assertTrue("Expected add to return true for new vertex",
                intGraph.add(1));
        assertEquals("Expected graph to contain added vertex", 
                Set.of(1), intGraph.vertices());

        // Test empty graph with custom object vertex labels
        class CustomVertex {
            final String name;
            CustomVertex(String name) {
                this.name = name;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof CustomVertex)) return false;
                CustomVertex other = (CustomVertex) o;
                return name.equals(other.name);
            }

            @Override
            public int hashCode() {
                return name.hashCode();
            }

            @Override
            public String toString() {
                return name;
            }
        }

        Graph<CustomVertex> customGraph = Graph.empty();
        CustomVertex v1 = new CustomVertex("A");
        CustomVertex v2 = new CustomVertex("B");

        assertTrue("Expected add to return true for new custom vertex",
                customGraph.add(v1));
        assertEquals("Expected graph to contain added custom vertex", 
                Set.of(v1), customGraph.vertices());
        customGraph.add(v2);
        assertEquals("Expected graph to contain multiple custom vertices", 
                Set.of(v1, v2), customGraph.vertices());
    }
}
