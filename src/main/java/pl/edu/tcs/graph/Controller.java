package pl.edu.tcs.graph;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.event.ActionEvent;
import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.algo.BFS;
import pl.edu.tcs.graph.algo.Bipartition;
import pl.edu.tcs.graph.algo.DFS;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.view.GraphVisualization;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.EdgeMiddleman;
import pl.edu.tcs.graph.viewmodel.Vertex;
import pl.edu.tcs.graph.viewmodel.VertexMiddleman;

import java.util.Arrays;
import java.util.Collection;

public class Controller {
    private GraphVisualization visualization = new GraphVisualization();
    private VertexMiddleman vM = new VertexMiddleman() {
        @Override
        public boolean setColor(Vertex v, Paint c) {
            boolean result = visualization.setVertexColor(v, c);
            try {
                Thread.sleep(paint_delay);
            } catch (InterruptedException e) {
            }
            Platform.runLater((() -> {
                visualization.updateDrawing(false);
                stage.setScene(scene);
                stage.show();
            }));
            return result;
        }
    };
    private EdgeMiddleman eM = new EdgeMiddleman() {
        @Override
        public boolean setColor(Edge e, Paint c) {
            boolean result = visualization.setEdgeColor(e, c);
            try {
                Thread.sleep(paint_delay);
            } catch (InterruptedException ee) {
            }
            Platform.runLater((() -> {
                visualization.updateDrawing(false);
                stage.setScene(scene);
                stage.show();
            }));
            return result;
        }
    };

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
    ObservableList<String> choiceList = FXCollections.observableArrayList("DFS", "BFS", "BIPARTITION");

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
            if (input.length % 2 != 0)
                throw new Exception();
            visualization.fromAdjacencyList(input);
            visualization.updateDrawing(true);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong input!");
            alert.showAndWait();
        }
    }

    private int paint_delay = 700;
    private boolean isSomeoneRunning = false;

    private void runAlgo(Algorithm a, Collection<Pair<String, Integer>> requirements) {
        new Thread(() -> {
            try {
                a.run(visualization.getGraph(), vM, eM, requirements);
                isSomeoneRunning = false;
            } catch (AlgorithmException e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(e.getLocalizedMessage());
                    alert.showAndWait();
                    isSomeoneRunning = false;
                });
            }
        }).start();
    }

    public void runAlgorithm(ActionEvent ev) {
        if (choiceBox.getValue() == null || isSomeoneRunning)
            return;
        isSomeoneRunning = true;
        for (Vertex v : visualization.getGraph().getVertices())
            visualization.setVertexColor(v, javafx.scene.paint.Color.WHITE);
        for (Edge e : visualization.getGraph().getEdges())
            visualization.setEdgeColor(e, javafx.scene.paint.Color.BLACK);
        if (choiceBox.getValue() == "DFS")
            runAlgo(new DFS(), null);
        else if (choiceBox.getValue() == "BIPARTITION")
            runAlgo(new Bipartition(), null);
        else if (choiceBox.getValue() == "BFS")
            runAlgo(new BFS(), null);
    }
}
