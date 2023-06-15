package pl.edu.tcs.graph.algo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.view.Colors;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
public class DFS implements Algorithm {
    private final Collection<AlgorithmProperties> properties = Arrays.asList(AlgorithmProperties.SOURCE,
            AlgorithmProperties.TARGET);

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return Arrays.asList(
                new VertexAction("set start", (v -> {
                    algoMiddleman.setVertexColor(sourceVertex, Colors.white);
                    sourceVertex = v;
                    algoMiddleman.setVertexColor(v, Colors.source);
                    return null;
                })),
                new VertexAction("set end", (v -> {
                    algoMiddleman.setVertexColor(targetVertex, Colors.white);
                    targetVertex = v;
                    algoMiddleman.setVertexColor(v, Colors.target);
                    return null;
                })));
    }
    AlgoMiddleman algoMiddleman;

    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.algoMiddleman = aM;
    }

    private Vertex sourceVertex, targetVertex;
    private boolean found;
    private double rainbowRate = 0.01;
    private Map<Vertex, Boolean> visited;

    private void dfs(Graph g, Vertex u, double progress)
            throws AlgorithmException {
        algoMiddleman.setVertexColor(u, Colors.rainbow(progress));
        visited.put(u, true);

        if (u.equals(targetVertex)) {
            algoMiddleman.setVertexColor(u, new int[]{255, 215, 0});
            found = true;
        }

        for (Vertex to : g.getIncidentVertices(u)) {
            if (found)
                return;
            if (visited.containsKey(to) || !to.isActive())
                continue;
            algoMiddleman.setEdgeColor(g.getCorrespondingEdge(u, to), new int[]{0, 128, 0});
            dfs(g, to, progress+rainbowRate);
        }
    }

    @Override
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        found = false;
        for (Vertex v : g.getVertices())
            if (v.isActive())
                algoMiddleman.instantSetVertexColor(v, new int[]{255, 255, 255});
        for (Edge e : g.getEdges())
            algoMiddleman.instantSetEdgeColor(e, new int[]{0, 0, 0});
        try {
            if (requirements.get(AlgorithmProperties.SOURCE) != null
                    && g.getVertex(requirements.get(AlgorithmProperties.SOURCE)) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null
                    && g.getVertex(requirements.get(AlgorithmProperties.TARGET)) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            if (sourceVertex == null)
                sourceVertex = g.getVertex(1);
            dfs(g, sourceVertex, 0);
            sourceVertex = targetVertex = null;
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
