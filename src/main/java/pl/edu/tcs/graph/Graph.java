package pl.edu.tcs.graph;

import java.util.Collection;

public interface Graph {
    public Collection<Vertex> getVertices();

    public Vertex getVertex(int vertexId) throws InvalidVertexException;

    public Collection<Edge> getEdges();

    public Edge getEdge(int edgeId) throws InvalidEdgeException;

    public Collection<Edge> getIncidentEdges(Vertex v) throws InvalidVertexException;

    public Vertex insertVertex(int vertexId) throws InvalidVertexException;

    public Edge insertEdge(Vertex from, Vertex to, int edgeWeight, int edgeId)
            throws InvalidVertexException, InvalidEdgeException;

    public void removeVertex(Vertex v) throws InvalidVertexException;

    public void removeEdge(Edge e) throws InvalidEdgeException;
}
