package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import pl.edu.tcs.graph.viewmodel.CircularDraw;
import pl.edu.tcs.graph.viewmodel.FruchtermanReingoldDraw;
import pl.edu.tcs.graph.viewmodel.RandomDraw;
import pl.edu.tcs.graph.viewmodel.RescaleDraw;
import pl.edu.tcs.graph.model.DrawableEdgeImpl;
import pl.edu.tcs.graph.model.DrawableVertexImpl;
import pl.edu.tcs.graph.model.GraphImpl;
import pl.edu.tcs.graph.viewmodel.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GraphVisualization {
    AnchorPane drawingPane;
    int height = 600;
    int width = 800;
    double magic = 18 * Math.sqrt(2); // TODO: FIX THIS!

    StackPane getVertex(DrawableVertex v) {
        Circle circle = new Circle(20.0);
        circle.setFill(v.getFill());
        circle.setStroke(v.getStroke());
        Text text = new Text(Integer.valueOf(v.getVertex().getId()).toString());
        StackPane stackPane = new StackPane(circle, text);
        stackPane.setLayoutX(v.getX());
        stackPane.setLayoutY(v.getY());
        return stackPane;
    }

    private Map<Vertex, DrawableVertex> drawableVertexMap;

    Line getEdge(DrawableEdge e) {
        Vertex one = null, two = null;
        for (Vertex v : e.getEdge().getEndpoints())
            if (one == null)
                one = v;
            else
                two = v;
        Line returnLine = new Line(drawableVertexMap.get(one).getX() + magic,
                drawableVertexMap.get(one).getY() + magic,
                drawableVertexMap.get(two).getX() + magic,
                drawableVertexMap.get(two).getY() + magic);
        returnLine.setFill(e.getFill());
        return returnLine;
    }

    private Graph g;
    private Map<Edge, DrawableEdge> drawableEdgeMap;

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
        for (i = 0; i < 15; i++)
            g.insertEdge(r.nextInt(8), r.nextInt(8), 1, i);
    }

    public void updateDrawing() {
        drawingPane.getChildren().clear();
        drawableVertexMap = new HashMap<>();
        drawableEdgeMap = new HashMap<>();

        for (Vertex v : g.getVertices())
            drawableVertexMap.putIfAbsent(v, new DrawableVertexImpl(v));
        for (Edge e : g.getEdges())
            drawableEdgeMap.putIfAbsent(e, new DrawableEdgeImpl(e));

        // TODO: change 1600x800 to something smarter?
        DrawStrategy strategy = new RandomDraw();
        strategy.draw(width, height, g, drawableVertexMap.values());

        strategy = new CircularDraw();
        strategy.draw(width, height, g, drawableVertexMap.values());

        strategy = new FruchtermanReingoldDraw();
        strategy.draw(width, height, g, drawableVertexMap.values());

        strategy = new RescaleDraw();
        strategy.draw(width, height, g, drawableVertexMap.values());

        for (Vertex v : g.getVertices())
            drawingPane.getChildren().add(getVertex(drawableVertexMap.get(v)));
        for (Edge e : g.getEdges())
            drawingPane.getChildren().add(0, getEdge(drawableEdgeMap.get(e)));

    }

    public AnchorPane getNode() {
        return drawingPane;
    }
}
