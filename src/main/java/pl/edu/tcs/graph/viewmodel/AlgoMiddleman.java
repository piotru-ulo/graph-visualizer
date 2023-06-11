package pl.edu.tcs.graph.viewmodel;

import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Vertex;

public interface AlgoMiddleman {
    boolean setVertexColor(Vertex v, int r, int g, int b);

    boolean setEdgeColor(Edge v, int r, int g, int b);

    double getX(Vertex v);

    double getY(Vertex v);
}
