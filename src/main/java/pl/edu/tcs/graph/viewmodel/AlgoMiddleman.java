package pl.edu.tcs.graph.viewmodel;

import javafx.scene.paint.Color;
import pl.edu.tcs.graph.model.Edge;
import pl.edu.tcs.graph.model.Vertex;

import java.util.zip.CheckedOutputStream;

public interface AlgoMiddleman {
    boolean setVertexColor(Vertex v, int r, int g, int b);

    boolean setEdgeColor(Edge v, int r, int g, int b);

    default boolean setVertexColor(Vertex v, String colorName) {
        Color c = Color.valueOf(colorName);
        System.out.println(colorName+" "+ (int) c.getRed()*255 +" "+ (int) c.getGreen()*255+ " "+ (int) c.getBlue()*255);
        return setVertexColor(v, (int) c.getRed()*255, (int) c.getGreen()*255, (int) c.getBlue()*255);
    }

    double getX(Vertex v);

    double getY(Vertex v);
}
