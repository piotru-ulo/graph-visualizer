package pl.edu.tcs.graph.algo;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

import java.util.Collection;
import java.util.Map;

public class TestGeoAlgo implements Algorithm {
    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        for (Vertex v : g.getVertices())
            System.out.println(aM.getX(v)+ " , " + aM.getY(v));
    }

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return null;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return null;
    }
}
