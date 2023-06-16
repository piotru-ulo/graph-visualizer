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
public class Bipartition implements Algorithm {
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

    private Map<Vertex, Boolean> color;

    private void dfs(boolean col, Graph g, Vertex u, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        if (col)
            algoMiddleman.setVertexColor(u, 0, 128, 0);
        else
            algoMiddleman.setVertexColor(u, 255, 255, 0);
        color.put(u, col);
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to.isActive() && !color.containsKey(to)) {
                dfs(!col, g, to, algoMiddleman);
            } else if (to.isActive() && color.get(to).equals(col)) {
                throw new AlgorithmException("Given graph has an odd-length cycle.");
            }
        }
    }

    @Override
    public void run(Graph g, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        color = new HashMap<>();
        for (Vertex v : g.getVertices())
            if (v.isActive())
                aM.instantSetVertexColor(v, 255, 255, 255);
        for (Edge e : g.getEdges())
            aM.instantSetEdgeColor(e, 0, 0, 0);
        try {
            for (Vertex v : g.getVertices()) {
                if (!v.isActive() || color.containsKey(v))
                    continue;
                dfs(false, g, v, aM);
            }
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
