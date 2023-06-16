package pl.edu.tcs.graph.algo;

import lombok.Setter;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;

import java.util.*;
import java.util.function.Function;

public class GameOfLife implements Algorithm {

    private Set<Vertex> alive;
    @Setter
    private AlgoMiddleman algoMiddleman;

    public void initialize(Graph g) {
        alive = new HashSet<>();
        for (Vertex v : g.getVertices()) {
            if (!v.isActive()) {
                algoMiddleman.setVertexColor(v, new int[] { 235, 242, 233 }, 0);
                alive.add(v);
            } else {
                algoMiddleman.setVertexColor(v, new int[] { 147, 112, 82 }, 0);
            }
        }
    }

    private ArrayList<Vertex> hack(Graph g, Vertex v) {
        Set<Vertex> visited = new HashSet<>();
        visited.add(v);
        for (int rep = 0; rep < 2; rep++) {
            for (Vertex u : visited) {
                Set<Vertex> newVisited = new HashSet<>(visited);
                for (Vertex to : g.getIncidentVertices(u)) {
                    if (Math.max(Math.abs(algoMiddleman.getX(to).get() - algoMiddleman.getX(v).get()),
                            Math.abs(algoMiddleman.getY(to).get() - algoMiddleman.getY(v).get())) <= 1)
                        newVisited.add(to);
                }
                visited = newVisited;
            }
        }
        visited.remove(v);
        System.out.println(visited.size());
        return new ArrayList<Vertex>(visited);
    }

    private boolean willLive(Vertex v, Graph g) {
        int crowd = 0;
        for (Vertex n : hack(g, v)) {
            if (alive.contains(n))
                crowd++;
        }
        if (alive.contains(v))
            return (crowd == 2 || crowd == 3);
        if (!alive.contains(v))
            return (crowd == 3);
        return false;
    }

    private int paintDelay = 1000;

    @Override
    public void run(Graph g, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        try {
            while (true) {
                Set<Vertex> newAlive = new HashSet<>();
                for (Vertex v : g.getVertices()) {
                    if (!v.isActive())
                        continue;
                    if (willLive(v, g)) {
                        newAlive.add(v);
                        algoMiddleman.setVertexColor(v, new int[] { 235, 242, 233 }, 0);
                    } else {
                        algoMiddleman.setVertexColor(v, new int[] { 147, 112, 82 }, 0);
                    }
                }
                if (alive.equals(newAlive))
                    break;
                alive = newAlive;
                Thread.sleep(paintDelay);
            }
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return null;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }

    @Override
    public Function<? super DrawableVertex, Object> getVertexOnclick() {
        return dv -> {
            Vertex v = dv.getVertex();
            if (alive.contains(v))
                alive.remove(v);
            else
                alive.add(v);
            if (!alive.contains(v))
                algoMiddleman.setVertexColor(v, new int[] { 147, 112, 82 }, 0);
            else
                algoMiddleman.setVertexColor(v, new int[] { 235, 242, 233 }, 0);
            return null;
        };
    }

    @Override
    public void setPaintDelay(int delay) {
        paintDelay = delay;
    }

    @Override
    public void setAlgoMiddleman(AlgoMiddleman algoMiddleman) {
        this.algoMiddleman = algoMiddleman;
    }
}
