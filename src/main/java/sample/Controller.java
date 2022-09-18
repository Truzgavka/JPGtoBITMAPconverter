package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    @FXML
    private Button saveFile;

    private ImageManipulator imageManipulator;

    public void initialize() {

        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(imageView.getImage() != null) {
                    imageView.setImage(imageManipulator.convertImage((int) slider.getValue()));
                }
            }
        });

    }

    @FXML
    public void showSelectWindow() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG","*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());

        if(selectedFile != null) {
            try {
                //inizjalizacja i użycie ImageConvertera
                imageManipulator = new ImageManipulator(ImageIO.read(selectedFile));
                slider.setValue(0.0);
                imageView.setImage(imageManipulator.getCurrentImage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            showOriginal.setDisable(false);
            reset.setDisable(false);
            saveFile.setDisable(false);
            slider.setDisable(false);
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
        imageView.setImage(imageManipulator.getOriginalImage());
    }

    @FXML
    public void showOriginal() {
        Stage originalImageStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/originalImage.fxml"));

        try {
            Parent root = fxmlLoader.load();
            originalImageStage.initOwner(mainBorderPane.getScene().getWindow());
            originalImageStage.setScene(new Scene(root, 500, 500));
            originalImageStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        OriginalImageController originalImageController = fxmlLoader.getController();
        originalImageController.setImageView(imageManipulator.getOriginalImage());

        showOriginal.setDisable(true);
        originalImageStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                showOriginal.setDisable(false);
            }
        });
    }

    @FXML
    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BMP","*.bmp"));
        File selectedFile = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());
        try {
            imageManipulator.saveImage(selectedFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
