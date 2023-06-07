package pl.edu.tcs.graph.viewmodel;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

import java.util.Collection;

public interface DrawableEdge {
    public Edge getEdge();

    public Paint getStroke();

    Collection<Node> toDraw();

    void setStroke(Paint newPaint);
}
