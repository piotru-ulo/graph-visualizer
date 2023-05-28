package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import pl.edu.tcs.graph.model.GridGraph;

public class GridVisualization {
    private AnchorPane drawingPane;
    private GridGraph gg;
    int pixelWidth;
    int pixelHeight;

    int graphWidth;
    int graphHeight;


    public GridVisualization(int graphWidth, int graphHeight, int pixelWidth, int pixelHeight) {
        drawingPane = new AnchorPane();
        gg = new GridGraph(graphWidth, graphHeight);
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    public void updateDrawing() {
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
    }

    public AnchorPane getNode() { return drawingPane; }

}
