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

public class DFS implements Algorithm {
    private Collection<AlgorithmProperties> properties = Arrays.asList(AlgorithmProperties.SOURCE,
            AlgorithmProperties.TARGET);

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    private Vertex targetVertex = null;
    private boolean found = false;
    private Map<Vertex, Boolean> visited;

    private void dfs(Graph g, Vertex u, AlgoMiddleman aM)
            throws AlgorithmException {
        aM.setVertexColor(u, javafx.scene.paint.Color.PINK);
        visited.put(u, true);

        if (u.equals(targetVertex)) {
            aM.setVertexColor(u, javafx.scene.paint.Color.GOLD);
            found = true;
        }

        for (Vertex to : g.getIncidentVertices(u)) {
            if (found)
                return;
            if (visited.containsKey(to))
                continue;
            aM.setEdgeColor(g.getCorrespondingEdge(u, to), javafx.scene.paint.Color.GREEN);
            dfs(g, to, aM);
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        try {
            Vertex sourceVertex = g.getVertex(1);
            System.out.println(requirements);
            if (requirements.get(AlgorithmProperties.SOURCE) != null
                    && g.getVertex(requirements.get(AlgorithmProperties.SOURCE)) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null
                    && g.getVertex(requirements.get(AlgorithmProperties.TARGET)) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            dfs(g, sourceVertex, aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }

}