package pl.edu.tcs.graph.algo;

import java.util.*;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;

public class MST implements Algorithm<Vertex, Edge> {
    private final Collection<AlgorithmProperties> properties = List.of();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    private Map<Vertex, Vertex> forest;

    Vertex findTopmost(Vertex v) {
        while (!forest.get(v).equals(v))
            v = forest.get(v);
        return v;
    }

    private boolean mergeComponents(Vertex a, Vertex b) {
        a = findTopmost(a);
        b = findTopmost(b);
        if (a.equals(b))
            return false;
        forest.put(a, b); // a podpinam do b
        return true;
    }

    @Override
    public void run(Graph<? extends Vertex, ? extends Edge> g, AlgoMiddleman algoMiddleman,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        forest = new HashMap<>();
        for (Vertex v : g.getVertices())
            forest.put(v, v);
        ArrayList<Edge> edges = new ArrayList<>(g.getEdges());
        edges.sort(Comparator.comparingInt(Edge::getWeight));
        for (Edge e : edges) {
            if (e.isDirected())
                throw new AlgorithmException(
                        "MST exists only for undirected graphs. You can implement your own arborescence though!");
            Vertex one = null, two = null;
            for (Vertex v : e.getEndpoints()) {
                if (one == null)
                    one = v;
                else
                    two = v;
            }
            if (mergeComponents(one, two))
                algoMiddleman.setEdgeColor(e, 255, 182, 193);
        }
    }
}
