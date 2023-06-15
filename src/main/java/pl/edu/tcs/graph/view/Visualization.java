package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;

import java.util.Collection;

public interface Visualization {
    Graph getGraph();

    void setGraph(Graph newGraph);

    void initialize();

    void updateDrawing(boolean redraw);

    AnchorPane getNode();

    boolean setVertexColor(Vertex v, Paint p);

    boolean setEdgeColor(Edge e, Paint p);

    void setVertexActions(Collection<Algorithm.VertexAction> vertexActions);

    void setWidth(int width);
    void setHeight(int height);
}
