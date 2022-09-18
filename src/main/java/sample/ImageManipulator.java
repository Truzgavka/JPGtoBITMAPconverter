package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageManipulator {

    private final BufferedImage originalImage;
    private BufferedImage currentImage;

    public ImageManipulator(BufferedImage originalImage) {
        this.originalImage = originalImage;
        this.currentImage = this.originalImage;
    }

    private Image convertBufferedImageToFXImage (BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    protected Image getCurrentImage() {
        return convertBufferedImageToFXImage(currentImage);
    }

    protected Image getOriginalImage() {
        return convertBufferedImageToFXImage(originalImage);
    }

    protected Image convertImage(int sliderValue) {

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage tmpImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currentPixel = originalImage.getRGB(x, y);
                //bitmask used to get all the colors 0x means (its hex)
                //>> means right shift
                int blue = currentPixel & 0xff;
                int green = (currentPixel >> 8) & 0xff;
                int red = (currentPixel >> 16) & 0xff;
                if (blue > sliderValue || green > sliderValue || red > sliderValue) {
                    blue = green = red = 255;
                } else {
                    blue = green = red = 0;
                }
                currentPixel = 255 << 24 | red << 16 | green << 8 | blue;
                tmpImage.setRGB(x, y, currentPixel);
            }
        }

        currentImage = tmpImage;

        return convertBufferedImageToFXImage(tmpImage);
    }

    public void saveImage(File file) throws IOException {
        if (file != null && !originalImage.equals(currentImage)) {
            ImageIO.write(currentImage, "bmp", file);
        }
    }
}
