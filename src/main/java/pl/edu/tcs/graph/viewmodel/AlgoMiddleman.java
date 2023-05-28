package pl.edu.tcs.graph.viewmodel;

import javafx.scene.paint.Paint;

public interface AlgoMiddleman {
    public boolean setVertexColor(Vertex v, Paint c);

    public boolean setEdgeColor(Edge v, Paint c);
}
