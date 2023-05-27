package pl.edu.tcs.graph.viewmodel;

import javafx.scene.paint.Paint;

public interface DrawableEdge {
    public Edge getEdge();

    Paint getStroke();

    void setStroke(Paint newPaint);
}
