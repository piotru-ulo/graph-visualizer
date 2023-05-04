package pl.edu.tcs.graph;

import javafx.scene.paint.Paint;

public interface DrawableVertex {
    public Vertex getVertex();

    public void setX(double newX);

    public void setY(double newY);

    Paint getFill();

    Paint getStroke();

    void setFill(Paint newPaint);

    void setStroke(Paint newPaint);

    public double getX();

    public double getY();
}
