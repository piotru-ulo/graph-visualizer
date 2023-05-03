package pl.edu.tcs.graph;

import java.util.Collection;

public class CircularDraw implements DrawStrategy {
    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> drawableVertices) {
        double angle = 2.0 * Math.PI / drawableVertices.size();
        int i = 0;
        for (DrawableVertex v : drawableVertices) {
            v.setX(Math.cos(i * angle));
            v.setY(Math.sin(i * angle));
            i++;
        }
    }
}
