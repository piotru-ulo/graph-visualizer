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

public class CycleFinding implements Algorithm {
    private final Collection<AlgorithmProperties> properties = Arrays.asList();
    AlgoMiddleman aM;

    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.aM = aM;
    }

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    private Map<Vertex, Integer> vertexColor;
    private Map<Vertex, Vertex> vertexParent;
    private Vertex cycleStart, cycleEnd;

    private boolean dfs(Graph g, Vertex u, Vertex par)
            throws AlgorithmException {
        vertexColor.put(u, 1);
        aM.setVertexColor(u, new int[] { 250, 25, 25 }, paintDelay);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to.equals(par) || !to.isActive())
                continue;
            if (!vertexColor.containsKey(to)) {
                vertexParent.put(to, u);
                if (dfs(g, to, u))
                    return true;
            } else if (vertexColor.get(to).equals(1)) {
                cycleStart = to;
                cycleEnd = u;
                return true;
            }
        }
        vertexColor.put(u, 2);
        aM.setVertexColor(u, new int[] { 25, 250, 25 }, paintDelay);
        return false;
    }

    @Override
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        vertexColor = new HashMap<>();
        vertexParent = new HashMap<>();
        cycleStart = cycleEnd = null;
        for (Vertex v : g.getVertices())
            if (v.isActive())
                aM.setVertexColor(v, Colors.white, 0);
        for (Edge e : g.getEdges())
            aM.setEdgeColor(e, Colors.black, 0);
        for (Vertex v : g.getVertices())
            if (v.isActive() && !vertexColor.containsKey(v) && dfs(g, v, v))
                break;
        if (cycleStart != null) {
            for (Vertex me = cycleEnd; me != null && !me.equals(cycleStart); me = vertexParent.get(me)) {
                aM.setVertexColor(me, new int[] { 25, 25, 250 }, paintDelay);
            }
            aM.setVertexColor(cycleStart, new int[] { 25, 25, 250 }, paintDelay);
        }
    }

    private int paintDelay;

    @Override
    public void setPaintDelay(int delay) {
        paintDelay = delay;
    }
}