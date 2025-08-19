package imagemodel;

import java.awt.image.BufferedImage;

/**
 * A class to convert image formats from buffered to array and vice-versa.
 */
public class ImageConvert {

  /**
   * A method to convert array to bufferedImage.
   *
   * @param pixelArray the array to be converted.
   * @return a buffered image.
   */
  public BufferedImage convertToBufferedImage(int[][][] pixelArray) {
    int height = pixelArray.length;
    int width = pixelArray[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = pixelArray[i][j][0];
        int green = pixelArray[i][j][1];
        int blue = pixelArray[i][j][2];

        int pixelValue = (255 << 24) | (red << 16) | (green << 8) | blue;
        image.setRGB(j, i, pixelValue);
      }
    }
    return image;
  }

  /**
   * A method to convert buffered image to array form.
   *
   * @param image buffered image to be converted.
   * @return 3D array having pixels of image.
   */
  public int[][][] convertToArray(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    int[][][] array;
    array = new int[h][w][3];

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        int pixel = image.getRGB(j, i);
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        array[i][j][0] = red;
        array[i][j][1] = green;
        array[i][j][2] = blue;
      }
    }
    return array;
  }
}