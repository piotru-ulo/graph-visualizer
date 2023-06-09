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
import pl.edu.tcs.graph.view.Colors;

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

    AlgoMiddleman aM;

    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.aM = aM;
    }

    private Set<Vertex> unvisited;
    private Set<Vertex> touched;

    private int countOccupied(Graph g, Vertex u) {
        int sum = 0;
        for (Vertex to : g.getIncidentVertices(u)) {
            sum += (unvisited.contains(to) ? 1 : 0);
        }
        return g.getIncidentVertices(u).size() - sum;
    }

    private void dfessa(Graph g, Vertex u) {
        unvisited.remove(u);
        List<Vertex> adj = new ArrayList<Vertex>(g.getIncidentVertices(u));
        Collections.shuffle(adj);
        touched.add(u);
        for (Vertex to : adj) {
            touched.add(to);
            if (unvisited.contains(to) && countOccupied(g, to) <= 1) {
                dfessa(g, to);
            }
        }
    }

    @Override
    public void run(Graph g, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        unvisited = new HashSet<>();
        touched = new HashSet<>();
        for (Vertex v : g.getVertices()) {
            if (!v.isActive())
                v.setActive(true);
            aM.setVertexColor(v, Colors.white, 0);
        }
        for (Edge e : g.getEdges())
            aM.setEdgeColor(e, Colors.black, 0);
        unvisited.addAll(g.getVertices());
        dfessa(g, g.getVertex(0));
        for (Vertex v : g.getVertices()) {
            if (touched.contains(v))
                continue;
            dfessa(g, v);
        }
        // for(int i = 0; i * i < unvisited.size(); i++) { } maybe remove
        // ~sqrt(unvisited) vertices to make it more interesting?
        for (Vertex v : unvisited) {
            v.setActive(false);
            aM.setVertexColor(v, new int[] { 100, 100, 100 }, paintDelay);
        }
    }

    private int paintDelay;

    @Override
    public void setPaintDelay(int delay) {
        paintDelay = delay;
    }
}
