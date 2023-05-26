package pl.edu.tcs.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
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
    private TextField initialVertexField;
    @FXML
    private TextField finalVertexField;

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
        visualization.updateDrawing(true);

        stage.setScene(scene);
        stage.show();
    }

    public void graphFromInput(ActionEvent e) {
        mainPane.lookup("graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        visualization.initialize();
        try {
            int[] input = Arrays.stream(adjListInput.getText().split("[\\s\n]+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if(input.length%2 != 0)
                throw new Exception();
            visualization.fromAdjacencyList(input);
            visualization.updateDrawing(true);
        }
        catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong input!");
            alert.showAndWait();
        }
    }

    public void runAlgorithm(ActionEvent e) { // TODO: implement!
        if (choiceBox.getValue() != null) {

        }
    }



}
