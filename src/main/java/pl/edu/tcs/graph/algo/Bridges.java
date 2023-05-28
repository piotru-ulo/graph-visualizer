package pl.edu.tcs.graph.algo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

public class Bridges implements Algorithm {
    private Collection<AlgorithmProperties> properties = Arrays.asList();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    private Map<Vertex, Boolean> visited;
    private Map<Vertex, Integer> tin, low;
    private int time = 0;

    private void dfs(Graph g, Vertex u, Vertex par, AlgoMiddleman aM)
            throws AlgorithmException {
        visited.put(u, true);
        time++;
        tin.put(u, time);
        low.put(u, time);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to == par)
                continue;
            if (visited.containsKey(to))
                low.put(u, Math.min(low.get(u), tin.get(to)));
            else {
                dfs(g, to, u, aM);
                low.put(u, Math.min(low.get(u), low.get(to)));
                if (low.get(to) > tin.get(u)) {
                    aM.setEdgeColor(g.getCorrespondingEdge(u, to), javafx.scene.paint.Color.RED);
                }
            }
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        tin = new HashMap<>();
        low = new HashMap<>();
        try {
            dfs(g, g.getVertex(1), g.getVertex(1), aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }

}
