import static org.junit.Assert.assertArrayEquals;

import imagemodel.ImageConvert;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

/**
 * Test class to test convert formats.
 */
public class ImageConvertTest {

  @Test
  public void testConvertToArrayAndConvertToBufferedImage() throws IOException {
    ImageConvert imageConvert = new ImageConvert();

    BufferedImage originalImage = ImageIO.read(
        new File("resources\\assets\\dp.jpg"));

    int[][][] array = imageConvert.convertToArray(originalImage);

    BufferedImage convertedImage = imageConvert.convertToBufferedImage(array);

    int originalWidth = originalImage.getWidth();
    int originalHeight = originalImage.getHeight();
    int convertedWidth = convertedImage.getWidth();
    int convertedHeight = convertedImage.getHeight();
    assertArrayEquals(new int[]{originalWidth, originalHeight},
        new int[]{convertedWidth, convertedHeight});

    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {
        int originalRGB = originalImage.getRGB(j, i);
        int convertedRGB = convertedImage.getRGB(j, i);
        assertArrayEquals(new int[]{originalRGB}, new int[]{convertedRGB});
      }
    }
  }
}
