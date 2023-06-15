package pl.edu.tcs.graph.viewmodel;

import java.util.Optional;

import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Vertex;

public interface AlgoMiddleman {
    public boolean setVertexColor(Vertex v, int r, int g, int b);

    public boolean setEdgeColor(Edge v, int r, int g, int b);

    public boolean instantSetVertexColor(Vertex v, int r, int g, int b);

    public boolean instantSetEdgeColor(Edge v, int r, int g, int b);

    public Optional<Double> getX(Vertex v);

    public Optional<Double> getY(Vertex v);
}
