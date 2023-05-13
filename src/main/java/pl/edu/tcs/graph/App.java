package pl.edu.tcs.graph;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception { // How do i sleep here? xD

            URL fxmlLocation = getClass().getResource("/graph.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            primaryStage = loader.load();

            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
