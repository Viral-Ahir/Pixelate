package imagemodel;

/**
 * The ExtendedImageModel interface defines methods for processing image arrays.
 */
public interface ExtendedImageModel extends ImageModel {

  /**
   * Compresses the image using the specified threshold.
   *
   * @param image     The 3D array representing the color values of the image.
   * @param threshold The compression threshold.
   * @return The compressed image.
   */
  int[][][] compress(int[][][] image, int threshold);

  /**
   * Adjusts the levels of the entire image.
   *
   * @param image The 3D array representing the color values of the image.
   * @param b     The b parameter for level adjustment.
   * @param m     The m parameter for level adjustment.
   * @param w     The w parameter for level adjustment.
   * @return The modified image after adjusting levels.
   */
  int[][][] adjustLevel(int[][][] image, int b, int m, int w);

  /**
   * Generates the histogram of the entire image.
   *
   * @param colorArray The 3D array representing the color values of the image.
   * @return The histogram of the image.
   */
  int[][] generateHistogram(int[][][] colorArray);

  /**
   * Applies color correction to the entire image.
   *
   * @param colorArray The 3D array representing the color values of the image.
   * @return The modified image after applying color correction.
   */
  int[][][] colorCorrect(int[][][] colorArray);

  /**
   * Splits and applies a blur effect to the specified portion of the input color array.
   *
   * @param colorArray      The input color array representing the image.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @return The processed color array.
   */
  int[][][] splitBlurImage(int[][][] colorArray, int splitPercentage);

  /**
   * Splits and applies a sharpening effect to the specified portion of the input color array.
   *
   * @param colorArray      The input color array representing the image.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @return The processed color array.
   */
  int[][][] splitSharpenImage(int[][][] colorArray, int splitPercentage);

  /**
   * Splits and applies a sepia tone effect to the specified portion of the input color array.
   *
   * @param colorArray      The input color array representing the image.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @return The processed color array.
   */
  int[][][] splitSepiaImage(int[][][] colorArray, int splitPercentage);

  /**
   * Splits and applies a color correction effect to the specified portion of the input color
   * array.
   *
   * @param colorArray      The input color array representing the image.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @return The processed color array.
   */
  int[][][] splitColorCorrectionImage(int[][][] colorArray, int splitPercentage);

  /**
   * Splits and adjusts the levels of the specified portion of the input color array.
   *
   * @param colorArray      The input color array representing the image.
   * @param b               The brightness adjustment factor.
   * @param m               The contrast adjustment factor.
   * @param w               The gamma correction factor.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @return The processed color array.
   */
  int[][][] splitLevelAdjustImage(int[][][] colorArray, int b, int m, int w, int splitPercentage);

  /**
   * Splits and converts the specified portion of the input color array to grayscale.
   *
   * @param colorArray      The input color array representing the image.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @return The processed color array.
   */
  int[][][] splitGrayscaleImage(int[][][] colorArray, int splitPercentage);
}
