package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

public interface Visualization {
    Graph getGraph();
    void setGraph(Graph newGraph);
    void initialize();
    void updateDrawing(boolean redraw);
    AnchorPane getNode();

    boolean setVertexColor(Vertex v, Paint p);
    boolean setEdgeColor(Edge e, Paint p);
}