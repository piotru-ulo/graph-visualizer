package pl.edu.tcs.graph;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class Menu implements SubWindow {
    GraphVisualization visualization;
    Label textUp = new Label("graph visualizer");
    Button randomGraphButton = new Button("random graph");
    Label label = new Label("input adjacency list representation of your graph here:");
    TextArea adjListInput = new TextArea();
    Button adjListButton = new Button("accept");
    VBox menu = new VBox(textUp, randomGraphButton, label, adjListInput, adjListButton);

    public Menu(GraphVisualization visualization){
        //TODO: is this the right way of communication with the graph?
        this.visualization = visualization;
        randomGraphButton.setOnAction(event -> {
            //System.out.println("clicked!");
            visualization.initialize();
            visualization.fakeValues(1);
            visualization.updateDrawing();
        });
        adjListButton.setOnAction(actionEvent -> {
            System.out.println("input:" + adjListInput.getText());
            int[] input = Arrays.stream(adjListInput.getText().split("[\\s\n]+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if(input.length%2 != 0) {
                //TODO: wrong input handling
                return;
            }

            visualization.fromAdjacencyList(input);
            visualization.updateDrawing();
        });
        menu.setSpacing(20);
        adjListInput.setPrefSize(300, 300);
    }

    @Override
    public Node getNode() {
        return menu;
    }
}
