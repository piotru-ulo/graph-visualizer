package pl.edu.tcs.graph.algo;

import java.util.*;

import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

public class CycleFinding implements Algorithm<Vertex, Edge> {
    private final Collection<AlgorithmProperties> properties = List.of();

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

    private boolean dfs(Graph<? extends Vertex, ? extends Edge> g, Vertex u, Vertex par, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        vertexColor.put(u, 1);
        algoMiddleman.setVertexColor(u, 250, 25, 25);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to.equals(par))
                continue;
            if (!vertexColor.containsKey(to)) {
                vertexParent.put(to, u);
                if (dfs(g, to, u, algoMiddleman))
                    return true;
            } else if (vertexColor.get(to).equals(1)) {
                cycleStart = to;
                cycleEnd = u;
                return true;
            }
        }
        vertexColor.put(u, 2);
        algoMiddleman.setVertexColor(u, 25, 250, 25);
        return false;
    }

    @Override
    public void run(Graph<? extends Vertex, ? extends Edge> g, AlgoMiddleman aM,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        vertexColor = new HashMap<>();
        vertexParent = new HashMap<>();
        cycleStart = cycleEnd = null;
        for (Vertex v : g.getVertices())
            if (!vertexColor.containsKey(v) && dfs(g, v, v, aM))
                break;
        if (cycleStart != null) {
            for (Vertex me = cycleEnd; me != null && !me.equals(cycleStart); me = vertexParent.get(me)) {
                aM.setVertexColor(me, 25, 25, 250);
            }
            aM.setVertexColor(cycleStart, 25, 25, 250);
        }
    }
}