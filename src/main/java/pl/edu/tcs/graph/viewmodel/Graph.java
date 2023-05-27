package pl.edu.tcs.graph.viewmodel;

import java.util.Collection;

public interface Graph {
    public Collection<Vertex> getVertices();

    public Vertex getVertex(int vertexId);

    public Collection<Edge> getEdges();

    public Edge getEdge(int edgeId);

    public Collection<Edge> getIncidentEdges(Vertex v);

    public Collection<Vertex> getIncidentVertices(Vertex v);

    public Vertex insertVertex(int vertexId);

    public Edge insertEdge(Vertex from, Vertex to, int edgeWeight, int edgeId);

    public Edge insertEdge(int from, int to, int edgeWeight, int edgeId);

    public void removeVertex(Vertex v);

    public void removeEdge(Edge e);

    boolean containsVertex(int i);

    Edge getCorrespondingEdge(Vertex from, Vertex to);
}
