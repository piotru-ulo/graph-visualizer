package pl.edu.tcs.graph.model;

import java.util.Collection;

public interface Graph<V extends Vertex, E extends Edge> {
    Collection<V> getVertices();

    V getVertex(int vertexId);

    Collection<E> getEdges();

    E getEdge(int edgeId);

    Collection<E> getIncidentEdges(Vertex v);

    Collection<V> getIncidentVertices(Vertex v);

    V insertVertex(int vertexId);

    E insertEdge(Vertex from, Vertex to, int edgeWeight, int edgeId);

    E insertEdge(int from, int to, int edgeWeight, int edgeId);

    void removeVertex(Vertex v);

    void removeEdge(Edge e);

    boolean containsVertex(int i);

    E getCorrespondingEdge(Vertex from, Vertex to);
}
