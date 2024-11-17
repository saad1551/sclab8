import java.util.*;

public class AdjacencyListGraph<L> implements Graph<L> {

    private final Map<L, Map<L, Integer>> adjacencyList = new HashMap<>();

    @Override
    public boolean add(L vertex) {
        if (adjacencyList.containsKey(vertex)) {
            return false;
        }
        adjacencyList.put(vertex, new HashMap<>());
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        adjacencyList.putIfAbsent(source, new HashMap<>());
        adjacencyList.putIfAbsent(target, new HashMap<>());

        Map<L, Integer> edges = adjacencyList.get(source);
        int previousWeight = edges.getOrDefault(target, 0);

        if (weight == 0) {
            edges.remove(target);
        } else {
            edges.put(target, weight);
        }

        return previousWeight;
    }

    @Override
    public boolean remove(L vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return false;
        }

        adjacencyList.remove(vertex);

        for (Map<L, Integer> edges : adjacencyList.values()) {
            edges.remove(vertex);
        }

        return true;
    }

    @Override
    public Set<L> vertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Map.Entry<L, Map<L, Integer>> entry : adjacencyList.entrySet()) {
            L source = entry.getKey();
            Map<L, Integer> edges = entry.getValue();
            if (edges.containsKey(target)) {
                sources.put(source, edges.get(target));
            }
        }
        return sources;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        return adjacencyList.getOrDefault(source, Collections.emptyMap());
    }
}
