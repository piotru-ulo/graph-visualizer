package pl.edu.tcs.graph;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import pl.edu.tcs.graph.algo.AlgorithmException;
import pl.edu.tcs.graph.algo.Articulation;
import pl.edu.tcs.graph.algo.BFS;
import pl.edu.tcs.graph.algo.Bipartition;
import pl.edu.tcs.graph.algo.Bridges;
import pl.edu.tcs.graph.algo.CycleFinding;
import pl.edu.tcs.graph.algo.DFS;
import pl.edu.tcs.graph.algo.MST;
import pl.edu.tcs.graph.algo.SCC;
import pl.edu.tcs.graph.model.Algorithm;
import pl.edu.tcs.graph.model.AlgorithmProperties;
import pl.edu.tcs.graph.model.DigraphImpl;
import pl.edu.tcs.graph.model.GraphImpl;
import pl.edu.tcs.graph.view.GraphVisualization;
import pl.edu.tcs.graph.view.GridVisualization;
import pl.edu.tcs.graph.view.Visualization;
import pl.edu.tcs.graph.viewmodel.AlgoMiddleman;
import pl.edu.tcs.graph.viewmodel.Edge;
import pl.edu.tcs.graph.viewmodel.Vertex;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Visualization visualization = new GraphVisualization();

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
    @FXML
    private TextField gridHeightTextField;
    @FXML
    private TextField gridWidthTextField;
    @FXML
    private Button setPropertiesButton;
    @FXML
    private TextField paintDelayTextField;
    @FXML
    private Text gridLabel;
    @FXML
    private Button gridAcceptButton;
    @FXML
    private Text insertLabel;
    @FXML
    private Button acceptFromInput;
    @FXML
    private Button randomButton;
    @FXML
    private CheckBox diGraphCheckBox;

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
        int height = Integer.parseInt(gridHeightTextField.getText());
        int width = Integer.parseInt(gridWidthTextField.getText());
        System.out.println("changing to grid: " + height + " " + width);
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
        mainPane.lookup("#graphPane");
        graphPane.getChildren().clear();
        graphPane.getChildren().add(visualization.getNode());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        choiceBox.setItems(choiceList);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(algoThread!=null) {
                System.out.println("interrupting");
                isSomeoneRunning = false;
                algoThread.stop();
                //TODO: fix the stop?
            }
            if (newTab == normalTab) {
                System.out.println("normal tab ");
                visualization = new GraphVisualization();
                choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
                        GraphAlgorithms.BFS, GraphAlgorithms.BIPARTITION, GraphAlgorithms.BRIDGES,
                        GraphAlgorithms.ARTICULATION_POINTS, GraphAlgorithms.MST, GraphAlgorithms.SCCS,
                        GraphAlgorithms.ANYCYCLE);
                choiceBox.setItems(choiceList);

//                choiceBox.getSelectionModel().selectedItemProperty().
//                        addListener((boxObservable, oldValue, newValue) -> {
//                            ContextMenu contextMenu = new ContextMenu();
//                            for(AlgorithmProperties ap : )
//                            visualization.setVertexContextMenu();
//                            System.out.println("Selected: " + newValue);
//
//                        });
            }
            else if(newTab == gridTab) {
                System.out.println("grid tab");
                visualization = new GridVisualization(0, 0, 0, 0);
                choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
                        GraphAlgorithms.BFS);
                choiceBox.setItems(choiceList);
            }
            graphPane.getChildren().clear();
        });
    }

    private enum GraphAlgorithms {
        DFS,
        BFS,
        BIPARTITION,
        BRIDGES,
        ARTICULATION_POINTS,
        SCCS,
        MST,
        ANYCYCLE
    }

    ObservableList<GraphAlgorithms> choiceList = FXCollections.observableArrayList(GraphAlgorithms.DFS,
            GraphAlgorithms.BFS, GraphAlgorithms.BIPARTITION, GraphAlgorithms.BRIDGES,
            GraphAlgorithms.ARTICULATION_POINTS, GraphAlgorithms.MST, GraphAlgorithms.SCCS, GraphAlgorithms.ANYCYCLE);
    private static Map<AlgorithmProperties, Integer> requirements;

    public void setRequirements(Map<AlgorithmProperties, Integer> req) {
        requirements = new HashMap<>(req);
        System.out.println(requirements);
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
        for (Vertex v : visualization.getGraph().getVertices())
            if (v.isActive())
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
        else if (choiceBox.getValue() == GraphAlgorithms.SCCS)
            runAlgo(new SCC(), requirements);
        else if (choiceBox.getValue() == GraphAlgorithms.MST)
            runAlgo(new MST(), requirements);
        else if (choiceBox.getValue() == GraphAlgorithms.ANYCYCLE)
            runAlgo(new CycleFinding(), requirements);
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
            else if (choiceBox.getValue() == GraphAlgorithms.SCCS)
                controller.setListOfProperties(new SCC().getProperties());
            else if (choiceBox.getValue() == GraphAlgorithms.MST)
                controller.setListOfProperties(new MST().getProperties());
            else if (choiceBox.getValue() == GraphAlgorithms.ANYCYCLE)
                controller.setListOfProperties(new CycleFinding().getProperties());
            root.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
