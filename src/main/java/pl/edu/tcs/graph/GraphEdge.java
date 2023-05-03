package pl.edu.tcs.graph;

import java.util.ArrayList;
import java.util.List;

class GraphEdge implements Edge {
    int id;
    int edgeWeight;
    Vertex from, to;

    public GraphEdge(Vertex from, Vertex to, int edgeWeight, int id) {
        this.from = from;
        this.to = to;
        this.edgeWeight = edgeWeight;
        this.id = id;
    }

    @Override
    public int getWeight() {
        return edgeWeight;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Vertex> getEndpoints() {
        List<Vertex> returnList = new ArrayList<>();
        returnList.add(from);
        returnList.add(to);
        return returnList;
    }

}