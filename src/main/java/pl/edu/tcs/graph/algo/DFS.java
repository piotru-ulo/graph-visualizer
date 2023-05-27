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

public class DFS implements Algorithm {
    private Map<Vertex, Boolean> visited;

    private void dfs(Graph g, Vertex u, VertexMiddleman vM, EdgeMiddleman eM, int sleepDelay) {
        vM.setColor(u, javafx.scene.paint.Color.PINK);
        visited.put(u, true);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (visited.containsKey(to))
                continue;
            try {
                Thread.sleep(sleepDelay);
            } catch (InterruptedException e) {
            }
            eM.setColor(g.getCorrespondingEdge(u, to), javafx.scene.paint.Color.GREEN);
            dfs(g, to, vM, eM, sleepDelay);
        }
    }

    @Override
    public void run(Graph g, VertexMiddleman vM, EdgeMiddleman eM, int sleepDelay,
            Collection<Pair<String, Integer>> requirements) {
        visited = new HashMap<>();
        dfs(g, g.getVertex(1), vM, eM, sleepDelay);
    }

}
