package pl.edu.tcs.graph.algo;

import java.util.*;

import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

public class Bridges implements Algorithm<Vertex, Edge> {
    private final Collection<AlgorithmProperties> properties = List.of();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    private Map<Vertex, Boolean> visited;
    private Map<Vertex, Integer> preOrder, low;
    private int time;

    private void dfs(Graph<? extends Vertex,? extends Edge> g, Vertex u, Vertex p, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        visited.put(u, true);
        time++;
        preOrder.put(u, time);
        low.put(u, time);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to == p)
                continue;
            if (visited.containsKey(to))
                low.put(u, Math.min(low.get(u), preOrder.get(to)));
            else {
                dfs(g, to, u, algoMiddleman);
                low.put(u, Math.min(low.get(u), low.get(to)));
                if (low.get(to) > preOrder.get(u)) {
                    algoMiddleman.setEdgeColor(g.getCorrespondingEdge(u, to), 255, 160, 122);
                }
            }
        }
    }

    @Override
    public void run(Graph<? extends Vertex, ? extends Edge> g, AlgoMiddleman aM,
                    Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        preOrder = new HashMap<>();
        low = new HashMap<>();
        time = 0;
        try {
            dfs(g, g.getVertex(1), g.getVertex(1), aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
