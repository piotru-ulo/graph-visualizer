package pl.edu.tcs.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import pl.edu.tcs.graph.view.GraphVisualization;

import java.util.Arrays;

public class Controller {
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
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button runButton;
    ObservableList<String> choiceList = FXCollections.observableArrayList("DFS", "BFS");

    @FXML
    private void initialize() {
        choiceBox.setItems(choiceList);
    }

    public void nextGraph(ActionEvent e) {
        mainPane.lookup("#graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        visualization.initialize();
        visualization.fakeValues(1);
        visualization.updateDrawing();

        stage.setScene(scene);
        stage.show();
    }

    public void graphFromInput(ActionEvent e) { // TODO: do it better?
        mainPane.lookup("graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        visualization.initialize();
        int[] input = Arrays.stream(adjListInput.getText().split("[\\s\n]+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        if (input.length % 2 != 0) {
            // TODO: wrong input handling
            return;
        }
        visualization.fromAdjacencyList(input);
        visualization.updateDrawing();
    }

    public void runAlgorithm(ActionEvent e) { // TODO: implement!
        if (choiceBox.getValue() != null) {

        }
    }

}
