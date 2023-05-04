package pl.edu.tcs.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    Map<Vertex, DrawableVertex> mapka = new HashMap<>();
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

    void initialize() {
        g = new GraphImpl();
    }

    void fakeValues(int i) {
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

    void updateDrawing(Stage primaryStage) {
        AnchorPane drawingPane = new AnchorPane();
        drawableVertexMap = new HashMap<>();
        drawableEdgeMap = new HashMap<>();

        for (Vertex v : g.getVertices())
            drawableVertexMap.putIfAbsent(v, new DrawableVertexImpl(v));
        for (Edge e : g.getEdges())
            drawableEdgeMap.putIfAbsent(e, new DrawableEdgeImpl(e));

        // TODO: change 1600x800 to something smarter?
        DrawStrategy strategy = new RandomDraw();
        strategy.draw(1600, 800, g, drawableVertexMap.values());

        strategy = new CircularDraw();
        strategy.draw(1600, 800, g, drawableVertexMap.values());

        strategy = new FruchtermanReingoldDraw();
        for (int i = 0; i < 850; i++)
            strategy.draw(1600, 800, g, drawableVertexMap.values());

        strategy = new RescaleDraw();
        strategy.draw(1600, 800, g, drawableVertexMap.values());

        for (Vertex v : g.getVertices())
            drawingPane.getChildren().add(getVertex(drawableVertexMap.get(v)));
        for (Edge e : g.getEdges())
            drawingPane.getChildren().add(0, getEdge(drawableEdgeMap.get(e)));

        Button button = new Button("New graph");
        button.setOnAction(event -> {
            initialize();
            fakeValues(1);
            updateDrawing(primaryStage);
        });
        drawingPane.getChildren().add(button);

        Scene scene = new Scene(drawingPane, 1600, 800);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void start(Stage primaryStage) throws Exception { // How do i sleep here? xD
        Button button = new Button("Begin");
        button.setOnAction(event -> {
            initialize();
            fakeValues(0);
            updateDrawing(primaryStage);
        });
        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
