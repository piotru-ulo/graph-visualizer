package pl.edu.tcs.graph.viewmodel;

import javafx.scene.paint.Paint;

public interface DrawableVertex {
    public Vertex getVertex();

    public void setX(double newX);

    public void setY(double newY);

    Paint getFill();

    void setFill(Paint newPaint);

    public double getX();

    public double getY();
}
