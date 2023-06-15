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
    AlgoMiddleman algoMiddleman;

    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.algoMiddleman = aM;
    }

    private Vertex sourceVertex, targetVertex;
    private boolean found;
    private Map<Vertex, Boolean> visited;

    private void dfs(Graph g, Vertex u)
            throws AlgorithmException {
        algoMiddleman.setVertexColor(u, 255, 192, 203);
        visited.put(u, true);

        if (u.equals(targetVertex)) {
            algoMiddleman.setVertexColor(u, 255, 215, 0);
            found = true;
        }

        for (Vertex to : g.getIncidentVertices(u)) {
            if (found)
                return;
            if (visited.containsKey(to) || !to.isActive())
                continue;
            algoMiddleman.setEdgeColor(g.getCorrespondingEdge(u, to), 0, 128, 0);
            dfs(g, to);
        }
    }

    @Override
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        found = false;
        for (Vertex v : g.getVertices())
            if (v.isActive())
                algoMiddleman.instantSetVertexColor(v, 255, 255, 255);
        for (Edge e : g.getEdges())
            algoMiddleman.instantSetEdgeColor(e, 0, 0, 0);
        try {
            if (requirements.get(AlgorithmProperties.SOURCE) != null
                    && g.getVertex(requirements.get(AlgorithmProperties.SOURCE)) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null
                    && g.getVertex(requirements.get(AlgorithmProperties.TARGET)) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            if (sourceVertex == null)
                sourceVertex = g.getVertex(1);
            dfs(g, sourceVertex);
            sourceVertex = targetVertex = null;
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
