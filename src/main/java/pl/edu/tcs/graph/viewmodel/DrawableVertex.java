package pl.edu.tcs.graph.viewmodel;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.model.DrawableGridVertex;

import java.util.Collection;
import java.util.function.Function;

public interface DrawableVertex {
    public Vertex getVertex();

    public void setX(double newX);

    public void setY(double newY);

    public Paint getFill();

    public void setFill(Paint newPaint);

    public double getX();

    public double getY();

    Collection<Node> toDraw();

    void setOnclick(Function<? super DrawableVertex, Object> onClick);

    void setContextMenu(ContextMenu contextMenu);
}
