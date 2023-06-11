package pl.edu.tcs.graph.viewmodel;

import pl.edu.tcs.graph.model.Graph;

import java.util.Collection;

public interface DrawStrategy {
    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> drawableVertices);
}