package pl.edu.tcs.graph.model;

import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.viewmodel.DrawableEdge;
import pl.edu.tcs.graph.viewmodel.Edge;

public class DrawableEdgeImpl implements DrawableEdge {

    private Edge underlyingEdge;
    private Paint fill;

    public DrawableEdgeImpl(Edge underlyingEdge) {
        fill = javafx.scene.paint.Color.BLACK;
        this.underlyingEdge = underlyingEdge;
    }

    @Override
    public Edge getEdge() {
        return underlyingEdge;
    }

    @Override
    public Paint getFill() {
        return fill;
    }

    @Override
    public void setFill(Paint newPaint) {
        fill = newPaint;
    }

}
