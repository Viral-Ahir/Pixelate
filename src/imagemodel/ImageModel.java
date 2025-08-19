package imagemodel;

/**
 * This interface defines a set of image processing operations that can be performed on images.
 */
public interface ImageModel {

  /**
   * A method that extracts the red component of an image.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the red component of the image.
   */
  int[][][] extractRedComponent(int[][][] colorArray);

  /**
   * A method that extracts the green component of an image.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the green component of the image.
   */
  int[][][] extractGreenComponent(int[][][] colorArray);

  /**
   * A method that extracts the blue component of an image.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the blue component of the image.
   */
  int[][][] extractBlueComponent(int[][][] colorArray);

  /**
   * A method that flips the image horizontally.
   *
   * @param originalArray A 3D integer array representing the original image.
   * @return A 3D integer array representing the horizontally flipped image.
   */
  int[][][] flipHorizontally(int[][][] originalArray);

  /**
   * A method that flips the image vertically.
   *
   * @param originalArray A 3D integer array representing the original image.
   * @return A 3D integer array representing the vertically flipped image.
   */
  int[][][] flipVertically(int[][][] originalArray);

  /**
   * A method that converts the image to sepia tone.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the sepia-toned image.
   */
  int[][][] convertToSepia(int[][][] colorArray);

  /**
   * A method that combines separate red, green, and blue components into a single color image.
   *
   * @param redImage   A 3D integer array representing the red component.
   * @param blueImage  A 3D integer array representing the blue component.
   * @param greenImage A 3D integer array representing the green component.
   * @return A 3D integer array representing the combined color image.
   */
  int[][][] combineRGBImage(int[][][] redImage, int[][][] blueImage, int[][][] greenImage);

  /**
   * A method that brightens the image by applying an adjustment to each color component.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @param adjustment The adjustment value to be applied to each color component.
   * @return A 3D integer array representing the brightened image.
   */
  int[][][] brightenImage(int[][][] colorArray, int adjustment);

  /**
   * A method that applies a blur effect to the image using a convolution kernel.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the blurred image.
   */
  int[][][] blurImage(int[][][] colorArray);

  /**
   * A method that applies a sharpening effect to the image using a convolution kernel.
   *
   * @param colorArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the sharpened image.
   */
  int[][][] sharpenImage(int[][][] colorArray);

  /**
   * A method that calculates the maximum color component value for each pixel.
   *
   * @param inputArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the image with maximum component values.
   */
  int[][][] calculateValue(int[][][] inputArray);

  /**
   * A method that calculates the intensity of the image as the average of the color components.
   *
   * @param inputArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the image in grayscale based on intensity.
   */
  int[][][] calculateIntensity(int[][][] inputArray);

  /**
   * A method that calculates the luma value of the image using the ITU-R BT.709 formula.
   *
   * @param inputArray A 3D integer array representing the color image.
   * @return A 3D integer array representing the image in grayscale based on luma.
   */
  int[][][] calculateLuma(int[][][] inputArray);
}