package pl.edu.tcs.graph.model;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import pl.edu.tcs.graph.viewmodel.DrawableEdge;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static pl.edu.tcs.graph.view.GraphVisualization.magic;

public class DrawableEdgeImpl implements DrawableEdge {
    private Edge underlyingEdge;
    private Paint stroke;
    private DrawableVertex from;
    private DrawableVertex to;

    public DrawableEdgeImpl(Edge underlyingEdge, DrawableVertex from, DrawableVertex to) {
        stroke = javafx.scene.paint.Color.BLACK;
        this.underlyingEdge = underlyingEdge;
        this.from = from;
        this.to = to;
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

    @Override
    public Collection<Node> toDraw() {
        Line line = new Line(from.getX() + magic,
                from.getY() + magic,
                to.getX() + magic,
                to.getY() + magic);
        line.setStroke(getStroke());

        double arrowStrokeWidth = underlyingEdge.getWeight();
        line.setStrokeWidth(Math.sqrt(Math.max(1, arrowStrokeWidth)));

        if (!underlyingEdge.isDirected())
            return List.of(line);
        double slope = (double) (line.getEndY() - line.getStartY())
                / (line.getEndX() - line.getStartX());
        double lineAngle = Math.atan(slope);
        double arrowAngle = line.getStartX() > line.getEndX() ? Math.toRadians(45) : -Math.toRadians(225);

        double arrowX = (line.getStartX() + line.getEndX()) / 2.;
        double arrowY = (line.getStartY() + line.getEndY()) / 2.;

        double len = Math.sqrt((line.getStartX() - line.getEndX())
                * (line.getStartX() - line.getEndX())
                + (line.getStartY() - line.getEndY()) * (line.getStartY() - line.getEndY()));
        double arrowLength = len / 20.;

        Line arrow1 = new Line();
        arrow1.setStartX(arrowX);
        arrow1.setStartY(arrowY);
        arrow1.setEndX(arrowX + arrowLength * Math.cos(lineAngle - arrowAngle));
        arrow1.setEndY(arrowY + arrowLength * Math.sin(lineAngle - arrowAngle));
        arrow1.setStrokeWidth(Math.sqrt(Math.max(1, arrowStrokeWidth)));

        Line arrow2 = new Line();
        arrow2.setStartX(arrowX);
        arrow2.setStartY(arrowY);
        arrow2.setEndX(arrowX + arrowLength * Math.cos(lineAngle + arrowAngle));
        arrow2.setEndY(arrowY + arrowLength * Math.sin(lineAngle + arrowAngle));
        arrow2.setStrokeWidth(Math.sqrt(Math.max(1, arrowStrokeWidth)));
        return Arrays.asList(arrow1, arrow2, line);
    }

    public void setFrom(DrawableVertex from) {
        this.from = from;
    }

    public void setTo(DrawableVertex to) {
        this.to = to;
    }

}
