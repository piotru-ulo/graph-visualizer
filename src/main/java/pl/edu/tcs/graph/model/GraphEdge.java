package pl.edu.tcs.graph.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GraphEdge implements Edge {
    private int id;
    private int weight;
    private Vertex from, to;
    private boolean directed;

    public GraphEdge(Vertex from, Vertex to, int edgeWeight, int id) {
        this.from = from;
        this.to = to;
        this.weight = edgeWeight;
        this.id = id;
        this.directed = false;
    }

    public GraphEdge(Vertex from, Vertex to, int edgeWeight, boolean directed, int id) {
        this.from = from;
        this.to = to;
        this.weight = edgeWeight;
        this.id = id;
        this.directed = directed;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Collection<Vertex> getEndpoints() {
        if (from == null || to == null)
            throw new RuntimeException();
        List<Vertex> returnList = new ArrayList<>();
        returnList.add(from);
        returnList.add(to);
        return returnList;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
}