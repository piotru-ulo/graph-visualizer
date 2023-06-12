package pl.edu.tcs.graph.algo;

import lombok.AllArgsConstructor;
import lombok.Getter;
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

    Set<Vertex> alive;
    @Setter
    AlgoMiddleman algoMiddleman;

    public void initialize(Graph g, AlgoMiddleman aM) {
        algoMiddleman = aM;
        System.out.println("initializing");
        alive = new HashSet<>();
        for (Vertex v : g.getVertices()) {
            algoMiddleman.setVertexColor(v, 235, 242, 233);
            alive.add(v);
        }
    }

    private ArrayList<Vertex> hack(Graph g, Vertex v) {
        Set<Vertex> visited = new HashSet<>();
        visited.add(v);
        for (int rep = 0; rep < 2; rep++) {
            for (Vertex u : visited) {
                Set<Vertex> newVisited = new HashSet<>(visited);
                for (Vertex to : g.getIncidentVertices(u)) {
                    if (Math.max(Math.abs(algoMiddleman.getX(to) - algoMiddleman.getX(v)),
                            Math.abs(algoMiddleman.getY(to) - algoMiddleman.getY(v))) <= 1)
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
        System.out.println(crowd);
        if (alive.contains(v))
            return (crowd == 2 || crowd == 3);
        if (!alive.contains(v))
            return (crowd == 3);
        return false;
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        algoMiddleman = aM;
        try {
            while (true) {
                Set<Vertex> newAlive = new HashSet<>();
                for (Vertex v : g.getVertices()) {
                    if (willLive(v, g)) {
                        newAlive.add(v);
                        aM.setVertexColor(v, 235, 242, 233);
                    } else {
                        aM.setVertexColor(v, 147, 112, 82);
                    }
                }
                alive = newAlive;
                Thread.sleep(1000);
                System.out.println("?");
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
            System.out.println("hello from the game");
            if (alive.contains(v))
                alive.remove(v);
            else
                alive.add(v);
            if (algoMiddleman != null) {
                if (!alive.contains(v)) {
                    algoMiddleman.setVertexColor(v, 147, 112, 82);
                } else {
                    algoMiddleman.setVertexColor(v, 235, 242, 233);
                }
            }
            return null;
        };
    }
}
