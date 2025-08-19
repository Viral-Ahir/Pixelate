package imagemodel;

/**
 * A class representing the model, contains all methods for filters.
 */
public class ImageModelImpl implements ImageModel {

  @Override
  public int[][][] extractRedComponent(int[][][] colorArray) {
    return extractComponent(colorArray, 1, 0, 0);
  }

  @Override
  public int[][][] extractGreenComponent(int[][][] colorArray) {
    return extractComponent(colorArray, 0, 1, 0);
  }

  @Override
  public int[][][] extractBlueComponent(int[][][] colorArray) {
    return extractComponent(colorArray, 0, 0, 1);
  }

  /**
   * A helper method to extract a single color component (red, green, or blue).
   *
   * @param colorArray  A 3D integer array representing the color image.
   * @param redOffset   the flag variable to get red component.
   * @param greenOffset the flag variable to get blue component.
   * @param blueOffset  the flag variable to get green component.
   * @return A 3D integer array representing the red or green or blue component of the image.
   */
  private int[][][] extractComponent(int[][][] colorArray, int redOffset, int greenOffset,
      int blueOffset) {
    int height = colorArray.length;
    int width = colorArray[0].length;

    int[][][] componentArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = colorArray[i][j][0];
        int green = colorArray[i][j][1];
        int blue = colorArray[i][j][2];

        componentArray[i][j][0] = redOffset != 0 ? red : 0;
        componentArray[i][j][1] = greenOffset != 0 ? green : 0;
        componentArray[i][j][2] = blueOffset != 0 ? blue : 0;
      }
    }

    return componentArray;
  }

  @Override
  public int[][][] flipHorizontally(int[][][] originalArray) {
    return flipArray(originalArray, true, false);
  }

  @Override
  public int[][][] flipVertically(int[][][] originalArray) {
    return flipArray(originalArray, false, true);
  }

  /**
   * A helper method to flip an array horizontally or vertically.
   *
   * @param originalArray A 3D integer array representing the color image.
   * @param horizontally  the flag variable to flip horizontally.
   * @param vertically    the flag variable to flip vertically.
   * @return A 3D integer array representing the flipped image.
   */
  private int[][][] flipArray(int[][][] originalArray, boolean horizontally, boolean vertically) {
    int height = originalArray.length;
    int width = originalArray[0].length;

    int[][][] flippedArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int targetI = vertically ? height - i - 1 : i;
        int targetJ = horizontally ? width - j - 1 : j;
        flippedArray[targetI][targetJ] = originalArray[i][j];
      }
    }
    return flippedArray;
  }

  @Override
  public int[][][] calculateValue(int[][][] inputArray) {
    return calculateColorComponent(inputArray, "max");
  }

  @Override
  public int[][][] calculateIntensity(int[][][] inputArray) {
    return calculateColorComponent(inputArray, "avg");
  }

  @Override
  public int[][][] calculateLuma(int[][][] inputArray) {
    return calculateColorComponent(inputArray, "luma");
  }

  /**
   * A helper method to calculate a color component (max, avg, or luma).
   *
   * @param inputArray A 3D integer array representing the color image.
   * @param type       the type of greyscale we want to apply
   * @return A 3D integer array representing the requested color component image.
   */
  private int[][][] calculateColorComponent(int[][][] inputArray, String type) {
    int height = inputArray.length;
    int width = inputArray[0].length;
    int[][][] outputArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = inputArray[i][j][0];
        int green = inputArray[i][j][1];
        int blue = inputArray[i][j][2];

        int componentValue = 0;

        switch (type) {
          case "max":
            componentValue = Math.max(red, Math.max(green, blue));
            break;
          case "avg":
            componentValue = (red + green + blue) / 3;
            break;
          case "luma":
            componentValue = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
            break;
          default:
            break;
        }

        outputArray[i][j][0] = componentValue;
        outputArray[i][j][1] = componentValue;
        outputArray[i][j][2] = componentValue;
      }
    }
    return outputArray;
  }


  @Override
  public int[][][] blurImage(int[][][] colorArray) {
    return applyKernelFilter(colorArray, getGaussianBlurKernel());
  }

  @Override
  public int[][][] sharpenImage(int[][][] colorArray) {
    return applyKernelFilter(colorArray, getSharpenKernel());
  }

  /**
   * A helper method to apply a kernel filter to the image to blur or sharpen the image.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @param kernel     the 2D array representing the kernel that is used.
   * @return A 3D integer array representing the blurred or sharpened image.
   */
  private int[][][] applyKernelFilter(int[][][] colorArray, int[][] kernel) {
    int height = colorArray.length;
    int width = colorArray[0].length;
    int[][][] filteredArray = new int[height][width][3];

    int kernelSum = calculateKernelSum(kernel);
    int kernelSize = kernel.length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (int x = -kernelSize / 2; x <= kernelSize / 2; x++) {
          for (int y = -kernelSize / 2; y <= kernelSize / 2; y++) {
            int neighborX = i + x;
            int neighborY = j + y;

            // Handle border pixels by replicating values from the nearest valid pixel
            neighborX = Math.max(0, Math.min(neighborX, height - 1));
            neighborY = Math.max(0, Math.min(neighborY, width - 1));

            int neighborRed = colorArray[neighborX][neighborY][0];
            int neighborGreen = colorArray[neighborX][neighborY][1];
            int neighborBlue = colorArray[neighborX][neighborY][2];

            int kernelValue = kernel[x + kernelSize / 2][y + kernelSize / 2];

            red += neighborRed * kernelValue;
            green += neighborGreen * kernelValue;
            blue += neighborBlue * kernelValue;
          }
        }

        red /= kernelSum;
        green /= kernelSum;
        blue /= kernelSum;

        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        filteredArray[i][j][0] = red;
        filteredArray[i][j][1] = green;
        filteredArray[i][j][2] = blue;
      }
    }

    return filteredArray;
  }

  /**
   * A helper method to calculate the sum of all elements in a kernel.
   *
   * @param kernel the 2D array representing the kernel that is used.
   * @return the sum of the elements in kernel.
   */
  private int calculateKernelSum(int[][] kernel) {
    int sum = 0;
    for (int[] ints : kernel) {
      for (int anInt : ints) {
        sum += anInt;
      }
    }
    return (sum == 0) ? 1 : sum;
  }

  /**
   * A helper method to get the Gaussian blur kernel.
   *
   * @return an 2D array representation of kernel for blur filter.
   */
  private int[][] getGaussianBlurKernel() {
    return new int[][]{
        {1, 2, 1},
        {2, 4, 2},
        {1, 2, 1}
    };
  }

  /**
   * A helper method to get the sharpen kernel.
   *
   * @return an 2D array representation of kernel for sharpen filter.
   */
  private int[][] getSharpenKernel() {
    return new int[][]{
        {-1, -1, -1, -1, -1},
        {-1, 2, 2, 2, -1},
        {-1, 2, 8, 2, -1},
        {-1, 2, 2, 2, -1},
        {-1, -1, -1, -1, -1}
    };
  }

  @Override
  public int[][][] convertToSepia(int[][][] colorArray) {
    int height = colorArray.length;
    int width = colorArray[0].length;

    int[][][] sepiaArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = colorArray[i][j][0];
        int green = colorArray[i][j][1];
        int blue = colorArray[i][j][2];

        int sepiaRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
        int sepiaGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
        int sepiaBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

        sepiaRed = Math.min(255, sepiaRed);
        sepiaGreen = Math.min(255, sepiaGreen);
        sepiaBlue = Math.min(255, sepiaBlue);

        sepiaArray[i][j][0] = sepiaRed;
        sepiaArray[i][j][1] = sepiaGreen;
        sepiaArray[i][j][2] = sepiaBlue;
      }
    }

    return sepiaArray;
  }

  @Override
  public int[][][] combineRGBImage(int[][][] redImage, int[][][] greenImage,
      int[][][] blueImage) {
    int height = redImage.length;
    int width = redImage[0].length;
    int[][][] combinedRGBArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = redImage[i][j][0];
        int green = greenImage[i][j][1];
        int blue = blueImage[i][j][2];

        combinedRGBArray[i][j][0] = red;   // Red component
        combinedRGBArray[i][j][1] = green; // Green component
        combinedRGBArray[i][j][2] = blue;  // Blue component
      }
    }
    return combinedRGBArray;
  }

  @Override
  public int[][][] brightenImage(int[][][] colorArray, int adjustment) {
    int height = colorArray.length;
    int width = colorArray[0].length;

    int[][][] adjustedArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = colorArray[i][j][0] + adjustment;
        int green = colorArray[i][j][1] + adjustment;
        int blue = colorArray[i][j][2] + adjustment;

        // Clamp the values to the 0-255 range
        red = (red < 0) ? 0 : Math.min(red, 255);
        green = (green < 0) ? 0 : Math.min(green, 255);
        blue = (blue < 0) ? 0 : Math.min(blue, 255);

        adjustedArray[i][j][0] = red;
        adjustedArray[i][j][1] = green;
        adjustedArray[i][j][2] = blue;
      }
    }
    return adjustedArray;
  }
}