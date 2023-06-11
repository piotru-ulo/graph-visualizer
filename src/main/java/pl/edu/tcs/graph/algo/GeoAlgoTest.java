package pl.edu.tcs.graph.algo;

import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

import java.util.Collection;
import java.util.Map;

public class GeoAlgoTest implements Algorithm<GeoVertex, Edge> {

    @Override
    public void run(Graph<? extends GeoVertex, ? extends Edge> g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements) throws AlgorithmException {
        for(GeoVertex gv : g.getVertices()) {
            System.out.println(gv.getX()+ " , " + gv.getY());
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
}
