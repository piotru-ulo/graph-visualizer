package pl.edu.tcs.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphImpl implements Graph {
    private Map<Integer, Vertex> vertices;
    private Map<Integer, Edge> edges;

    public GraphImpl() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }

    @Override
    public Collection<Vertex> getVertices() {
        return vertices.values().stream().collect(Collectors.toList());
    }

    @Override
    public Vertex getVertex(int vertexId) throws InvalidVertexException {
        if (!vertices.containsKey(vertexId))
            throw new InvalidVertexException("Graph doesn't contain this vertex.");
        return vertices.get(vertexId);
    }

    @Override
    public Collection<Edge> getEdges() {
        return edges.values().stream().collect(Collectors.toList());
    }

    @Override
    public Edge getEdge(int edgeId) throws InvalidEdgeException {
        if (!edges.containsKey(edgeId))
            throw new InvalidEdgeException("Graph doesn't contain this edge.");
        return edges.get(edgeId);
    }

    private boolean graphContainsVertex(int vertexId) {
        return vertices.containsKey(vertexId);
    }

    private boolean graphContainsEdge(int edgeId) {
        return edges.containsKey(edgeId);
    }

    private boolean graphContainsVertex(Vertex vertex) {
        return graphContainsVertex(vertex.getId());
    }

    private boolean graphContainsEdge(Edge edge) {
        return graphContainsEdge(edge.getId());
    }

    @Override
    public Collection<Edge> getIncidentEdges(Vertex vertex) throws InvalidVertexException {
        if (!graphContainsVertex(vertex))
            throw new InvalidVertexException("Graph doesn't contain this vertex.");
        return edges.values().stream()
                .filter(e -> e.getEndpoints().contains(vertex))
                .collect(Collectors.toList());
    }

    @Override
    public Vertex insertVertex(int vertexId) throws InvalidVertexException {
        if (graphContainsVertex(vertexId))
            throw new InvalidVertexException("Graph does contain this vertex already.");
        GraphVertex putVertex = new GraphVertex(vertexId);
        vertices.put(vertexId, putVertex);
        return putVertex;
    }

    @Override
    public Edge insertEdge(Vertex from, Vertex to, int edgeWeight, int id)
            throws InvalidVertexException, InvalidEdgeException {
        if (!graphContainsVertex(from) || !graphContainsVertex(to))
            throw new InvalidVertexException("Graph doesn't contain this vertex.");
        if (graphContainsEdge(id))
            throw new InvalidEdgeException("Graph does contain this edge already.");
        Edge insertEdge = new GraphEdge(from, to, edgeWeight, id);
        edges.put(id, insertEdge);
        return insertEdge;
    }

    @Override
    public void removeEdge(Edge edge) throws InvalidEdgeException {
        if (!graphContainsEdge(edge))
            throw new InvalidEdgeException("Graph doesn't contain this edge.");
        edges.remove(edge.getId());
    }

    @Override
    public void removeVertex(Vertex vertex) throws InvalidVertexException {
        if (!graphContainsVertex(vertex))
            throw new InvalidVertexException("Graph doesn't contain this vertex.");
        getIncidentEdges(vertex).forEach(e -> {
            removeEdge(e);
        });
        vertices.remove(vertex.getId());
    }
}
