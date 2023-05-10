package pl.edu.tcs.graph;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GraphGenerator {
    private Map<Vertex, DrawableVertex> mapka = new HashMap<>();
    double magic = 18 * Math.sqrt(2); // TODO: FIX THIS!

    public GraphGenerator(){}
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

    public void initialize() {
        g = new GraphImpl();
    }

    public void fakeValues(int i) {
        if (i == 0) {
            g.insertVertex(0);
            g.insertVertex(1);
            g.insertVertex(2);
            g.insertVertex(3);
            g.insertVertex(4);
            g.insertEdge(0, 1, 0, 0);
            g.insertEdge(1, 2, 0, 1);
            g.insertEdge(2, 3, 0, 2);
            g.insertEdge(2, 4, 0, 3);
            g.insertEdge(4, 0, 0, 4);
            g.insertEdge(0, 2, 0, 5);
            return;
        }
        for (i = 0; i < 8; i++)
            g.insertVertex(i);
        Random r = new Random();
        for (i = 0; i < 15; i++)
            g.insertEdge(r.nextInt(8), r.nextInt(8), 1, i);
    }

    public void updateDrawing(SplitPane splitPane) {
        AnchorPane anchorPane = (AnchorPane) splitPane.lookup("#graphPane");
        anchorPane.getChildren().clear();
        drawableVertexMap = new HashMap<>();
        drawableEdgeMap = new HashMap<>();

        for (Vertex v : g.getVertices())
            drawableVertexMap.putIfAbsent(v, new DrawableVertexImpl(v));
        for (Edge e : g.getEdges())
            drawableEdgeMap.putIfAbsent(e, new DrawableEdgeImpl(e));

        // TODO: change 1600x800 to something smarter?
        DrawStrategy strategy = new RandomDraw();
        strategy.draw( 910, 610, g, drawableVertexMap.values());

        strategy = new CircularDraw();
        strategy.draw( 910, 610, g, drawableVertexMap.values());
        strategy = new FruchtermanReingoldDraw();
        strategy.draw( 910, 610, g, drawableVertexMap.values());


        strategy = new RescaleDraw();
        strategy.draw( 910, 610, g, drawableVertexMap.values());



        for (Vertex v : g.getVertices())
            anchorPane.getChildren().add(getVertex(drawableVertexMap.get(v)));
        for (Edge e : g.getEdges())
            anchorPane.getChildren().add(0, getEdge(drawableEdgeMap.get(e)));

    }

}
