package pl.edu.tcs.graph.algo;

import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Graph;
import pl.edu.tcs.graph.model.Vertex;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Astar implements Algorithm {
    private final Collection<AlgorithmProperties> properties = Arrays.asList(
            AlgorithmProperties.SOURCE,
            AlgorithmProperties.TARGET);

    @Override
    public Collection<AlgorithmProperties> getProperties() {
        return properties;
    }

    @Override
    public Collection<VertexAction> getVertexActions() {
        return Arrays.asList(
                new VertexAction("set start", (v -> {
                    sourceVertex = v;
                    return null;
                })),
                new VertexAction("set end", (v -> {
                    targetVertex = v;
                    return null;
                })));
    }

    private Vertex targetVertex = null;
    private Vertex sourceVertex = null;

    Double getScore(Vertex from, Vertex to, AlgoMiddleman algoMiddleman) {
        return Math.pow(algoMiddleman.getX(from).get().doubleValue() - algoMiddleman.getX(to).get().doubleValue(), 2)
                + Math.pow(algoMiddleman.getY(from).get().doubleValue() - algoMiddleman.getY(to).get().doubleValue(),
                        2);
    }

    private class Location implements Comparable<Location> {
        private Vertex vertex;
        private Double score;

        Location(Vertex vertex, Double score) {
            this.vertex = vertex;
            this.score = score;
        }

        @Override
        public int compareTo(Location o) {
            return score.compareTo(o.score);
        }
    }

    private void astar(Graph g, AlgoMiddleman algoMiddleman)
            throws AlgorithmException {
        PriorityQueue<Location> queue = new PriorityQueue<>();
        Map<Vertex, Double> minDist = new HashMap<>();
        queue.add(new Location(sourceVertex, 0.0));
        while (!queue.isEmpty()) {
            Location location = queue.poll();
            algoMiddleman.setVertexColor(location.vertex, 127, 255, 212);
            if (location.vertex.equals(targetVertex)) {
                algoMiddleman.setVertexColor(location.vertex, 212, 175, 55);
                return;
            }
            for (Vertex to : g.getIncidentVertices(location.vertex)) {
                if (!to.isActive())
                    continue;
                Double score = location.score + 1;
                if (!minDist.containsKey(to) || minDist.get(to).compareTo(score) > 0) {
                    minDist.put(to, score);
                    queue.add(new Location(to, getScore(to, targetVertex, algoMiddleman)));
                    algoMiddleman.setVertexColor(to, 250, 240, 230);
                }
            }
            algoMiddleman.setVertexColor(location.vertex, 138, 43, 226);
        }
    }

    @Override
    public void run(Graph g, AlgoMiddleman aM, Map<AlgorithmProperties, Integer> requirements)
            throws AlgorithmException {
        for (Vertex v : g.getVertices())
            if (v.isActive())
                aM.instantSetVertexColor(v, 255, 255, 255);
        try {
            if (requirements.get(AlgorithmProperties.SOURCE) != null)
                sourceVertex = g.getVertex(requirements.get(AlgorithmProperties.SOURCE));
            if (requirements.get(AlgorithmProperties.TARGET) != null)
                targetVertex = g.getVertex(requirements.get(AlgorithmProperties.TARGET));
            if (sourceVertex == null)
                sourceVertex = g.getVertex(1);
            if (targetVertex == null) {
                int mx = 1;
                for (Vertex v : g.getVertices())
                    mx = Math.max(mx, v.getId());
                targetVertex = g.getVertex(mx);
            }
            astar(g, aM);
            sourceVertex = targetVertex = null;
        } catch (AlgorithmException e) {
            throw e;
        }
    }
}
