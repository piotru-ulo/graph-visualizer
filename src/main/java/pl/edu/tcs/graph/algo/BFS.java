package pl.edu.tcs.graph.algo;

import java.util.*;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
public class BFS implements Algorithm {
    private final Collection<AlgorithmProperties> properties = Arrays.asList(
            AlgorithmProperties.SOURCE,
            AlgorithmProperties.TARGET);

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }
    AlgoMiddleman algoMiddleman;

    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.algoMiddleman = aM;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return Arrays.asList(
                new VertexAction("set start", (v -> {
                    algoMiddleman.setVertexColor(sourceVertex, 255, 255, 255);
                    sourceVertex = v;
                    algoMiddleman.setVertexColor(v, 235, 143, 52);
                    ;
                    return null;
                })),
                new VertexAction("set end", (v -> {
                    algoMiddleman.setVertexColor(targetVertex, 255, 255, 255);
                    targetVertex = v;
                    algoMiddleman.setVertexColor(v, 116, 72, 194);
                    return null;
                })));
    }

    private Vertex targetVertex = null;
    private Vertex sourceVertex = null;
    private Map<Vertex, Boolean> visited;
    private Queue<Vertex> que;

    private void bfs(Graph g, Vertex u)
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
    public void run(Graph g, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        visited = new HashMap<>();
        for (Vertex v : g.getVertices())
            if (v.isActive())
                algoMiddleman.instantSetVertexColor(v, 255, 255, 255);
        for (Edge e : g.getEdges())
            algoMiddleman.instantSetEdgeColor(e, 0, 0, 0);
        try {
            if (requirements.get(AlgorithmProperties.SOURCE) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            if (sourceVertex == null)
                sourceVertex = g.getVertex(1);
            bfs(g, sourceVertex);
            sourceVertex = targetVertex = null;

        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
