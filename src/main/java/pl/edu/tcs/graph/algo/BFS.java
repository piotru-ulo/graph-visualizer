package pl.edu.tcs.graph.algo;

import java.util.*;

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

    @Override
    public Collection<VertexAction> getVertexActions() {
        return Arrays.asList(
                new VertexAction("set start", (v -> {
                    sourceVertex = v;
                    return null;
                })),
                new VertexAction("set end", (v -> {
                    targetVertex = v;
                    return null;
                })));
    }

    private Vertex targetVertex;
    private Vertex sourceVertex;
    private Map<Vertex, Boolean> visited;
    private Queue<Vertex> que;

    private void bfs(Graph g, Vertex u, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        que = new LinkedList<>();
        que.add(u);
        visited.put(u, true);
        while (!que.isEmpty()) {
            Vertex v = que.poll();
            algoMiddleman.setVertexColor(v, 127, 255, 212);
            for (Vertex to : g.getIncidentVertices(v)) {
                if (!visited.containsKey(to) && to.isActive()) {
                    visited.put(to, true);
                    que.add(to);
                    if (to.equals(targetVertex)) {
                        algoMiddleman.setVertexColor(to, 212, 175, 55);
                        return;
                    }
                    algoMiddleman.setVertexColor(to, 250, 240, 230);
                }
            }
            algoMiddleman.setVertexColor(v, 138, 43, 226);
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        visited = new HashMap<>();
        targetVertex = null;
        try {
            System.out.println(requirements);
            if (requirements.get(AlgorithmProperties.SOURCE) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            bfs(g, sourceVertex, aM);
            if (sourceVertex==null)
                sourceVertex = g.getVertex(1);

        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
