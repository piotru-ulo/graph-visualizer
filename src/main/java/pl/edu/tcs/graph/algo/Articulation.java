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

public class Articulation implements Algorithm {
    private Collection<AlgorithmProperties> properties = Arrays.asList();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    private Map<Vertex, Boolean> visited;
    private Map<Vertex, Integer> preOrder, low;
    private int time = 0;

    private void dfs(Graph g, Vertex u, Vertex p, AlgoMiddleman aM)
            throws AlgorithmException {
        visited.put(u, true);
        time++;
        preOrder.put(u, time);
        low.put(u, time);
        int childrenCount = 0;
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to.equals(p))
                continue;
            if (visited.containsKey(to))
                low.put(u, Math.min(low.get(u), preOrder.get(to)));
            else {
                dfs(g, to, u, aM);
                low.put(u, Math.min(low.get(u), low.get(to)));
                if (low.get(to) >= preOrder.get(u) && p != g.getVertex(1))
                    aM.setVertexColor(u, 255, 160, 122);
                ++childrenCount;
            }
        }
        if (p.equals(g.getVertex(1)) && childrenCount > 1)
            aM.setVertexColor(u, 255, 0, 0);
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        preOrder = new HashMap<>();
        low = new HashMap<>();
        try {
            dfs(g, g.getVertex(1), g.getVertex(1), aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
