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
    public Vertex getVertex(int vertexId) {
        return vertices.get(vertexId);
    }

    @Override
    public Collection<Edge> getEdges() {
        return edges.values().stream().collect(Collectors.toList());
    }

    @Override
    public Edge getEdge(int edgeId) {
        return edges.get(edgeId);
    }

    @Override
    public Collection<Edge> getIncidentEdges(Vertex vertex) {
        return edges.values().stream()
                .filter(e -> e.getEndpoints().contains(vertex))
                .collect(Collectors.toList());
    }

    @Override
    public Vertex insertVertex(int vertexId) {
        GraphVertex putVertex = new GraphVertex(vertexId);
        vertices.put(vertexId, putVertex);
        return putVertex;
    }

    @Override
    public Edge insertEdge(Vertex from, Vertex to, int weight, int id) {
        Edge insertEdge = new GraphEdge(from, to, weight, id);
        edges.put(id, insertEdge);
        return insertEdge;
    }

    @Override
    public void removeEdge(Edge edge) {
        edges.remove(edge.getId());
    }

    @Override
    public void removeVertex(Vertex vertex) {
        getIncidentEdges(vertex).forEach(e -> {
            removeEdge(e);
        });
        vertices.remove(vertex.getId());
    }
}
