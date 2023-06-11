package pl.edu.tcs.graph.view;

import javafx.scene.control.ContextMenu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

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
}
