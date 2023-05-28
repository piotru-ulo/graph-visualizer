package pl.edu.tcs.graph.viewmodel;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

import java.util.Collection;

public interface DrawableVertex {
    public Vertex getVertex();

    public void setX(double newX);

    public void setY(double newY);

    Paint getFill();

    void setFill(Paint newPaint);

    public double getX();

    public double getY();

    Collection<Node> toDraw();
}
