package pl.edu.tcs.graph.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;

public class Maze implements Algorithm {

    private final Collection<AlgorithmProperties> properties = Arrays.asList();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    private Set<Vertex> unvisited;

    private int countOccupied(Graph g, Vertex u) {
        int sum = 0;
        for (Vertex to : g.getIncidentVertices(u)) {
            sum += (unvisited.contains(to) ? 1 : 0);
        }
        return g.getIncidentVertices(u).size() - sum;
    }

    private void dfessa(Graph g, AlgoMiddleman aM, Vertex u) {
        unvisited.remove(u);
        List<Vertex> adj = new ArrayList<Vertex>(g.getIncidentVertices(u));
        Collections.shuffle(adj);
        for (Vertex to : adj) {
            if (unvisited.contains(to) && countOccupied(g, to) <= 1) {
                dfessa(g, aM, to);
            }
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        unvisited = new HashSet<>();
        for (Vertex v : g.getVertices()) {
            if (!v.isActive())
                v.setActive(true);
            aM.instantSetVertexColor(v, 255, 255, 255);
        }
        for (Edge e : g.getEdges())
            aM.instantSetEdgeColor(e, 0, 0, 0);
        for (Vertex v : g.getVertices())
            unvisited.add(v);
        dfessa(g, aM, g.getVertex(0));
        // for(int i = 0; i * i < unvisited.size(); i++) { } maybe remove
        // ~sqrt(unvisited) vertices to make it more interesting?
        for (Vertex v : unvisited) {
            v.setActive(false);
            aM.setVertexColor(v, 100, 100, 100);
        }
    }
}
