package pl.edu.tcs.graph.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.Controller;
import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.model.Algorithm.VertexAction;
import pl.edu.tcs.graph.viewmodel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GraphVisualization implements Visualization {
    private AnchorPane drawingPane;
    private int height = 600;
    private int width = 800;
    public static final double magic = 15 * Math.sqrt(2); // TODO: FIX THIS!

    private Map<Vertex, DrawableVertex> drawableVertexMap;

    private Collection<Algorithm.VertexAction> vertexActions = new ArrayList<>();
    private Graph g;
    private Map<Edge, DrawableEdge> drawableEdgeMap;
    public void setWidth(int width) {
        this.width = width - 10;
    }
    public void setHeight(int height) {
        this.height = height - 10;
    }

    @Override
    public Graph getGraph() {
        return g;
    }

    @Override
    public void setGraph(Graph newGraph) {
        g = newGraph;
    }

    public GraphVisualization() {
        g = new GraphImpl();
        drawingPane = new AnchorPane();
    }

    @Override
    public void initialize() {
        g = new GraphImpl();
    }

    @Override
    public boolean setVertexColor(Vertex v, Paint p) {
        if (!drawableVertexMap.containsKey(v))
            return false;
        drawableVertexMap.get(v).setFill(p);
        return true;
    }

    @Override
    public boolean setEdgeColor(Edge e, Paint p) {
        if (!drawableEdgeMap.containsKey(e))
            return false;
        drawableEdgeMap.get(e).setStroke(p);
        return true;
    }

    @Override
    public void setVertexActions(Collection<Algorithm.VertexAction> vcm) {
        vertexActions = vcm;
        if (drawableVertexMap != null) {
            for (DrawableVertex dv : drawableVertexMap.values()) {
                dv.setActions(vertexActions);
            }
        }
    }

    @Override
    public void updateDrawing(boolean redraw) {
        drawingPane.getChildren().clear();

        if (redraw) {
            drawableVertexMap = new HashMap<>();
            drawableEdgeMap = new HashMap<>();
        }

        for (Vertex v : g.getVertices())
            drawableVertexMap.putIfAbsent(v, new DrawableVertexImpl(v));

        for (var dv : drawableVertexMap.values())
            dv.setActions(vertexActions);

        for (Edge e : g.getEdges()) {
            DrawableVertex one = null, two = null;
            for (Vertex v : e.getEndpoints())
                if (one == null)
                    one = drawableVertexMap.get(v);
                else
                    two = drawableVertexMap.get(v);
            drawableEdgeMap.putIfAbsent(e, new DrawableEdgeImpl(e, one, two));
        }

        if (redraw) {
            DrawStrategy strategy = new CircularDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());

            strategy = new FruchtermanReingoldDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());

            strategy = new RescaleDraw();
            strategy.draw(width, height, g, drawableVertexMap.values());
        }

        for (Edge e : g.getEdges())
            drawingPane.getChildren().addAll(drawableEdgeMap.get(e).toDraw());

        for (Vertex v : g.getVertices())
            drawingPane.getChildren().addAll(drawableVertexMap.get(v).toDraw());
    }

    @Override
    public AnchorPane getNode() {
        return drawingPane;
    }
}
