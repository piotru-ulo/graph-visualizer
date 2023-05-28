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
    private Paint fill;

    double x;
    double y;
    double size;

    @Override
    public Collection<Node> toDraw() {
        List<Node> nodes = new ArrayList<>();
        Rectangle square = new Rectangle(x, y, size, size);
        square.setStroke(fill);
        nodes.add(square);
        return nodes;
    }

    public DrawableGridVertex(GridVertex v, double x, double y, double size) {
        this.underlyingVertex = v;
        this.x = x;
        this.y = y;
        this.size = size;
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
