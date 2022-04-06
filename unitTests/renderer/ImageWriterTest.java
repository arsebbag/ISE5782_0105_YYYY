package renderer;

import primitives.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        Color yellowColor = new Color(255,255,0);
        Color redColor = new Color(0,255,0);
        int nX = 800;
        int nY = 600;
        ImageWriter imageWriter = new ImageWriter("testyellow", nX, nY);
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, redColor);
                } else
                    imageWriter.writePixel(i, j, yellowColor);
            }
        }
        imageWriter.writeToImage();
    }

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage2() {
        ImageWriter imageWriter = new ImageWriter("testblue3", 800, 600);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 600; j++) {
                // 800/16 = 50 and 600/10 = 60
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(255,0,0));
                } else {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.BLUE));
                }
            }
        }
        imageWriter.writeToImage();
    }


}