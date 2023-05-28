package pl.edu.tcs.graph.model;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.Collection;
import java.util.List;

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
    public Collection<Node> toDraw() {
        Circle circle = new Circle(20.0);
        circle.setFill(getFill());
        circle.setStroke(javafx.scene.paint.Color.BLACK);
        Text text = new Text(Integer.valueOf(getVertex().getId()).toString());
        StackPane stackPane = new StackPane(circle, text);
        stackPane.setLayoutX(getX());
        stackPane.setLayoutY(getY());
        return List.of(new StackPane[]{stackPane});
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
