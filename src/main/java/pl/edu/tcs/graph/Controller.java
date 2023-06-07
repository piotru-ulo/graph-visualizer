package pl.edu.tcs.graph;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.algo.Articulation;
import pl.edu.tcs.graph.algo.BFS;
import pl.edu.tcs.graph.algo.Bipartition;
import pl.edu.tcs.graph.algo.Bridges;
import pl.edu.tcs.graph.algo.DFS;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.view.GraphVisualization;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private GraphVisualization visualization = new GraphVisualization();
    private AlgoMiddleman aM = new AlgoMiddleman() {
        @Override
        public boolean setVertexColor(Vertex v, int r, int g, int b) {
            boolean result = visualization.setVertexColor(v, javafx.scene.paint.Color.rgb(r, g, b));
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

        @Override
        public boolean setEdgeColor(Edge e, int r, int g, int b) {
            boolean result = visualization.setEdgeColor(e, javafx.scene.paint.Color.rgb(r, g, b));
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
    private ChoiceBox<GraphAlgorithms> choiceBox;
    @FXML
    private Button runButton;

    private enum GraphAlgorithms {
        DFS,
        BFS,
        BIPARTITION,
        BRIDGES,
        ARTICULATION_POINTS
    }

    ObservableList<GraphAlgorithms> choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
            GraphAlgorithms.BFS, GraphAlgorithms.BIPARTITION, GraphAlgorithms.BRIDGES,
            GraphAlgorithms.ARTICULATION_POINTS);

    @FXML
    private Button setPropertiesButton;
    private static Map<AlgorithmProperties, Integer> requirements;

    public void setRequirements(Map<AlgorithmProperties, Integer> req) {
        requirements = new HashMap<>(req);
        System.out.println(requirements);
    }

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

    private void runAlgo(Algorithm a, Map<AlgorithmProperties, Integer> req) {
        new Thread(() -> {
            try {
                a.run(visualization.getGraph(), aM, req);
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
        if (requirements == null)
            requirements = new HashMap<>();
        isSomeoneRunning = true;
        System.out.println(requirements);
        for (Vertex v : visualization.getGraph().getVertices())
            visualization.setVertexColor(v, javafx.scene.paint.Color.WHITE);
        for (Edge e : visualization.getGraph().getEdges())
            visualization.setEdgeColor(e, javafx.scene.paint.Color.BLACK);
        if (choiceBox.getValue() == GraphAlgorithms.DFS)
            runAlgo(new DFS(), requirements);
        else if (choiceBox.getValue() == GraphAlgorithms.BIPARTITION)
            runAlgo(new Bipartition(), requirements);
        else if (choiceBox.getValue() == GraphAlgorithms.BFS)
            runAlgo(new BFS(), requirements);
        else if (choiceBox.getValue() == GraphAlgorithms.BRIDGES)
            runAlgo(new Bridges(), requirements);
        else if (choiceBox.getValue() == GraphAlgorithms.ARTICULATION_POINTS)
            runAlgo(new Articulation(), requirements);
    }

    public void openProperties(ActionEvent e) {
        try {
            URL fxmlLocation = getClass().getResource("/properties.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Stage root = loader.load();
            PropertiesController controller = loader.<PropertiesController>getController();
            if (choiceBox.getValue() == GraphAlgorithms.DFS)
                controller.setListOfProperties(new DFS().getProperties());
            else if (choiceBox.getValue() == GraphAlgorithms.BIPARTITION)
                controller.setListOfProperties(new Bipartition().getProperties());
            else if (choiceBox.getValue() == GraphAlgorithms.BFS)
                controller.setListOfProperties(new BFS().getProperties());
            else if (choiceBox.getValue() == GraphAlgorithms.BRIDGES)
                controller.setListOfProperties(new Bridges().getProperties());
            else if (choiceBox.getValue() == GraphAlgorithms.ARTICULATION_POINTS)
                controller.setListOfProperties(new Articulation().getProperties());
            root.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
