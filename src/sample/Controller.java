package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Controller {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ImageView imageToConvert;

    public void initialize() {

    }

    @FXML
    public void showSelectWindow() {
        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG","*.jpg, *.png"));
        File selectedFile = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());

        if(selectedFile != null) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(selectedFile);
            } catch (IOException e) {
            }
            imageToConvert.setImage(SwingFXUtils.toFXImage(img, null));
        }


    }
}
