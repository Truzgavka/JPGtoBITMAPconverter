package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Slider slider;

    private ImageConverter imageConverter;
    private int sliderValue;

    public void initialize() {

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(imageView.getImage() != null) {
                    sliderValue = t1.intValue();

                    System.out.println(sliderValue);
                    imageView.setImage(imageConverter.convertImage(sliderValue));
                }
            }
        });

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
            //inizjalizacja i użycie ImageConvertera
            imageConverter = new ImageConverter(img);
            imageView.setImage(imageConverter.getCurrentImage());
            slider.setValue(0.0);
        }
    }

    @FXML
    public void exitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure you want to exit application?");
        alert.setContentText("Not saved progress will be losted.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
