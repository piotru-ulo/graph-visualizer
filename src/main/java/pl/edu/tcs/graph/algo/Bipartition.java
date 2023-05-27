package pl.edu.tcs.graph.algo;

import java.util.*;

import javafx.util.Pair;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

public class Bipartition implements Algorithm {
    // properties
    private Set<AlgorithmProperties> properties = new HashSet<>();

    @Override
    public Set<AlgorithmProperties> getProperties() {
        return properties;
    }
    //algo
    private Map<Vertex, Boolean> color;

    private void dfs(boolean col, Graph g, Vertex u, AlgoMiddleman aM)
            throws AlgorithmException {
        aM.setVertexColor(u, col ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.YELLOW);
        color.put(u, col);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (!color.containsKey(to)) {
                dfs(!col, g, to, aM);
            } else if (color.get(to) == col) {
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
