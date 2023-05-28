package pl.edu.tcs.graph.model;

import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.viewmodel.DrawableEdge;
import pl.edu.tcs.graph.viewmodel.Edge;

public class DrawableEdgeImpl implements DrawableEdge {
    private Edge underlyingEdge;
    private Paint stroke;

    public DrawableEdgeImpl(Edge underlyingEdge) {
        stroke = javafx.scene.paint.Color.BLACK;
        this.underlyingEdge = underlyingEdge;
    }

    @Override
    public Edge getEdge() {
        return underlyingEdge;
    }

    @Override
    public Paint getStroke() {
        return stroke;
    }

    @Override
    public void setStroke(Paint newPaint) {
        stroke = newPaint;
    }
}
