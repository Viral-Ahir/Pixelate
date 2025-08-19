package imageview;

import imagecontroller.Features;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * The ImageView interface provides methods for displaying messages, images, histograms, and
 * handling user interactions in an image viewer application.
 */
public interface ImageView {

  /**
   * Displays a message in the command line.
   *
   * @param message The message to be printed.
   */
  void displayMessage(String message);

  /**
   * Sets the photo to be displayed in the view.
   *
   * @param image The image to be set as the photo.
   */
  void setPhoto(Image image);

  /**
   * Generates a histogram view based on the provided histogram data and image map.
   *
   * @param hist     The histogram data as a string.
   * @param imageMap The map containing image data.
   */
  void generateHistogramView(String hist, Map<String, int[][][]> imageMap);

  /**
   * Sets the histogram image to be displayed in the view.
   *
   * @param image The histogram image to be set.
   */
  void setHistogram(Image image);

  /**
   * Adds features to the view for processing images.
   *
   * @param features The features to be added.
   */
  void addFeatures(Features features);

  /**
   * Displays an error message in the view.
   *
   * @param error The error message to be displayed.
   */
  void throwError(String error);

  /**
   * Handles the Bright button view interaction.
   *
   * @param brighten The bright command.
   * @return The result of the bright operation.
   */
  String handleBrightenButtonView(String brighten);

  /**
   * Handles the Compress button view interaction.
   *
   * @param compress The compress command.
   * @return The result of the compress operation.
   */
  String handleCompressButtonView(String compress);

  /**
   * Handles the Levels Adjust button view interaction.
   *
   * @param levelAdjust The level adjustment command.
   * @return An array of results from the level adjustment operation.
   */
  String[] handleLevelsAdjustButtonView(String levelAdjust);

  /**
   * Handles the Load button view interaction.
   *
   * @return The result of the load operation.
   */
  String handleLoadButton();

  /**
   * Handles the Save button view interaction.
   *
   * @return The result of the save operation.
   */
  String handleSaveButton();

  /**
   * Creates a BufferedImage from the processed image array.
   *
   * @param processedImageArray The processed image data array.
   * @return The created BufferedImage.
   */
  BufferedImage createBufferedImage(int[][][] processedImageArray);

  /**
   * Gets the processed image based on the provided image name and image map.
   *
   * @param image1   The name of the image to be processed.
   * @param imageMap The map containing image data.
   * @return The processed Image.
   */
  Image getProcessedImage(String image1, Map<String, int[][][]> imageMap);

  /**
   * Handles the Image Processing button view interaction.
   *
   * @param command      The command for image processing.
   * @param supportSplit A boolean indicating whether split processing is supported.
   */
  void handleImageProcessingButtonView(String command, Boolean supportSplit);

  /**
   * Gets the values associated with BMW (Brightness, Contrast, and Saturation).
   *
   * @return An array containing the BMW values.
   */
  String[] getBMWValues();
}
