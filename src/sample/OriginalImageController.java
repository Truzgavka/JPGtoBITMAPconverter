package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalImageController {

    @FXML
    private ImageView imageView;

    public void setImageView(Image image) {
        imageView.setImage(image);
    }
}
