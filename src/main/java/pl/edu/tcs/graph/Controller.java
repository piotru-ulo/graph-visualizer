package pl.edu.tcs.graph;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.util.Arrays;

public class Controller {
    //GraphGenerator generator = new GraphGenerator();
    GraphVisualization visualization = new GraphVisualization();
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private SplitPane mainPane;
    @FXML
    private AnchorPane graphPane;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private TextArea adjListInput;


    public void nextGraph(ActionEvent e){
        mainPane.lookup("#graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        visualization.initialize();
        visualization.fakeValues(1);
        visualization.updateDrawing();

//        generator.initialize();
//        generator.fakeValues(1);
//        generator.updateDrawing(mainPane);

        stage.setScene(scene);
        stage.show();
    }

    public void graphFromInput(ActionEvent e) {
         mainPane.lookup("graphPane");
         graphPane.getChildren().clear();
         graphPane.getChildren().add(visualization.getNode());
         visualization.initialize();
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
    }
}
