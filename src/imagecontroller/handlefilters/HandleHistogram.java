package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import imagemodel.ImageConvert;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Class for histogram method application call to model.
 */
public class HandleHistogram extends HandleConstruct implements HandleInterfaceCommand {

  private final ImageConvert imageConvertor = new ImageConvert();

  /**
   * Constructs a HandleHistogram object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleHistogram(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid histogram command.");
      return;
    }

    String outputImageName = tokens[2];
    String imageName = tokens[1];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
      return;
    }

    int[][] histograms = imageFilter.generateHistogram(imageFromHashMap);
    int width = 256;
    int height = 256;
    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphicHistogram = histogramImage.createGraphics();

    graphicHistogram.setBackground(Color.WHITE);
    graphicHistogram.clearRect(0, 0, width, height);

    drawGridLines(graphicHistogram, width, height, 10);

    scaleHistogram(histograms, graphicHistogram);
    int[][][] histogramArrayImage = imageConvertor.convertToArray(histogramImage);
    imageMap.put(outputImageName, histogramArrayImage);
  }

  /**
   * A helper method to scale and plot the histogram values.
   *
   * @param histograms the array that stores the frequencies of all the values.
   * @param graphics   an object of Graphics2D.
   */
  private void scaleHistogram(int[][] histograms, Graphics2D graphics) {
    int maxFrequency = findMaxFrequency(histograms);

    int[] channelColors = {0xFF0000, 0x00FF00, 0x0000FF};

    for (int channelIndex = 0; channelIndex < 3; channelIndex++) {
      int[] frequencies = histograms[channelIndex];
      Color color = new Color(channelColors[channelIndex]);
      graphics.setColor(color);

      for (int i = 0; i < 255; i++) {
        int scaledFrequency = (int) (frequencies[i] * (255.0 / maxFrequency));
        int scaledNextFrequency = (int) (frequencies[i + 1] * (255.0 / maxFrequency));

        graphics.drawLine(i, 255 - scaledFrequency, i + 1, 255 - scaledNextFrequency);
      }
    }
  }

  /**
   * A private helper method to find the maximum frequency of a particular histogram.
   *
   * @param histograms the array that stores the frequencies of all the values.
   * @return the maximum frequency.
   */
  private int findMaxFrequency(int[][] histograms) {
    int maxFrequency = histograms[0][0];
    for (int[] frequencies : histograms) {
      for (int frequency : frequencies) {
        if (frequency > maxFrequency) {
          maxFrequency = frequency;
        }
      }
    }
    return maxFrequency;
  }

  /**
   * A private helper method to draw grid lines.
   *
   * @param graphics an object of Graphics2D.
   * @param width    the width of the histogram.
   * @param height   the height of the histogram.
   * @param gridSize the required grid size in the histogram.
   */
  private void drawGridLines(Graphics2D graphics, int width, int height, int gridSize) {
    graphics.setColor(new Color(230, 230, 230));
    graphics.setStroke(new BasicStroke(0.5f));

    for (int i = 0; i < width; i += gridSize) {
      graphics.drawLine(i, 0, i, height);
    }

    for (int i = 0; i < height; i += gridSize) {
      graphics.drawLine(0, i, width, i);
    }
  }
}