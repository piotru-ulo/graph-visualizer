package pl.edu.tcs.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GraphEdge implements Edge {
    int id;
    int weight;
    Vertex from, to;

    public GraphEdge(Vertex from, Vertex to, int edgeWeight, int id) {
        this.from = from;
        this.to = to;
        this.weight = edgeWeight;
        this.id = id;
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
        List<Vertex> returnList = new ArrayList<>();
        returnList.add(from);
        returnList.add(to);
        return returnList;
    }

}