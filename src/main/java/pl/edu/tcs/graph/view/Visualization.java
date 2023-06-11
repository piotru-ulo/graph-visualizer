package pl.edu.tcs.graph.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;

import java.util.Collection;

public interface Visualization<V extends Vertex, E extends Edge> {
    Graph<V, E> getGraph();

    void setGraph(Graph<? extends Vertex, ? extends Edge> newGraph);

    void initialize();

    void updateDrawing(boolean redraw);

    AnchorPane getNode();

    boolean setVertexColor(Vertex v, Paint p);

    boolean setEdgeColor(Edge e, Paint p);

    void setVertexActions(Collection<Algorithm.VertexAction> vertexActions);
}
