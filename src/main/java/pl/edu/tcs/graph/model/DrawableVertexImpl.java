package pl.edu.tcs.graph.model;

import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Vertex;

public class DrawableVertexImpl implements DrawableVertex {
    private Vertex underlyingVertex;
    private double x, y;
    private Paint fill;

    public DrawableVertexImpl(Vertex underlyingVertex) {
        x = y = 0.0;
        fill = javafx.scene.paint.Color.WHITE;
        this.underlyingVertex = underlyingVertex;
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
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Paint getFill() {
        return fill;
    }

    @Override
    public void setFill(Paint newPaint) {
        fill = newPaint;
    }
}
