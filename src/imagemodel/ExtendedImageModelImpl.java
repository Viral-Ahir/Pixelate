package imagemodel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Implementation of the ExtendedImageModelImpl interface that extends ImageModelImpl.
 */
public class ExtendedImageModelImpl extends ImageModelImpl implements ExtendedImageModel {

  @Override
  public int[][][] splitBlurImage(int[][][] colorArray, int splitPercentage) {
    return splitAndProcessImage(colorArray, splitPercentage, this::blurImage);
  }

  @Override
  public int[][][] splitSharpenImage(int[][][] colorArray, int splitPercentage) {
    return splitAndProcessImage(colorArray, splitPercentage, this::sharpenImage);
  }

  @Override
  public int[][][] splitSepiaImage(int[][][] colorArray, int splitPercentage) {
    return splitAndProcessImage(colorArray, splitPercentage, this::convertToSepia);
  }

  @Override
  public int[][][] splitColorCorrectionImage(int[][][] colorArray, int splitPercentage) {
    return splitAndProcessImage(colorArray, splitPercentage, this::colorCorrect);
  }

  @Override
  public int[][][] splitLevelAdjustImage(int[][][] colorArray, int b, int m, int w,
      int splitPercentage) {
    return splitAndProcessImage(colorArray, splitPercentage, xi -> adjustLevel(xi, b, m, w));
  }

  @Override
  public int[][][] splitGrayscaleImage(int[][][] colorArray, int splitPercentage) {
    return splitAndProcessImage(colorArray, splitPercentage, this::calculateLuma);
  }

  /**
   * Splits the input color array, applies an image processing function to one part, and combines it
   * with the original array.
   *
   * @param colorArray      The input color array representing the image.
   * @param splitPercentage The percentage of the image width at which to split and apply the
   *                        effect.
   * @param imageProcessor  The image processing function to be applied to the split part.
   * @return The combined color array after applying the image processing function.
   */
  public int[][][] splitAndProcessImage(int[][][] colorArray, int splitPercentage,
      Function<int[][][], int[][][]> imageProcessor) {
    try {
      if (splitPercentage < 0 || splitPercentage > 100) {
        throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
    int height = colorArray.length;
    int width = colorArray[0].length;

    int splitPosition = (int) (width * (splitPercentage / 100.0));

    int[][][] xi = getPart(colorArray, splitPosition);

    int[][][] leftPart = imageProcessor.apply(xi);

    int[][][] combinedArray = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      System.arraycopy(leftPart[i], 0, combinedArray[i], 0, splitPosition);
      System.arraycopy(colorArray[i], splitPosition, combinedArray[i], splitPosition,
          width - splitPosition);
    }
    return combinedArray;
  }

  /**
   * Retrieves a specific portion of the original color array up to the specified end column.
   *
   * @param originalArray The original color array representing the image.
   * @param endCol        The ending column index up to which the portion is retrieved.
   * @return The extracted portion of the color array.
   */
  private int[][][] getPart(int[][][] originalArray, int endCol) {
    int height = originalArray.length;
    int[][][] partArray = new int[height][endCol][3];

    for (int i = 0; i < height; i++) {
      System.arraycopy(originalArray[i], 0, partArray[i], 0, endCol);
    }
    return partArray;
  }

  @Override
  public int[][][] compress(int[][][] image, int threshold) {
    int height = image.length;
    int width = image[0].length;
    int channels = image[0][0].length;
    int targetSize = calculateTargetSize(Math.max(height, width));
    int[][][] compressedImage = new int[height][width][channels];

    try {
      if (threshold < 0 || threshold > 100) {
        throw new IllegalArgumentException("Compression percentage must be between 0 and 100.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }

    double[][][] transformedImage = new double[channels][height][width];
    for (int c = 0; c < channels; c++) {
      int[][] channel = new int[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          channel[i][j] = image[i][j][c];
        }
      }
      double[][] paddedChannel = padWithZeros(channel, targetSize);
      double[][] transformedChannel = applyHaarWaveletTransform(paddedChannel);

      transformedImage[c] = transformedChannel;
    }

    double compressionThreshold = calculateThreshold(transformedImage, threshold);

    for (int c = 0; c < channels; c++) {
      double[][] transformedChannel = transformedImage[c];
      for (int i = 0; i < transformedChannel.length; i++) {
        for (int j = 0; j < transformedChannel[0].length; j++) {
          if (Math.abs(transformedChannel[i][j]) < compressionThreshold) {
            transformedChannel[i][j] = 0;
          }
        }
      }
      double[][] invertedChannel = invertHaarWaveletTransform(transformedChannel);
      int numRows = invertedChannel.length;
      int numCols = invertedChannel[0].length;
      int[][] roundedValues = new int[numRows][numCols];
      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
          roundedValues[i][j] = (int) Math.round(invertedChannel[i][j]);
        }
      }
      int[][] originalChannel = removePadding(roundedValues, height, width);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          originalChannel[i][j] = Math.max(0, Math.min(255, originalChannel[i][j]));
          compressedImage[i][j][c] = originalChannel[i][j];
        }
      }
    }
    return compressedImage;
  }

  /**
   * Applies the Haar wavelet transform to the given image.
   *
   * @param image The input image to which the Haar wavelet transform is applied.
   * @return The image after applying the Haar wavelet transform.
   */
  private double[][] applyHaarWaveletTransform(double[][] image) {
    int length = image.length;
    while (length > 1) {
      for (int i = 0; i < length; i++) {
        image[i] = applyHaarOperation(image[i]);
      }
      for (int j = 0; j < length; j++) {
        double[] column = new double[length];
        for (int i = 0; i < length; i++) {
          column[i] = image[i][j];
        }
        double[] transformedColumn = applyHaarOperation(column);
        for (int i = 0; i < length; i++) {
          image[i][j] = transformedColumn[i];
        }
      }
      length /= 2;
    }
    return image;
  }

  /**
   * Applies the Haar operation to the given data array.
   *
   * @param data The input data array to which the Haar operation is applied.
   * @return The array after applying the Haar operation.
   */
  private double[] applyHaarOperation(double[] data) {
    int length = data.length;
    int halfLength = length / 2;
    double[] transformedData = new double[length];

    for (int i = 0; i < halfLength; i++) {
      double a = data[2 * i];
      double b = data[2 * i + 1];
      transformedData[i] = (a + b) / Math.sqrt(2);
      transformedData[i + halfLength] = ((a - b) / Math.sqrt(2));
    }
    return transformedData;
  }

  /**
   * Inverts the Haar wavelet transform on the transformed image.
   *
   * @param transformedImage The input image after Haar wavelet transform.
   * @return The image after inverting the Haar wavelet transform.
   */
  private double[][] invertHaarWaveletTransform(double[][] transformedImage) {
    int length = transformedImage.length;
    int c = 2;
    while (c <= length) {
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = transformedImage[i][j];
        }
        double[] originalColumn = invertHaarOperation(column);
        for (int i = 0; i < c; i++) {
          transformedImage[i][j] = originalColumn[i];
        }
      }
      for (int i = 0; i < c; i++) {
        transformedImage[i] = invertHaarOperation(transformedImage[i]);
      }
      c *= 2;
    }
    return transformedImage;
  }

  /**
   * Inverts the Haar operation on the transformed data array.
   *
   * @param transformedData The input data array after Haar operation.
   * @return The array after inverting the Haar operation.
   */
  private double[] invertHaarOperation(double[] transformedData) {
    int length = transformedData.length;
    int halfLength = length / 2;
    double[] originalData = new double[length];

    for (int i = 0; i < halfLength; i++) {
      double a = transformedData[i];
      double b = transformedData[i + halfLength];
      originalData[2 * i] = (a + b) / Math.sqrt(2);
      originalData[2 * i + 1] = (a - b) / Math.sqrt(2);
    }
    return originalData;
  }

  /**
   * Calculates the target size for padding based on the input size.
   *
   * @param size The input size for which the target size is calculated.
   * @return The target size for padding.
   */
  private int calculateTargetSize(int size) {
    int targetSize = 1;
    while (targetSize < size) {
      targetSize *= 2;
    }
    return targetSize;
  }

  /**
   * Pads the input array with zeros to the target size.
   *
   * @param input      The input array to be padded.
   * @param targetSize The target size for padding.
   * @return The padded array.
   */
  private double[][] padWithZeros(int[][] input, int targetSize) {
    int height = input.length;
    int width = input[0].length;
    double[][] paddedArray = new double[targetSize][targetSize];

    for (int i = 0; i < targetSize; i++) {
      for (int j = 0; j < targetSize; j++) {
        if (i < height && j < width) {
          paddedArray[i][j] = input[i][j];
        } else {
          paddedArray[i][j] = 0;
        }
      }
    }
    return paddedArray;
  }

  /**
   * Removes padding from the input array to the original size.
   *
   * @param input          The input array with padding.
   * @param originalHeight The original height of the array before padding.
   * @param originalWidth  The original width of the array before padding.
   * @return The array without padding.
   */
  private int[][] removePadding(int[][] input, int originalHeight, int originalWidth) {
    int[][] originalArray = new int[originalHeight][originalWidth];

    for (int i = 0; i < originalHeight; i++) {
      System.arraycopy(input[i], 0, originalArray[i], 0, originalWidth);
    }
    return originalArray;
  }

  /**
   * Calculates the threshold for compression based on the image and compression percentage.
   *
   * @param image                 The input image for which the threshold is calculated.
   * @param compressionPercentage The compression percentage used to determine the threshold.
   * @return The calculated threshold for compression.
   */
  private double calculateThreshold(double[][][] image, int compressionPercentage) {

    double[][] flattenedChannel = new double[3][image[0].length * image[0][0].length];

    for (int c = 0; c < 3; c++) {
      int index = 0;
      double[][] channel = image[c];
      for (double[] doubles : channel) {
        for (int j = 0; j < channel[0].length; j++) {
          double value = doubles[j];
          flattenedChannel[c][index] = value;
          index++;
        }
      }
    }

    double[] finalflattenedchannel = new double[flattenedChannel.length
        * flattenedChannel[0].length];
    int index = 0;
    for (double[] doubles : flattenedChannel) {
      for (int j = 0; j < flattenedChannel[0].length; j++) {
        double value = doubles[j];
        finalflattenedchannel[index] = value;
        index++;
      }
    }

    double[] sortedChannel = Arrays.copyOf(finalflattenedchannel, finalflattenedchannel.length);
    Arrays.sort(sortedChannel);
    double[] sortedChannelAbs = getUniqueAbsoluteValues(sortedChannel);
    Arrays.sort(sortedChannelAbs);
    int thresholdIndex = (int) ((compressionPercentage) / 100.0 * sortedChannelAbs.length);
    return Math.abs(sortedChannelAbs[thresholdIndex]);
  }

  /**
   * Retrieves unique absolute values from the sorted array.
   *
   * @param sortedArray The sorted array from which unique absolute values are retrieved.
   * @return An array containing unique absolute values.
   */
  private double[] getUniqueAbsoluteValues(double[] sortedArray) {
    Set<Double> uniqueAbsValuesSet = new HashSet<>();
    for (double value : sortedArray) {
      uniqueAbsValuesSet.add(Math.abs(value));
    }
    return uniqueAbsValuesSet.stream().mapToDouble(Double::doubleValue).toArray();
  }

  @Override
  public int[][][] adjustLevel(int[][][] colorArray, int b, int m, int w) {
    int width = colorArray.length;
    int height = colorArray[0].length;

    int[][][] adjustedImage = new int[width][height][3];

    try {
      if (!(b >= 0 && b <= m && m <= w && w <= 255)) {
        throw new IllegalArgumentException(
            "Values b, m, and w must be in ascending order and within the range of 0 to 255.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int c = 0; c < 3; c++) {
          int pixelValue = colorArray[x][y][c];
          int adjustedValue = applyLevelAdjustment(pixelValue, b, m, w);
          adjustedImage[x][y][c] = adjustedValue;
        }
      }
    }
    return adjustedImage;
  }

  /**
   * Applies the level adjustment to the given pixel value.
   *
   * @param pixelValue The input pixel value to which the level adjustment is applied.
   * @param b          Parameter 'b' used in the level adjustment formula.
   * @param m          Parameter 'm' used in the level adjustment formula.
   * @param w          Parameter 'w' used in the level adjustment formula.
   * @return The adjusted pixel value.
   */
  private int applyLevelAdjustment(int pixelValue, int b, int m, int w) {
    double a = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    double a1 = -b * (128 - 255) + 128 * w - 255 * m;
    double a2 = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    double a3 = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);

    double aValue = a1 / a;
    double bValue = a2 / a;
    double cValue = a3 / a;

    double adjustedValue = aValue * pixelValue * pixelValue + bValue * pixelValue + cValue;

    return Math.max(0, Math.min(255, (int) adjustedValue));
  }

  @Override
  public int[][][] colorCorrect(int[][][] colorArray) {

    int width = colorArray.length;
    int height = colorArray[0].length;
    int[] redHistogram = generateComponentHistogram(colorArray, 0);
    int[] greenHistogram = generateComponentHistogram(colorArray, 1);
    int[] blueHistogram = generateComponentHistogram(colorArray, 2);

    int redPeak = findMeaningfulPeak(redHistogram);
    int greenPeak = findMeaningfulPeak(greenHistogram);
    int bluePeak = findMeaningfulPeak(blueHistogram);

    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = averagePeak - redPeak;
    int greenOffset = averagePeak - greenPeak;
    int blueOffset = averagePeak - bluePeak;

    int[][][] correctedImage = new int[width][height][3];
    for (int i = 0; i < colorArray.length; i++) {
      for (int j = 0; j < colorArray[0].length; j++) {
        System.arraycopy(colorArray[i][j], 0, correctedImage[i][j], 0, correctedImage[0][0].length);
      }
    }

    for (int i = 0; i < correctedImage.length; i++) {
      for (int j = 0; j < correctedImage[0].length; j++) {
        correctedImage[i][j][0] += redOffset;
        correctedImage[i][j][1] += greenOffset;
        correctedImage[i][j][2] += blueOffset;

        correctedImage[i][j][0] = Math.min(255, Math.max(0, correctedImage[i][j][0]));
        correctedImage[i][j][1] = Math.min(255, Math.max(0, correctedImage[i][j][1]));
        correctedImage[i][j][2] = Math.min(255, Math.max(0, correctedImage[i][j][2]));
      }
    }
    return correctedImage;
  }

  /**
   * Finds and returns the peak position within the meaningful range (10 to 245) of the given
   * histogram.
   *
   * @param histogram The input histogram array representing frequency distribution.
   * @return The position of the peak within the meaningful range.
   */
  private int findMeaningfulPeak(int[] histogram) {
    // Find and return the peak position based on meaningful range (10 to 245)
    int peakPosition = 0;
    int maxFrequency = 0;

    for (int i = 10; i <= 245; i++) {
      if (histogram[i] > maxFrequency) {
        peakPosition = i;
        maxFrequency = histogram[i];
      }
    }
    return peakPosition;
  }

  @Override
  public int[][] generateHistogram(int[][][] colorArray) {
    int[] redHistogram = generateComponentHistogram(colorArray, 0);
    int[] greenHistogram = generateComponentHistogram(colorArray, 1);
    int[] blueHistogram = generateComponentHistogram(colorArray, 2);

    int[][] histograms = new int[3][256];

    for (int i = 0; i < 256; i++) {
      histograms[0][i] = redHistogram[i];
      histograms[1][i] = greenHistogram[i];
      histograms[2][i] = blueHistogram[i];
    }
    return histograms;
  }

  /**
   * Generates a histogram for the specified color component of the given color array.
   *
   * @param colorArray The input color array for which the histogram is generated.
   * @param component  The color component for which the histogram is generated (0 for red, 1 for
   *                   green, 2 for blue).
   * @return The histogram for the specified color component.
   */
  public int[] generateComponentHistogram(int[][][] colorArray, int component) {
    int[] histogram = new int[256];

    for (int[][] ints : colorArray) {
      for (int j = 0; j < colorArray[0].length; j++) {
        int pixelValue = ints[j][component];
        histogram[pixelValue]++;
      }
    }
    return histogram;
  }
}