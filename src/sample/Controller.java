package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {

    }

    @FXML
    public void showSelectWindow() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());
    }
}
