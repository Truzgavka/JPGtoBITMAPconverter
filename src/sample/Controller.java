package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    @FXML
    private MenuItem reset;
    @FXML
    private MenuItem showOriginal;

    private ImageConverter imageConverter;
    private int sliderValue;

    public void initialize() {

        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(imageView.getImage() != null) {
                    sliderValue = (int) slider.getValue();
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
            slider.setValue(0.0);
            imageView.setImage(imageConverter.getCurrentImage());

            showOriginal.setDisable(false);
            reset.setDisable(false);
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

    @FXML
    public void resetImage() {
        slider.setValue(0.0);
        imageView.setImage(imageConverter.getOriginalImage());
    }

    @FXML
    public void showOriginal() {
        Stage originalImageStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("originalImage.fxml"));

        try {
            Parent root = fxmlLoader.load();
            originalImageStage.initOwner(mainBorderPane.getScene().getWindow());
            originalImageStage.setScene(new Scene(root, 500, 500));
            originalImageStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        OriginalImageController originalImageController = fxmlLoader.getController();
        originalImageController.setImageView(imageConverter.getOriginalImage());

        showOriginal.setDisable(true);
    }

    @FXML
    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG","*.jpg, *.png"));
        File selectedFile = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());
        try {
            imageConverter.saveImage(selectedFile);
        } catch (IOException e) {
            System.out.println("dupa");

        }
    }
}
