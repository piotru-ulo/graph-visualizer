package pl.edu.tcs.graph.algo;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javafx.util.Pair;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.viewmodel.EdgeMiddleman;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;
import pl.edu.tcs.graph.viewmodel.VertexMiddleman;

public class BFS implements Algorithm {
    private Map<Vertex, Boolean> visited;
    private Queue<Vertex> que;

    private void bfs(Graph g, Vertex u, VertexMiddleman vM, EdgeMiddleman eM)
            throws AlgorithmException {
        que = new LinkedList<>();
        que.add(u);
        visited.put(u, true);
        while (!que.isEmpty()) {
            Vertex v = que.poll();
            vM.setColor(v, javafx.scene.paint.Color.AQUAMARINE);
            for (Vertex to : g.getIncidentVertices(v)) {
                if (!visited.containsKey(to)) {
                    visited.put(to, true);
                    que.add(to);
                    vM.setColor(to, javafx.scene.paint.Color.BEIGE);
                }
            }
            vM.setColor(v, javafx.scene.paint.Color.BLUEVIOLET);
        }
    }

    @Override
    public void run(Graph g, VertexMiddleman vM, EdgeMiddleman eM, Collection<Pair<String, Integer>> requirements)
            throws AlgorithmException {
        visited = new HashMap<>();
        try {
            bfs(g, g.getVertex(1), vM, eM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }

}
