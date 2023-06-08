package pl.edu.tcs.graph.model;

import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Graph;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridGraph implements Graph {
    private GridVertex[][] grid; // x, y
    private Edge[][][] edges;
    private int height;
    private int width;

    private void addEdges(int x, int y) {
        if (x < width - 1)
            edges[x][y][0] = new GraphEdge(grid[x][y], grid[x + 1][y], 1, (x + y * height) * 4);
        if (y > 0)
            edges[x][y][1] = new GraphEdge(grid[x][y], grid[x][y - 1], 1, (x + y * height) * 4 + 1);
        if (x > 0)
            edges[x][y][2] = new GraphEdge(grid[x][y], grid[x - 1][y], 1, (x + y * height) * 4 + 2);
        if (y < height - 1)
            edges[x][y][3] = new GraphEdge(grid[x][y], grid[x][y + 1], 1, (x + y * height) * 4 + 3);
    }

    public GridGraph(int width, int height) {
        this.height = height;
        this.width = width;
        grid = new GridVertex[width][height];
        edges = new Edge[width][height][4];
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                grid[x][y] = new GridVertex(x, y, width);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                addEdges(x, y);
            }
        }
    }

    @Override
    public Collection<Vertex> getVertices() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Vertex getVertex(int vertexId) {
        return grid[vertexId % width][vertexId / width];
    }

    public Vertex getVertex(int x, int y) {
        return grid[x][y];
    }

    @Override
    public Collection<Edge> getEdges() {
        return Stream.of(edges)
                .flatMap(Stream::of)
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }

    // warning: might return null
    @Override
    public Edge getEdge(int edgeId) {
        int x = edgeId / 4 % width;
        int y = edgeId / 4 / width;
        int direction = edgeId % 4;
        return edges[x][y][direction];
    }

    public Edge getEdge(int x, int y, int direction) {
        return edges[x][y][direction];
    }

    @Override
    public Collection<Edge> getIncidentEdges(Vertex v) {
        Collection<Edge> result = new ArrayList<>();
        int id = v.getId();
        int x = id % width;
        int y = id / width;
        for (Edge edge : edges[x][y])
            if (edge != null)
                result.add(edge);
        return result;
    }

    @Override
    public Collection<Vertex> getIncidentVertices(Vertex v) {
        Collection<Vertex> result = new ArrayList<>();
        int id = v.getId();
        int x = id % width;
        int y = id / width;
        if (x < width - 1)
            result.add(getVertex(x + 1, y));
        if (y > 0)
            result.add(getVertex(x, y - 1));
        if (x > 0)
            result.add(getVertex(x - 1, y));
        if (y < height - 1)
            result.add(getVertex(x, y + 1));
        return result;
    }

    @Override
    public Vertex insertVertex(int vertexId) {
        throw new RuntimeException("inserting a vertex to a grid");
    }

    @Override
    public Edge insertEdge(Vertex from, Vertex to, int edgeWeight, int edgeId) {
        throw new RuntimeException("inserting an edge to a grid");
    }

    @Override
    public Edge insertEdge(int from, int to, int edgeWeight, int edgeId) {
        throw new RuntimeException("inserting an edge to a grid");
    }

    @Override
    public void removeVertex(Vertex v) {
        throw new RuntimeException("removing a vertex from a grid");
    }

    @Override
    public void removeEdge(Edge e) {
        throw new RuntimeException("removing an edge from a grid");
    }

    @Override
    public boolean containsVertex(int i) {
        return (i / width < height && i >= 0);
    }

    @Override
    public Edge getCorrespondingEdge(Vertex from, Vertex to) {
        for (Edge edge : getIncidentEdges(from))
            if (edge.getEndpoints().contains(to))
                return edge;
        return null;
    }
}