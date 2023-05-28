package pl.edu.tcs.graph.algo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

public class BFS implements Algorithm {
    private final Collection<AlgorithmProperties> properties = Arrays.asList(
            AlgorithmProperties.SOURCE,
            AlgorithmProperties.TARGET);

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    private Vertex targetVertex = null;
    private Map<Vertex, Boolean> visited;
    private Queue<Vertex> que;

    private void bfs(Graph g, Vertex u, AlgoMiddleman aM)
            throws AlgorithmException {
        que = new LinkedList<>();
        que.add(u);
        visited.put(u, true);
        while (!que.isEmpty()) {
            Vertex v = que.poll();
            aM.setVertexColor(v, javafx.scene.paint.Color.AQUAMARINE);
            for (Vertex to : g.getIncidentVertices(v)) {
                if (!visited.containsKey(to)) {
                    visited.put(to, true);
                    que.add(to);
                    if (to.equals(targetVertex)) {
                        aM.setVertexColor(to, javafx.scene.paint.Color.GOLD);
                        return;
                    }
                    aM.setVertexColor(to, javafx.scene.paint.Color.BEIGE);
                }
            }
            aM.setVertexColor(v, javafx.scene.paint.Color.BLUEVIOLET);
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        visited = new HashMap<>();
        try {
            Vertex sourceVertex = g.getVertex(1);
            System.out.println(requirements);
            if (requirements.get(AlgorithmProperties.SOURCE) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            bfs(g, sourceVertex, aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }

}
