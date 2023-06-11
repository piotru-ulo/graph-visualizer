package pl.edu.tcs.graph.algo;

import java.util.*;

import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

public class Bipartition implements Algorithm<Vertex, Edge> {
    private final Collection<AlgorithmProperties> properties = List.of();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    private Map<Vertex, Boolean> color;

    private void dfs(boolean col, Graph<? extends Vertex, ? extends Edge> g, Vertex u, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        if (col)
            algoMiddleman.setVertexColor(u, 0, 128, 0);
        else
            algoMiddleman.setVertexColor(u, 255, 255, 0);
        color.put(u, col);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to.isActive() && !color.containsKey(to)) {
                dfs(!col, g, to, algoMiddleman);
            } else if (to.isActive() && color.get(to).equals(col)) {
                throw new AlgorithmException("Given graph has negative length cycle.");
            }
        }
    }

    @Override
    public void run(Graph<? extends Vertex, ? extends Edge> g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        color = new HashMap<>();
        try {
            for (Vertex v : g.getVertices()) {
                if (color.containsKey(v))
                    continue;
                dfs(false, g, v, aM);
            }
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
