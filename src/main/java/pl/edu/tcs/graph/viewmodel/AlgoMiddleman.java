package pl.edu.tcs.graph.viewmodel;

import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Vertex;

public interface AlgoMiddleman {
    public boolean setVertexColor(Vertex v, int r, int g, int b);

    public boolean setEdgeColor(Edge v, int r, int g, int b);
}
