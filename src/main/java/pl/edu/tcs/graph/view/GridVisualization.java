package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import pl.edu.tcs.graph.model.DrawableGridVertex;
import pl.edu.tcs.graph.model.GridGraph;
import pl.edu.tcs.graph.model.GridVertex;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.Map;

public class GridVisualization implements Visualization{
    private AnchorPane drawingPane;
    private GridGraph gg;
    private Map<GridVertex, DrawableGridVertex> drawableVertexes;
    int pixelWidth;
    int pixelHeight;

    int graphWidth;
    int graphHeight;

    public double getPosX(GridVertex v) {
        return 1.0f*v.getX()*pixelWidth/graphWidth;
    }

    public double getPosY (GridVertex v) {
        return 1.0f*v.getY()*pixelHeight/graphHeight;
    }

    public GridVisualization(int graphWidth, int graphHeight, int pixelWidth, int pixelHeight) {
        drawingPane = new AnchorPane();
        gg = new GridGraph(graphWidth, graphHeight);
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    @Override
    public void updateDrawing(boolean redraw) {

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
        gg = new GridGraph(10, 10);
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
            line.setStartY(y*(1.0f*pixelHeight/graphWidth));
            line.setEndY(y*(1.0f*pixelHeight/graphWidth));
            drawingPane.getChildren().add(line);
        }
        drawableVertexes.clear();
        for(int x = 0; x<graphWidth; x++) {
            for(int y = 0; y<graphHeight; y++) {
                GridVertex actV = (GridVertex) gg.getVertex(x, y);
                drawableVertexes.put(
                        actV,
                        new DrawableGridVertex(actV, getPosX(actV), getPosY(actV)));
            }
        }
    }

    @Override
    public AnchorPane getNode() { return drawingPane; }

    @Override
    public boolean setVertexColor(Vertex v, Paint p) {
        return false;
    }

    @Override
    public boolean setEdgeColor(Edge e, Paint p) {
        return false;
    }

}
