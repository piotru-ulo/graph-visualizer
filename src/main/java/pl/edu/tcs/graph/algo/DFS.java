package pl.edu.tcs.graph.algo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
                    algoMiddleman.setVertexColor(sourceVertex, Colors.white, 0);
                    sourceVertex = v;
                    algoMiddleman.setVertexColor(v, Colors.source, 0);
                    return null;
                })),
                new VertexAction("set end", (v -> {
                    algoMiddleman.setVertexColor(targetVertex, Colors.white, 0);
                    targetVertex = v;
                    algoMiddleman.setVertexColor(v, Colors.target, 0);
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
        algoMiddleman.setVertexColor(u, Colors.rainbow(progress), paintDelay);
        visited.put(u, true);

        if (u.equals(targetVertex)) {
            algoMiddleman.setVertexColor(u, new int[] { 255, 215, 0 }, paintDelay);
            found = true;
        }

        for (Vertex to : g.getIncidentVertices(u)) {
            if (found)
                return;
            if (visited.containsKey(to) || !to.isActive())
                continue;
            algoMiddleman.setEdgeColor(g.getCorrespondingEdge(u, to), new int[] { 0, 128, 0 }, paintDelay);
            dfs(g, to, progress + rainbowRate);
        }
    }

    @Override
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        visited = new HashMap<>();
        found = false;
        rainbowRate = 7.0 / g.getVertices().size();
        for (Vertex v : g.getVertices())
            if (v.isActive())
                algoMiddleman.setVertexColor(v, Colors.white, 0);
        for (Edge e : g.getEdges())
            algoMiddleman.setEdgeColor(e, Colors.black, 0);
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

    private int paintDelay;

    @Override
    public void setPaintDelay(int delay) {
        paintDelay = delay;
    }
}
