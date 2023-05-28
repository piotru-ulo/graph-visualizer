package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import pl.edu.tcs.graph.model.DrawableGridVertex;
import pl.edu.tcs.graph.model.GridGraph;
import pl.edu.tcs.graph.model.GridVertex;
import pl.edu.tcs.graph.viewmodel.DrawableVertex;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.HashMap;
import java.util.Map;

public class GridVisualization implements Visualization{
    private AnchorPane drawingPane;
    private GridGraph gg;
    private Map<Vertex, DrawableGridVertex> drawableVertexes;
    double pixelWidth;
    double pixelHeight;

    double vertexSize = 20;

    int graphWidth;
    int graphHeight;

    public double getPosX(GridVertex v) {
        return 1.0f*v.getX()*pixelWidth/graphWidth;
    }

    public double getPosY (GridVertex v) {
        return 1.0f*v.getY()*pixelHeight/graphHeight;
    }

    public GridVisualization(int graphWidth, int graphHeight, double pixelWidth, double pixelHeight) {
        vertexSize = Math.min(pixelWidth/graphWidth, pixelHeight/graphHeight);
        drawingPane = new AnchorPane();
        gg = new GridGraph(graphWidth, graphHeight);
        drawableVertexes = new HashMap<>();
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    @Override
    public void updateDrawing(boolean redraw) {
        if(redraw)
            initialize();

        //TODO!
    }

    @Override
    public Graph getGraph() {
        return gg;
    }

    @Override
    public void setGraph(Graph newGraph) {
        if(newGraph instanceof GridGraph) {
            gg = (GridGraph) newGraph;
        }
        else {
            throw new RuntimeException("Grid graph set to non-grid graph");
        }
    }

    @Override
    public void initialize() {
        drawingPane.getChildren().clear();
        for(int x = 0; x<=graphWidth; x++) {
            Line line = new Line();
            line.setStartX(x*(1.0f*pixelWidth/graphWidth));
            line.setEndX(x*(1.0f*pixelWidth/graphWidth));
            line.setStartY(0);
            line.setEndY(pixelHeight);
            drawingPane.getChildren().add(line);
        }
        for(int y = 0; y<=graphHeight; y++) {
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(pixelWidth);
            line.setStartY(y*(1.0f*pixelHeight/graphHeight));
            line.setEndY(y*(1.0f*pixelHeight/graphHeight));
            drawingPane.getChildren().add(line);
        }
        drawableVertexes = new HashMap<>();
        for(int x = 0; x<graphWidth; x++) {
            for(int y = 0; y<graphHeight; y++) {
                GridVertex actV = (GridVertex) gg.getVertex(x, y);
                DrawableGridVertex actDrawV = new DrawableGridVertex(actV, getPosX(actV), getPosY(actV), vertexSize);
                drawableVertexes.put(actV, actDrawV);
                drawingPane.getChildren().addAll(actDrawV.toDraw());
            }
        }
    }

    @Override
    public AnchorPane getNode() { return drawingPane; }

    @Override
    public boolean setVertexColor(Vertex v, Paint p) {
        if(!drawableVertexes.containsKey(v))
            return false;
        System.out.println("changed color of "+v.getId()+ " to " + p);
        drawableVertexes.get(v).setFill(p);
        return true;
    }

    @Override
    public boolean setEdgeColor(Edge e, Paint p) {
        return false;
    }

}
