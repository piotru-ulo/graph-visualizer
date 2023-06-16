package pl.edu.tcs.graph.model;

import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DigraphImpl implements Graph {
    private Map<Integer, Vertex> vertices;
    private Map<Integer, Edge> edges;

    public DigraphImpl() {
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
                .filter(e -> e.getEndpoints().toArray()[0].equals(vertex))
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
        Edge insertEdge = new GraphEdge(from, to, weight, true, id);
        edges.put(id, insertEdge);
        return insertEdge;
    }

    @Override
    public void removeEdge(Edge edge) {
        edges.remove(edge.getId());
    }

    @Override
    public boolean containsVertex(int i) {
        return vertices.containsKey(i);
    }

    @Override
    public void removeVertex(Vertex vertex) {
        getIncidentEdges(vertex).forEach(e -> {
            removeEdge(e);
        });
        vertices.remove(vertex.getId());
    }

    @Override
    public Edge insertEdge(int from, int to, int edgeWeight, int edgeId) {
        Vertex one = vertices.get(from), two = vertices.get(to);
        return insertEdge(one, two, edgeWeight, edgeId);
    }

    @Override
    public Collection<Vertex> getIncidentVertices(Vertex v) {
        return getIncidentEdges(v).stream().flatMap(e -> e.getEndpoints().stream()).filter(maybe -> maybe != v)
                .collect(Collectors.toList());
    }

    @Override
    public Edge getCorrespondingEdge(Vertex from, Vertex to) {
        return getIncidentEdges(from)
                .stream()
                .filter(e -> e.getEndpoints().stream().anyMatch(v -> v == to))
                .findFirst()
                .orElse(null);
    }

    public static Graph fromAdjacencyList(int[] input) {
        Graph g = new DigraphImpl();
        for (int i = 0; i < input.length; i += 3) {
            if (!g.containsVertex(input[i]))
                g.insertVertex(input[i]);
            if (!g.containsVertex(input[i + 1]))
                g.insertVertex(input[i + 1]);
            g.insertEdge(input[i], input[i + 1], input[i + 2], i / 3);
        }
        return g;
    }

    public static Graph randomGraph(int i) {
        Graph g = new DigraphImpl();
        for (i = 0; i < 15; i++)
            g.insertVertex(i);
        Random r = new Random();
        Set<Pair<Integer, Integer>> mapka = new HashSet<>();
        for (i = 0; i < 25; i++) {
            int a = r.nextInt(15), b = r.nextInt(15);
            if (mapka.contains(new Pair<Integer, Integer>(a, b)) ||
                    mapka.contains(new Pair<Integer, Integer>(b, a)))
                continue;
            mapka.add(new Pair<Integer, Integer>(a, b));
            g.insertEdge(a, b, 1 + r.nextInt(9), i);
        }
        return g;
    }

}
