package pl.edu.tcs.graph.model;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class DrawableVertexImpl implements DrawableVertex {
    public static double defaultSize = 20;
    private Vertex underlyingVertex;
    private final StackPane toDraw;
    private final Circle circle;


    public DrawableVertexImpl(Vertex underlyingVertex) {
        this.underlyingVertex = underlyingVertex;
        circle = new Circle(DrawableVertexImpl.defaultSize);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        Text text = new Text(Integer.valueOf(getVertex().getId()).toString());
        toDraw = new StackPane(circle, text);
    }

    @Override
    public void setOnclick(Function<? super DrawableVertex, Object> onClick) {
        toDraw.setOnMouseClicked(e -> onClick.apply(this));
    }

    @Override
    public void setActions(Collection<Algorithm.VertexAction> actions) {
        ContextMenu contextMenu = new ContextMenu();
        for(var action : actions) {
            MenuItem item = new MenuItem(action.getName());
            item.setOnAction(event-> {
                action.apply(underlyingVertex);
            });
            contextMenu.getItems().add(item);
        }
        toDraw.setOnContextMenuRequested(e -> {
            System.out.println("context menu requested");
            for(var action: actions)
                System.out.println(action.getName());
            contextMenu.show(circle, Side.BOTTOM, e.getX(), e.getY() - 2 * DrawableVertexImpl.defaultSize);
        });
    }

    @Override
    public Vertex getVertex() {
        return underlyingVertex;
    }

    @Override
    public void setX(double newX) {
        toDraw.setLayoutX(newX);
    }

    @Override
    public void setY(double newY) {
        toDraw.setLayoutY(newY);
    }

    @Override
    public double getX() {
        return toDraw.getLayoutX();
    }

    @Override
    public double getY() {
        return toDraw.getLayoutY();
    }

    @Override
    public Collection<Node> toDraw() {
        return List.of(new Node[]{toDraw});
    }

    @Override
    public Paint getFill() {
        return circle.getFill();
    }

    @Override
    public void setFill(Paint newPaint) {
        circle.setFill(newPaint);
    }
}
