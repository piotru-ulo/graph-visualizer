package pl.edu.tcs.graph;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class Controller {
    GraphGenerator generator = new GraphGenerator();
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
    public void nextGraph(ActionEvent e){
        generator.initialize();
        generator.fakeValues(1);
        generator.updateDrawing(mainPane);

        stage.setScene(scene);
        stage.show();

    }

}
