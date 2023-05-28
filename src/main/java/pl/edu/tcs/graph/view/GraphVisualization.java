package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;
import pl.edu.tcs.graph.model.DrawableEdgeImpl;
import pl.edu.tcs.graph.model.DrawableVertexImpl;
import pl.edu.tcs.graph.model.GraphImpl;
import pl.edu.tcs.graph.viewmodel.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GraphVisualization {
    private AnchorPane drawingPane;
    private int height = 600;
    private int width = 800;
    private double magic = 15 * Math.sqrt(2); // TODO: FIX THIS!

    private StackPane getVertex(DrawableVertex v) {
        Circle circle = new Circle(20.0);
        circle.setFill(v.getFill());
        circle.setStroke(javafx.scene.paint.Color.BLACK);
        Text text = new Text(Integer.valueOf(v.getVertex().getId()).toString());
        StackPane stackPane = new StackPane(circle, text);
        stackPane.setLayoutX(v.getX());
        stackPane.setLayoutY(v.getY());
        return stackPane;
    }

    private Map<Vertex, DrawableVertex> drawableVertexMap;

    private Collection<Line> getEdge(DrawableEdge e, boolean directed) {
        Vertex one = null, two = null;
        for (Vertex v : e.getEdge().getEndpoints())
            if (one == null)
                one = v;
            else
                two = v;
        Line line = new Line(drawableVertexMap.get(one).getX() + magic,
                drawableVertexMap.get(one).getY() + magic,
                drawableVertexMap.get(two).getX() + magic,
                drawableVertexMap.get(two).getY() + magic);
        line.setStroke(e.getStroke());
        if (!directed)
            return Arrays.asList(line);
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

        Line arrow2 = new Line();
        arrow2.setStartX(arrowX);
        arrow2.setStartY(arrowY);
        arrow2.setEndX(arrowX + arrowLength * Math.cos(lineAngle + arrowAngle));
        arrow2.setEndY(arrowY + arrowLength * Math.sin(lineAngle + arrowAngle));
        return Arrays.asList(arrow1, arrow2, line);
    }

    private Graph g;
    private Map<Edge, DrawableEdge> drawableEdgeMap;

    public Graph getGraph() {
        return g;
    }

    public GraphVisualization() {
        g = new GraphImpl();
        drawingPane = new AnchorPane();
    }

    public void initialize() {
        g = new GraphImpl();
    }

    public void fromAdjacencyList(int[] input) {
        g = new GraphImpl();
        for (int i = 0; i < input.length; i += 2) {
            if (!g.containsVertex(input[i]))
                g.insertVertex(input[i]);
            if (!g.containsVertex(input[i + 1]))
                g.insertVertex(input[i + 1]);
            g.insertEdge(input[i], input[i + 1], 0, i / 2);
        }
    }

    public void fakeValues(int i) {
        for (i = 0; i < 8; i++)
            g.insertVertex(i);
        Random r = new Random();
        Set<Pair<Integer, Integer>> mapka = new HashSet<>();
        for (i = 0; i < 15; i++) {
            int a = r.nextInt(8), b = r.nextInt(8);
            if (mapka.contains(new Pair<Integer, Integer>(a, b)) ||
                    mapka.contains(new Pair<Integer, Integer>(b, a)))
                continue;
            mapka.add(new Pair<Integer, Integer>(a, b));
            g.insertEdge(a, b, 1, i);

        }
    }

    public boolean setVertexColor(Vertex v, Paint p) {
        if (!drawableVertexMap.containsKey(v))
            return false;
        drawableVertexMap.get(v).setFill(p);
        return true;
    }

    public boolean setEdgeColor(Edge e, Paint p) {
        if (!drawableEdgeMap.containsKey(e))
            return false;
        drawableEdgeMap.get(e).setStroke(p);
        return true;
    }

    public void updateDrawing(boolean redraw) {
        drawingPane.getChildren().clear();

        if (redraw) {
            drawableVertexMap = new HashMap<>();
            drawableEdgeMap = new HashMap<>();
        }

        for (Vertex v : g.getVertices())
            drawableVertexMap.putIfAbsent(v, new DrawableVertexImpl(v));
        for (Edge e : g.getEdges())
            drawableEdgeMap.putIfAbsent(e, new DrawableEdgeImpl(e));

        if (redraw) {
            DrawStrategy strategy = new RandomDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());

            strategy = new CircularDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());

            strategy = new FruchtermanReingoldDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());

            strategy = new RescaleDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());
        }

        for (Vertex v : g.getVertices())
            drawingPane.getChildren().add(getVertex(drawableVertexMap.get(v)));
        for (Edge e : g.getEdges())
            for (Line l : getEdge(drawableEdgeMap.get(e), false)) // TODO: not always '''true'''
                drawingPane.getChildren().add(0, l);
    }

    public AnchorPane getNode() {
        return drawingPane;
    }
}
