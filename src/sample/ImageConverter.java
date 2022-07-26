package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class ImageConverter {

    private final BufferedImage originalImage;
    private Image currentImage;

    public ImageConverter(BufferedImage originalImage) {
        this.originalImage = originalImage;
        this.currentImage = convertBufferedImagetoImageType(originalImage);
    }

    protected Image getCurrentImage() {
        return currentImage;
    }

    protected void convertImage(int sliderValue) {

    }

    private Image convertBufferedImagetoImageType(BufferedImage bufferedImage){
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
