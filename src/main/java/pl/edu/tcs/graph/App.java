package pl.edu.tcs.graph;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/graph.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        primaryStage = loader.load();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
