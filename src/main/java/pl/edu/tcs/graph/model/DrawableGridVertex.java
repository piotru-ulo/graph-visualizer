package pl.edu.tcs.graph.model;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class DrawableGridVertex implements DrawableVertex {

    private final GridVertex underlyingVertex;
    private final Rectangle toDraw;

    private double x;
    private double y;
    private double size;

    @Override
    public Collection<Node> toDraw() {
        return List.of(new Node[] { toDraw });
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

    public DrawableGridVertex(GridVertex v, double x, double y, double size,
            Function<? super DrawableGridVertex, Object> onClick) {
        this.underlyingVertex = v;
        this.x = x;
        this.y = y;
        this.size = size;
        toDraw = new Rectangle(x, y, size, size);
        toDraw.setOnMouseClicked(e -> onClick.apply(this));
        toDraw.setStroke(Paint.valueOf("black"));
        toDraw.setFill(Paint.valueOf("white"));
    }

    @Override
    public void setOnclick(Function<? super DrawableVertex, Object> onClick) {
        toDraw.setOnMouseClicked(e -> onClick.apply(this));
    }

    @Override
    public void setActions(Collection<Algorithm.VertexAction> actions) {
        ContextMenu contextMenu = new ContextMenu();
        if(actions==null) actions = new ArrayList<>();
        for(var action : actions) {
            MenuItem item = new MenuItem(action.getName());
            item.setOnAction(event-> {
                action.apply(underlyingVertex);
            });
            contextMenu.getItems().add(item);
        }
        toDraw.setOnContextMenuRequested(e ->
                contextMenu.show(toDraw, Side.BOTTOM, e.getX(), e.getY()-2*DrawableVertexImpl.defaultSize));
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
