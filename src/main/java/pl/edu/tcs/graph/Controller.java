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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.tcs.graph.algo.*;
import pl.edu.tcs.graph.model.*;
import pl.edu.tcs.graph.model.Algorithm.VertexAction;
import pl.edu.tcs.graph.view.GraphVisualization;
import pl.edu.tcs.graph.view.GridVisualization;
import pl.edu.tcs.graph.view.Visualization;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Controller {
    @Getter
    @RequiredArgsConstructor
    private enum GraphAlgorithms {
        DFS(new DFS()),
        BFS(new BFS()),
        BIPARTITION(new Bipartition()),
        BRIDGES(new Bridges()),
        ARTICULATION_POINTS(new Articulation()),
        SCCS(new SCC()),
        MST(new MST()),
        ANYCYCLE(new CycleFinding()),
        ASTAR(new Astar()),
        MAZE(new Maze());

        private final Algorithm algorithm;
    }

    private Visualization visualization = new GraphVisualization();

    private Collection<Algorithm.VertexAction> vertexActions;

    private Thread algoThread;
    private final AlgoMiddleman aM = new AlgoMiddleman() {
        @Override
        public boolean setVertexColor(Vertex v, int r, int g, int b) {
            boolean result = visualization.setVertexColor(v, javafx.scene.paint.Color.rgb(r, g, b));
            try {
                Thread.sleep(paint_delay);
            } catch (InterruptedException ignored) {
            }
            Platform.runLater((() -> {
                visualization.updateDrawing(false);
                stage.setScene(scene);
                stage.show();
            }));
            return result;
        }

        @Override
        public boolean instantSetVertexColor(Vertex v, int r, int g, int b) {
            boolean result = visualization.setVertexColor(v, javafx.scene.paint.Color.rgb(r, g, b));
            Platform.runLater((() -> {
                visualization.updateDrawing(false);
                stage.setScene(scene);
                stage.show();
            }));
            return result;
        }

        @Override
        public boolean instantSetEdgeColor(Edge e, int r, int g, int b) {
            boolean result = visualization.setEdgeColor(e, javafx.scene.paint.Color.rgb(r, g, b));
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
            } catch (InterruptedException ignored) {
            }
            Platform.runLater((() -> {
                visualization.updateDrawing(false);
                stage.setScene(scene);
                stage.show();
            }));
            return result;
        }

        @Override
        public Optional<Double> getX(Vertex v) {
            if (visualization.getGraph() instanceof GridGraph)
                return Optional.of(((GridGraph) visualization.getGraph()).getX(v));
            return Optional.empty();
        }

        @Override
        public Optional<Double> getY(Vertex v) {
            if (visualization.getGraph() instanceof GridGraph)
                return Optional.of(((GridGraph) visualization.getGraph()).getY(v));
            return Optional.empty();
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
    private TextArea adjListInput;
    @FXML
    private ChoiceBox<GraphAlgorithms> choiceBox;
    @FXML
    private TextField gridHeightTextField;
    @FXML
    private TextField gridWidthTextField;
    @FXML
    private TextField paintDelayTextField;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab normalTab;

    @FXML
    private Tab gridTab;

    private boolean diGraph = false;

    @FXML
    private void diGraphSwitch() {
        diGraph = !diGraph;
        graphPane.getChildren().clear();
        if (!diGraph) {
            choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
                    GraphAlgorithms.BFS, GraphAlgorithms.BIPARTITION, GraphAlgorithms.BRIDGES,
                    GraphAlgorithms.ARTICULATION_POINTS, GraphAlgorithms.MST, GraphAlgorithms.SCCS,
                    GraphAlgorithms.ANYCYCLE);
            choiceBox.setItems(choiceList);

        } else {
            choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
                    GraphAlgorithms.BFS, GraphAlgorithms.SCCS, GraphAlgorithms.ANYCYCLE);
            choiceBox.setItems(choiceList);
        }
    }

    public void changeToGrid() {
        int height, width;
        try {
            height = Integer.parseInt(gridHeightTextField.getText());
            width = Integer.parseInt(gridWidthTextField.getText());
        } catch (Exception e) {
            return;
        }
        visualization = new GridVisualization(width, height, width * 20, height * 20,
                dv -> {
                    if (dv.getVertex().isActive()) {
                        dv.setFill(Paint.valueOf("gray"));
                        dv.getVertex().setActive(false);
                    } else {
                        dv.setFill(Paint.valueOf("white"));
                        dv.getVertex().setActive(true);
                    }
                    return null;
                });
        visualization.initialize();
        visualization.setVertexActions(vertexActions);
        mainPane.lookup("#graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("deprecation")
    @FXML
    private void initialize() {
        choiceBox.setItems(choiceList);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (algoThread != null) {
                isSomeoneRunning = false;
                algoThread.stop();
            }
            if (newTab == normalTab) {
                visualization = new GraphVisualization();
                choiceList = FXCollections.observableArrayList(
                        GraphAlgorithms.DFS,
                        GraphAlgorithms.BFS,
                        GraphAlgorithms.BIPARTITION,
                        GraphAlgorithms.BRIDGES,
                        GraphAlgorithms.ARTICULATION_POINTS,
                        GraphAlgorithms.MST,
                        GraphAlgorithms.SCCS,
                        GraphAlgorithms.ANYCYCLE);
                choiceBox.setItems(choiceList);
            } else if (newTab == gridTab) {
                visualization = new GridVisualization(0, 0, 0, 0);
                choiceList = FXCollections.observableArrayList(
                        GraphAlgorithms.DFS,
                        GraphAlgorithms.BFS,
                        GraphAlgorithms.MAZE,
                        GraphAlgorithms.ASTAR,
                        GraphAlgorithms.SCCS,
                        GraphAlgorithms.ANYCYCLE,
                        GraphAlgorithms.ARTICULATION_POINTS);
                choiceBox.setItems(choiceList);
            }
            visualization.setVertexActions(vertexActions);
            graphPane.getChildren().clear();
        });
        choiceBox.getSelectionModel().selectedItemProperty().addListener((boxObservable, oldValue, newValue) -> {
            if (newValue != null) {
                vertexActions = new ArrayList<VertexAction>();
                if (newValue.algorithm.getVertexActions() != null)
                    vertexActions.addAll(newValue.algorithm.getVertexActions());
                vertexActions.add(new VertexAction("choose color", (v -> {
                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    ColorPicker colorPicker = new ColorPicker();
                    Button submit = new Button("submit");
                    submit.setOnAction(e -> {
                        visualization.setVertexColor(v, colorPicker.getValue());
                        popupStage.close();
                    });
                    VBox root = new VBox(colorPicker, submit);
                    Scene scene = new Scene(root, 300, 200);
                    popupStage.setScene(scene);
                    popupStage.showAndWait();
                    return null;
                })));
                visualization.setVertexActions(vertexActions);
            }
        });
    }

    ObservableList<GraphAlgorithms> choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
            GraphAlgorithms.BFS, GraphAlgorithms.BIPARTITION, GraphAlgorithms.BRIDGES,
            GraphAlgorithms.ARTICULATION_POINTS, GraphAlgorithms.MST, GraphAlgorithms.SCCS, GraphAlgorithms.ANYCYCLE);
    private static Map<AlgorithmProperties, Integer> requirements;

    public void setRequirements(Map<AlgorithmProperties, Integer> req) {
        requirements = new HashMap<>(req);
    }

    public void nextGraph(ActionEvent e) {
        mainPane.lookup("#graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        if (diGraph)
            visualization.setGraph(DigraphImpl.randomGraph(1));
        else
            visualization.setGraph(GraphImpl.randomGraph(1));
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
            if (input.length % 3 != 0)
                throw new Exception();
            if (diGraph)
                visualization.setGraph(DigraphImpl.fromAdjacencyList(input));
            else
                visualization.setGraph(GraphImpl.fromAdjacencyList(input));
            visualization.updateDrawing(true);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong input!");
            alert.showAndWait();
        }
    }

    private int paint_delay = 50;

    @FXML
    private void setDelay() {
        paint_delay = Integer.parseInt(paintDelayTextField.getText());
    }

    private boolean isSomeoneRunning = false;

    private void runAlgo(Algorithm a, Map<AlgorithmProperties, Integer> req) {
        algoThread = new Thread(() -> {
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
        });
        algoThread.start();
    }

    public void runAlgorithm(ActionEvent ev) {
        if (choiceBox.getValue() == null || isSomeoneRunning)
            return;
        if (requirements == null)
            requirements = new HashMap<>();
        isSomeoneRunning = true;

        Algorithm currentAlgo = choiceBox.getValue().getAlgorithm();
        runAlgo(currentAlgo, requirements);
    }

    public void openProperties(ActionEvent e) {
        try {
            URL fxmlLocation = getClass().getResource("/properties.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Stage root = loader.load();
            PropertiesController controller = loader.<PropertiesController>getController();
            controller.setListOfProperties(choiceBox.getValue().algorithm.getProperties());
            root.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
