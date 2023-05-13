package pl.edu.tcs.graph;

import javafx.scene.paint.Paint;

public class DrawableEdgeImpl implements DrawableEdge {

    private Edge underlyingEdge;
    private Paint fill;

    DrawableEdgeImpl(Edge underlyingEdge) {
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
