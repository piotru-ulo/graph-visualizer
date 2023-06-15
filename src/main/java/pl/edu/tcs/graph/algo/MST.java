package pl.edu.tcs.graph.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
public class MST implements Algorithm {
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
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        forest = new HashMap<>();
        for (Vertex v : g.getVertices())
            forest.put(v, v);
        ArrayList<Edge> edges = new ArrayList<>(g.getEdges());
        edges.sort(Comparator.comparingInt(Edge::getWeight));
        for (Edge e : g.getEdges())
            aM.instantSetEdgeColor(e, 0, 0, 0);
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
                aM.setEdgeColor(e, 255, 182, 193);
        }
    }
}
