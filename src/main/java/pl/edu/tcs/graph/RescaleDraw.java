package pl.edu.tcs.graph;

import java.util.Collection;

public class RescaleDraw implements DrawStrategy {
    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> dw) {
        // rescale
        double x_min = Double.MAX_VALUE, y_min = Double.MAX_VALUE;
        double x_max = Double.MIN_VALUE, y_max = Double.MIN_VALUE;
        for (DrawableVertex v : dw) {
            x_min = Math.min(x_min, v.getX());
            y_min = Math.min(y_min, v.getY());

            x_max = Math.max(x_max, v.getX());
            y_max = Math.max(y_max, v.getY());
        }
        double currentWidth = x_max - x_min;
        double currrentHeight = y_max - y_min;

        double x_scale = width / currentWidth;
        double y_scale = height / currrentHeight;
        double scale = 0.9 * Math.min(x_scale, y_scale);

        // center = (x_min + x_max, y_min + y_max)
        // and the offset = center / 2 * scale

        for (DrawableVertex v : dw) {
            v.setX(v.getX() * scale - (x_min + x_max) / 2 * scale);
            v.setY(v.getY() * scale - (y_min + y_max) / 2 * scale);
        }
    }
}