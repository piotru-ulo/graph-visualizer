package pl.edu.tcs.graph.viewmodel;

import java.util.Optional;

import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Vertex;

import java.util.zip.CheckedOutputStream;

public interface AlgoMiddleman {
    public boolean setVertexColor(Vertex v, int[] rgb, int waitTime);

    public boolean setEdgeColor(Edge v, int[] rbg, int waitTime);

    public Optional<Double> getX(Vertex v);

    public Optional<Double> getY(Vertex v);
}
