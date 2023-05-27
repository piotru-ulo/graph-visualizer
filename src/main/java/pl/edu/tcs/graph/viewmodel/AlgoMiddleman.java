package pl.edu.tcs.graph.viewmodel;

import javafx.scene.paint.Paint;

public interface AlgoMiddleman {
    boolean setVertexColor(Vertex v, Paint c);
    boolean setEdgeColor(Edge v, Paint c);
}
