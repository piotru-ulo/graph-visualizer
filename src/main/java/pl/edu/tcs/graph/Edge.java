package pl.edu.tcs.graph;

import java.util.List;

public interface Edge {
    public int getId();

    public int getWeight();

    public List<Vertex> getEndpoints();
}
