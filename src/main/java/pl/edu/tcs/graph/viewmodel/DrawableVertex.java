package pl.edu.tcs.graph.viewmodel;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.Vertex;

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

    void setActions(Collection<Algorithm.VertexAction> actions);
}
