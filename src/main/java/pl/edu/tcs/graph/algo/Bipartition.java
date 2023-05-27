package pl.edu.tcs.graph.algo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.viewmodel.EdgeMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;
import pl.edu.tcs.graph.viewmodel.VertexMiddleman;

public class Bipartition implements Algorithm {
    private Map<Vertex, Boolean> color;

    private void dfs(boolean col, Graph g, Vertex u, VertexMiddleman vM, EdgeMiddleman eM)
            throws AlgorithmException {
        vM.setColor(u, col ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.YELLOW);
        color.put(u, col);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (!color.containsKey(to)) {
                dfs(!col, g, to, vM, eM);
            } else if (color.get(to) == col) {
                throw new AlgorithmException("Given graph has negative length cycle.");
            }
        }
    }

    @Override
    public void run(Graph g, VertexMiddleman vM, EdgeMiddleman eM, Collection<Pair<String, Integer>> requirements)
            throws AlgorithmException {
        color = new HashMap<>();
        try {
            dfs(false, g, g.getVertex(1), vM, eM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
