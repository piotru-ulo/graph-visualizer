package pl.edu.tcs.graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    GraphVisualization visualization = new GraphVisualization();
    Menu menu = new Menu(visualization);

    private void updateDrawing(Stage primaryStage) {
        HBox mainHorizontal = new HBox();
        mainHorizontal.getChildren().add(menu.getNode());
        visualization.updateDrawing();
        mainHorizontal.getChildren().add(visualization.getNode());
        mainHorizontal.setSpacing(50);
        Scene scene = new Scene(mainHorizontal, 1600, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // How do i sleep here? xD
        Button button = new Button("Begin");
        button.setOnAction(event -> {
            visualization.initialize();
            visualization.fakeValues(0);
            //updateDrawing(primaryStage);
            updateDrawing(primaryStage);
        });
        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
