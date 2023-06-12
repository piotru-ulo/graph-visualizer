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
        for(Vertex v : g.getVertices()) {
            algoMiddleman.setVertexColor(v, 235, 242, 233);
        }
    }

    boolean willLive(Vertex v, Graph g) {
        int crowd = 0;
        for(Vertex n : g.getIncidentVertices(v)) {
            if(alive.contains(n))
                crowd++;
        }
        return crowd == 3 || crowd == 4;
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        algoMiddleman = aM;
        try {
            while(true) {
                Set<Vertex> newAlive = new HashSet<>();
                for (Vertex v : alive) {
                    if(willLive(v, g))
                        newAlive.add(v);
                    else {
                        algoMiddleman.setVertexColor(v, 235, 242, 233);
                    }
                    for(Vertex n : g.getIncidentVertices(v))
                        if(willLive(n, g)) {
                            if(!alive.contains(v))
                                algoMiddleman.setVertexColor(v, 147, 112, 82);
                            newAlive.add(n);
                        }
                }
                alive = newAlive;
                Thread.sleep(1000);
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
            if(alive.contains(v))
                alive.remove(v);
            else
                alive.add(v);
            if(algoMiddleman!=null) {
                if(!alive.contains(v)) {
                    algoMiddleman.setVertexColor(v, 235, 242, 233);
                } else {
                    algoMiddleman.setVertexColor(v, 147, 112, 82);
                }
            }
            return null;
        };
    }
}
