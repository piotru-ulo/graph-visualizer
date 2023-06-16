package pl.edu.tcs.graph.algo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.view.Colors;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;

public class SCC implements Algorithm {
    private final Collection<AlgorithmProperties> properties = Arrays.asList();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    AlgoMiddleman aM;

    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.aM = aM;
    }

    private Map<Vertex, Integer> dp, vertexComponentId;
    private Stack<Vertex> vertexList;
    private int time;
    private int componentCount;

    private void colorVertex(Vertex v, AlgoMiddleman algoMiddleman, int component) {
        int r = 7123 * component % 256;
        int g = 2136 * component % 256;
        int b = 8753 * component % 256;
        algoMiddleman.setVertexColor(v, new int[] { r, g, b }, paintDelay);
    }

    private int dfs(Graph g, Vertex u, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        time++;
        dp.put(u, time);
        int low = time;
        vertexList.add(u);
        colorVertex(u, algoMiddleman, 123);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (!to.isActive())
                continue;
            if (!vertexComponentId.containsKey(to)) {
                if (dp.containsKey(to))
                    low = Math.min(low, dp.get(to));
                else
                    low = Math.min(low, dfs(g, to, algoMiddleman));
            }
        }
        if (low == dp.get(u)) {
            componentCount++;
            Vertex v = null;
            do {
                v = vertexList.pop();
                vertexComponentId.put(v, componentCount);
                colorVertex(v, algoMiddleman, componentCount);
            } while (!v.equals(u));
        }
        dp.put(u, low);
        return low;
    }

    @Override
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        vertexList = new Stack<>();
        dp = new HashMap<>();
        vertexComponentId = new HashMap<>();
        time = componentCount = 0;
        for (Vertex v : g.getVertices())
            if (v.isActive())
                aM.setVertexColor(v, Colors.white, 0);
        for (Edge e : g.getEdges())
            aM.setEdgeColor(e, Colors.black, 0);
        try {
            for (Vertex v : g.getVertices()) {
                if (!v.isActive() || vertexComponentId.containsKey(v))
                    continue;
                dfs(g, v, aM);
            }
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
