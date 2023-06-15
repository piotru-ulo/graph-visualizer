package pl.edu.tcs.graph.algo;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.view.Colors;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

import javax.swing.plaf.synth.SynthOptionPaneUI;

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
                    algoMiddleman.setVertexColor(sourceVertex, Colors.white);
                    sourceVertex = v;
                    algoMiddleman.setVertexColor(v, Colors.source);
                    ;
                    return null;
                })),
                new VertexAction("set end", (v -> {
                    algoMiddleman.setVertexColor(targetVertex, Colors.white);
                    targetVertex = v;
                    algoMiddleman.setVertexColor(v, Colors.target);
                    return null;
                })));
    }

    private Vertex targetVertex = null;
    private Vertex sourceVertex = null;
    private double rainbowRate = 0.01;
    private Map<Vertex, Boolean> visited;
    @AllArgsConstructor
    private static class CVertex {
        public final Vertex v;
        public final double c;
    }
    private Queue<CVertex> que;

    private void bfs(Graph g, Vertex u)
            throws AlgorithmException {
        que = new LinkedList<>();
        que.add(new CVertex(u, 0));
        visited.put(u, true);
        while (!que.isEmpty()) {
            CVertex cVertex = que.poll();
            algoMiddleman.setVertexColor(cVertex.v, new int[]{127, 255, 212});
            for (Vertex to : g.getIncidentVertices(cVertex.v)) {
                if (!visited.containsKey(to) && to.isActive()) {
                    visited.put(to, true);
                    que.add(new CVertex(to, cVertex.c+rainbowRate));
                    if (to.equals(targetVertex)) {
                        algoMiddleman.setVertexColor(to, Colors.target);
                        return;
                    }
                    algoMiddleman.setVertexColor(to, new int[]{250, 240, 230});
                }
            }
            algoMiddleman.setVertexColor(cVertex.v, Colors.rainbow(cVertex.c));
            System.out.println(cVertex.c);
        }
    }

    @Override
    public void run(Graph g, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        rainbowRate = 7.0/g.getVertices().size();
        visited = new HashMap<>();
        for (Vertex v : g.getVertices())
            if (v.isActive())
                algoMiddleman.instantSetVertexColor(v, Colors.white);
        for (Edge e : g.getEdges())
            algoMiddleman.instantSetEdgeColor(e, new int[]{0, 0, 0});
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
