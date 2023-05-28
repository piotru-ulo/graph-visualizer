package pl.edu.tcs.graph.model;

import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Vertex;

public class DrawableGridVertex implements DrawableVertex {
    private final GridVertex underlyingVertex;
    private Paint fill;

    double x;
    double y;

    public DrawableGridVertex(GridVertex v, double x, double y) {
        this.underlyingVertex = v;
        this.x = x;
        this.y = y;
        fill = Paint.valueOf("white");
    }

    @Override
    public Vertex getVertex() {
        return underlyingVertex;
    }

    @Override
    public void setX(double newX) {
        x = newX;
    }

    @Override
    public void setY(double newY) {
        y = newY;
    }

    @Override
    public Paint getFill() {
        return fill;
    }

    @Override
    public void setFill(Paint newPaint) {
        fill = newPaint;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
