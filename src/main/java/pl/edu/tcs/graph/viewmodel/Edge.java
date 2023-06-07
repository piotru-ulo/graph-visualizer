package pl.edu.tcs.graph.viewmodel;

import java.util.Collection;

public interface Edge {
    public int getId();

    public int getWeight();

    public Collection<Vertex> getEndpoints();

    public boolean isDirected();
}
