package pl.edu.tcs.graph.viewmodel;

import java.util.Collection;

public interface DrawStrategy {
    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> drawableVertices);
}