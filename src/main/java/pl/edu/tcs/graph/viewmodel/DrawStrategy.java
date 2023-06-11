package pl.edu.tcs.graph.viewmodel;

import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;

import java.util.Collection;

public interface DrawStrategy {
//    TODO (maybe): generify DrawStrategy?
    public void draw(double width, double height, Graph<? extends Vertex,? extends Edge> graph, Collection<DrawableVertex> drawableVertices);
}