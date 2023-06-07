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

public class Bipartition implements Algorithm {
    private Collection<AlgorithmProperties> properties = Arrays.asList();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    private Map<Vertex, Boolean> color;

    private void dfs(boolean col, Graph g, Vertex u, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        if (col)
            algoMiddleman.setVertexColor(u, 0, 128, 0);
        else
            algoMiddleman.setVertexColor(u, 255, 255, 0);
        color.put(u, col);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (!color.containsKey(to)) {
                dfs(!col, g, to, algoMiddleman);
            } else if (color.get(to).equals(col)) {
                throw new AlgorithmException("Given graph has negative length cycle.");
            }
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        color = new HashMap<>();
        try {
            dfs(false, g, g.getVertex(1), aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
