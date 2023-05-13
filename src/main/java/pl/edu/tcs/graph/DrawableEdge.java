package pl.edu.tcs.graph;

import javafx.scene.paint.Paint;

public interface DrawableEdge {
    public Edge getEdge();

    Paint getFill();

    void setFill(Paint newPaint);
}
