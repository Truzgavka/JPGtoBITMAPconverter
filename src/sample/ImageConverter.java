package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    private final Image originalImage;
    private Image currentImage;

    public ImageConverter(BufferedImage originalImage) {
        this.originalImage = SwingFXUtils.toFXImage(originalImage, null);
        this.currentImage = this.originalImage;
    }

    protected Image getCurrentImage() {
        return currentImage;
    }

    protected Image convertImage(int sliderValue) {

        //przejsc na tylko image z bufferedimage
        BufferedImage image = SwingFXUtils.fromFXImage(originalImage, null);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage tmpImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int currentPixel = image.getRGB(x, y);
                //bitmask used to get all the colors 0x means (its hex)
                //>> means right shift
                int blue = currentPixel & 0xff;
                int green = (currentPixel >> 8) & 0xff;
                int red = (currentPixel >> 16) & 0xff;
                if (blue > sliderValue | green > sliderValue | red > sliderValue) {
                    blue = 255;
                    green = 255;
                    red = 255;
                } else {
                    blue = 0;
                    green = 0;
                    red = 0;
                }
                currentPixel = 255 << 24 | red << 16 | green << 8 | blue;
                tmpImage.setRGB(x, y, currentPixel);
            }
        }
        currentImage = SwingFXUtils.toFXImage(tmpImage, null);

        return currentImage;
    }

    public void saveImage(File file) throws IOException {
        if (file != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(currentImage, null), "png", file);
        }
    }
}
