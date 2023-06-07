package pl.edu.tcs.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import pl.edu.tcs.graph.model.AlgorithmProperties;

public class PropertiesController {
    @FXML
    private AnchorPane propertiesPane;
    @FXML
    private Button propertiesOkButton;
    private double y;
    Map<AlgorithmProperties, Integer> requirements;
    Collection<Pair<AlgorithmProperties, TextField>> fields;

    @FXML
    private void initialize() {
        y = 10.0;
        requirements = new HashMap<>();
        fields = new ArrayList<>();
    }

    public void setListOfProperties(Collection<AlgorithmProperties> ap) {
        if (ap.size() == 0) {
            Label label = new Label("No properties to set for this Algorithm");
            AnchorPane.setTopAnchor(label, y);
            propertiesPane.getChildren().addAll(label);
            return;
        }
        for (AlgorithmProperties i : ap) {
            Label label = new Label(i.toString() + " :");
            TextField field = new TextField();
            field.setPrefWidth(40.0);
            AnchorPane.setTopAnchor(label, y);
            AnchorPane.setLeftAnchor(label, 10.0);
            AnchorPane.setTopAnchor(field, y);
            AnchorPane.setLeftAnchor(field, 70.0);
            propertiesPane.getChildren().addAll(label, field);
            y += 40.0;
            fields.add(new Pair<>(i, field));
        }
    }

    public void acceptProperties() {
        for (Pair<AlgorithmProperties, TextField> i : fields) {
            try {
                Integer x = Integer.parseInt(i.getValue().getText());
                requirements.put(i.getKey(), x);
            } catch (Exception e) {
            }
        }
        Controller controller = new Controller();
        controller.setRequirements(requirements);
        Stage stage = (Stage) propertiesPane.getScene().getWindow();
        stage.close();
    }
}
