package pl.edu.tcs.graph.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

public class MST implements Algorithm {
    private Collection<AlgorithmProperties> properties = Arrays.asList();

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
    public void run(Graph g, AlgoMiddleman algoMiddleman,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        forest = new HashMap<>();
        for (Vertex v : g.getVertices())
            forest.put(v, v);
        ArrayList<Edge> edges = new ArrayList<>(g.getEdges());
        Collections.sort(edges, Comparator.comparingInt(Edge::getWeight));
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
