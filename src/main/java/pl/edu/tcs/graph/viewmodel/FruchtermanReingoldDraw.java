// For reference, see this: https://citeseer.ist.psu.edu/viewdoc/download?doi=10.1.1.13.8444&rep=rep1&type=pdf
package pl.edu.tcs.graph.viewmodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FruchtermanReingoldDraw implements DrawStrategy {
    private boolean areCollinear(DrawableVertex a, DrawableVertex b, DrawableVertex c) {
        return a.getX() * (b.getY() - c.getY()) +
                b.getX() * (c.getY() - a.getY()) +
                c.getX() * (a.getY() - b.getY()) == 0;
    }

    Random r = new Random();

    private void randDisp(DrawableVertex v, double width, double height) {
        v.setX(Math.min(width, Math.max(0.0, v.getX() - 37 + r.nextInt(75))));
        v.setY(Math.min(height, Math.max(0.0, v.getY() - 37 + r.nextInt(75))));
    }

    public void draw(double width, double height, Graph graph, Collection<DrawableVertex> drawableVertices) {
        Map<Vertex, DrawableVertex> mapka = new HashMap<>();
        for (DrawableVertex one : drawableVertices)
            mapka.put(one.getVertex(), one);
        double temperature = 1550.0 /* Math.sqrt(drawableVertices.size()) */;
        double magicConstant = Math.sqrt(width * height / drawableVertices.size());

        for (int rep = 0; rep < 195; rep++) {
            Map<DrawableVertex, Double> moveX = new HashMap<>();
            Map<DrawableVertex, Double> moveY = new HashMap<>();
            for (DrawableVertex v : drawableVertices) {
                moveX.put(v, 0.0);
                moveY.put(v, 0.0);
            }
            for (DrawableVertex one : drawableVertices) {
                for (DrawableVertex two : drawableVertices) {
                    for (DrawableVertex three : drawableVertices) {
                        if (one != two && two != three && one != three && areCollinear(one, two, three)) {
                            randDisp(one, width, height);
                            randDisp(two, width, height);
                            randDisp(three, width, height);
                        }
                    }
                }
            }
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
                moveX.put(one, moveX.get(one) + tmpMoveX);
                moveY.put(one, moveY.get(one) + tmpMoveY);
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

                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance == 0) {
                    double attractionForce = 20 * 20 / magicConstant;

                    moveX.put(one, moveX.get(one) + attractionForce);
                    moveY.put(one, moveY.get(one) + attractionForce);

                    moveX.put(two, moveX.get(two) - attractionForce);
                    moveY.put(two, moveY.get(two) - attractionForce);
                    continue;
                }

                double attractionForce = distance * distance / magicConstant;

                moveX.put(one, moveX.get(one) - deltaX / distance * attractionForce);
                moveY.put(one, moveY.get(one) - deltaY / distance * attractionForce);

                moveX.put(two, moveX.get(two) + deltaX / distance * attractionForce);
                moveY.put(two, moveY.get(two) + deltaY / distance * attractionForce);
            }
            for (DrawableVertex v : drawableVertices) {
                double deltaX = moveX.get(v);
                double deltaY = moveY.get(v);
                double distance = Math.max(10, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

                double temperatureCap = Math.min(temperature, distance);

                double realMoveX = moveX.get(v) / distance * temperatureCap;
                double realMoveY = moveY.get(v) / distance * temperatureCap;

                v.setX(Math.min(width, Math.max(0.0, v.getX() + realMoveX)));
                v.setY(Math.min(height, Math.max(0.0, v.getY() + realMoveY)));
            }
            temperature = Math.max(2.5, temperature * 0.98);
        }
    }
}