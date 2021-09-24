package sample;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem butOpen;

    @FXML
    private TableView<?> TabInfo;

    @FXML
    private AnchorPane PaneForModels;

    int size;
    class Coordinate {
        double x;
        double y;
        double z;

        Coordinate(double x0, double y0, double z0) {
            x = x0;
            y = y0;
            z = z0;
        }
    }

    ArrayList<Coordinate> allAtoms;
    URI url;
    @FXML
    void initialize() {

        butOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XYZ files (*.xyz)", "*.xyz");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(Main.prim);
                url = file.toURI();
            }
        });

    }
}

