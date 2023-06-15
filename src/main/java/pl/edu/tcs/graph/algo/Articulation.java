package pl.edu.tcs.graph.algo;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
public class Articulation implements Algorithm {
    AlgoMiddleman aM;
    @Override
    public void setAlgoMiddleman(AlgoMiddleman aM) {
        this.aM = aM;
    }
    private final Collection<AlgorithmProperties> properties = Arrays.asList();

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    private Map<Vertex, Boolean> visited;
    private Map<Vertex, Integer> preOrder, low;
    private int time;

    private void dfs(Graph g, Vertex u, Vertex p, Vertex startVertex, AlgoMiddleman aM)
            throws AlgorithmException {
        visited.put(u, true);
        time++;
        preOrder.put(u, time);
        low.put(u, time);
        int childrenCount = 0;
        for (Vertex to : g.getIncidentVertices(u)) {
            if (to.equals(p) || !to.isActive())
                continue;
            if (visited.containsKey(to))
                low.put(u, Math.min(low.get(u), preOrder.get(to)));
            else {
                dfs(g, to, u, startVertex, aM);
                low.put(u, Math.min(low.get(u), low.get(to)));
                if (low.get(to) >= preOrder.get(u) && !u.equals(startVertex))
                    aM.setVertexColor(u, new int[] {255, 160, 122});
                ++childrenCount;
            }
        }
        if (u.equals(startVertex) && childrenCount > 1)
            aM.setVertexColor(u, new int[]{255, 0, 0});
    }

    @Override
    public void run(Graph g,
            Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        for (Vertex v : g.getVertices())
            if (v.isActive())
                aM.instantSetVertexColor(v, new int[]{255, 255, 255});
        for (Edge e : g.getEdges())
            aM.instantSetEdgeColor(e, new int[]{0, 0, 0});

        visited = new HashMap<>();
        preOrder = new HashMap<>();
        low = new HashMap<>();
        time = 0;
        try {
            for (Vertex v : g.getVertices())
                if (v.isActive() && !visited.containsKey(v))
                    dfs(g, v, v, v, aM);
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
