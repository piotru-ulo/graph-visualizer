package pl.edu.tcs.graph.model;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Vertex;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrawableGridVertex implements DrawableVertex {
    private final GridVertex underlyingVertex;
    private final Rectangle toDraw;

    double x;
    double y;
    double size;

    @Override
    public Collection<Node> toDraw() {
        return List.of(new Node[]{toDraw});
    }

    public DrawableGridVertex(GridVertex v, double x, double y, double size) {
        this.underlyingVertex = v;
        this.x = x;
        this.y = y;
        this.size = size;
        toDraw = new Rectangle(x, y, size, size);
        toDraw.setStroke(Paint.valueOf("black"));
        toDraw.setFill(Paint.valueOf("white"));
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
        return toDraw.getFill();
    }

    @Override
    public void setFill(Paint newPaint) {
        toDraw.setFill(newPaint);
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
