// For reference, see this: https://citeseer.ist.psu.edu/viewdoc/download?doi=10.1.1.13.8444&rep=rep1&type=pdf
package pl.edu.tcs.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FruchtermanReingoldDraw implements DrawStrategy {
    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> drawableVertices) {
        Map<Vertex, DrawableVertex> mapka = new HashMap<>();
        for (DrawableVertex one : drawableVertices)
            mapka.put(one.getVertex(), one);
        double temperature = 1250.0 /* Math.sqrt(drawableVertices.size()) */;
        double magicConstant = Math.sqrt(width * height / drawableVertices.size());

        for (int rep = 0; rep < 95; rep++) {
            Map<DrawableVertex, Double> moveX = new HashMap<>();
            Map<DrawableVertex, Double> moveY = new HashMap<>();
            for (DrawableVertex one : drawableVertices) {
                double tmpMoveX = 0, tmpMoveY = 0;
                for (DrawableVertex two : drawableVertices) {
                    if (one == two)
                        continue;
                    double deltaX = one.getX() - two.getX();
                    double deltaY = one.getY() - two.getY();
                    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                    if (distance == 0) {
                        double repulsionForce = magicConstant * magicConstant / 20;

                        if (one.getVertex().getId() < two.getVertex().getId())
                            repulsionForce *= -1;

                        tmpMoveX += repulsionForce;
                        tmpMoveY += repulsionForce;
                        continue;
                    }

                    double repulsionForce = magicConstant * magicConstant / distance;

                    tmpMoveX += deltaX / distance * repulsionForce;
                    tmpMoveY += deltaY / distance * repulsionForce;
                }
                moveX.put(one, tmpMoveX);
                moveY.put(one, tmpMoveY);
            }
            for (Edge e : graph.getEdges()) {
                DrawableVertex one = null, two = null;
                for (Vertex v : e.getEndpoints())
                    if (one == null)
                        one = mapka.get(v);
                    else
                        two = mapka.get(v);
                double deltaX = one.getX() - two.getX();
                double deltaY = one.getY() - two.getY();
                double distance = Math.max(0.5, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

                double attractionForce = distance * distance / magicConstant;

                moveX.put(one, moveX.get(one) - deltaX / distance * attractionForce);
                moveY.put(one, moveY.get(one) - deltaY / distance * attractionForce);

                moveX.put(two, moveX.get(two) + deltaX / distance * attractionForce);
                moveY.put(two, moveY.get(two) + deltaY / distance * attractionForce);
            }
            for (DrawableVertex v : drawableVertices) {
                double deltaX = moveX.get(v);
                double deltaY = moveY.get(v);
                double distance = Math.max(0.5, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

                double temperatureCap = Math.min(temperature, distance);

                double realMoveX = moveX.get(v) / distance * temperatureCap;
                double realMoveY = moveY.get(v) / distance * temperatureCap;

                v.setX(Math.min(width, Math.max(0.0, v.getX() + realMoveX)));
                v.setY(Math.min(height, Math.max(0.0, v.getY() + realMoveY)));
            }
            temperature = Math.max(2.5, temperature * 0.95);
        }
    }
}