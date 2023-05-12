package pl.edu.tcs.graph;

import java.util.Collection;

public interface Edge {
    public int getId();

    public int getWeight();

    public Collection<Vertex> getEndpoints();
}
