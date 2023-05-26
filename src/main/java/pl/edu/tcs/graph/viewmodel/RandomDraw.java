package pl.edu.tcs.graph.viewmodel;

import java.util.Collection;
import java.util.Random;

public class RandomDraw implements DrawStrategy {
    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> dw) {
        Random r = new Random();
        for (DrawableVertex v : dw) {
            v.setX(r.nextDouble() * width);
            v.setY(r.nextDouble() * height);
        }
    }
}