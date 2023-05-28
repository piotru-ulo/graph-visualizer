package pl.edu.tcs.graph.viewmodel;

import javafx.scene.paint.Paint;

public interface DrawableEdge {
    public Edge getEdge();

    public Paint getStroke();

    public void setStroke(Paint newPaint);
}
