import imagemodel.ExtendedImageModel;

/**
 * Created ImageOperations mock class which perform different tasks related to image processing.
 */
public class MockImageModel implements ExtendedImageModel {

  protected final StringBuilder log;

  /**
   * Constructs a ImageProcess to initiate image data.
   *
   * @param log the inputs which is passing as arguments.
   */
  public MockImageModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public int[][][] extractRedComponent(int[][][] colorArray) {
    int pixel = colorArray[0][0][1];
    log.append("Input: red:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] extractGreenComponent(int[][][] colorArray) {
    int pixel = colorArray[0][0][1];
    log.append("Input: green:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] extractBlueComponent(int[][][] colorArray) {
    int pixel = colorArray[0][0][1];
    log.append("Input: blue:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] flipHorizontally(int[][][] originalArray) {
    int pixel = originalArray[0][0][1];
    log.append("Input: hflip:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] flipVertically(int[][][] originalArray) {
    int pixel = originalArray[0][0][1];
    log.append("Input: vflip:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] convertToSepia(int[][][] colorArray) {
    int pixel = colorArray[0][0][1];
    log.append("Input: sepia:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] combineRGBImage(int[][][] redImage, int[][][] blueImage, int[][][] greenImage) {
    log.append("Input: combine");
    return new int[0][][];
  }

  @Override
  public int[][][] brightenImage(int[][][] colorArray, int adjustment) {
    int pixel = colorArray[0][0][1];
    log.append("Input: bright:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] blurImage(int[][][] colorArray) {
    int pixel = colorArray[0][0][1];
    log.append("Input: blur:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] sharpenImage(int[][][] colorArray) {
    int pixel = colorArray[0][0][1];
    log.append("Input: sharpen:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] calculateValue(int[][][] inputArray) {
    int pixel = inputArray[0][0][1];
    log.append("Input: value:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] calculateIntensity(int[][][] inputArray) {
    int pixel = inputArray[0][0][1];
    log.append("Input: intensity:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] calculateLuma(int[][][] inputArray) {
    int pixel = inputArray[0][0][1];
    log.append("Input: luma:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] splitBlurImage(int[][][] colorArray, int splitPercentage) {
    int pixel = colorArray[0][0][1];
    log.append("Input: blurSplit:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] splitSharpenImage(int[][][] colorArray, int splitPercentage) {
    int pixel = colorArray[0][0][1];
    log.append("Input: sharpenSplit:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] splitSepiaImage(int[][][] colorArray, int splitPercentage) {
    int pixel = colorArray[0][0][1];
    log.append("Input: sepiaSplit:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] splitColorCorrectionImage(int[][][] colorArray, int splitPercentage) {
    int pixel = colorArray[0][0][1];
    log.append("Input: colorCorrectSplit:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] splitLevelAdjustImage(int[][][] colorArray, int b, int m, int w,
      int splitPercentage) {
    int pixel = colorArray[0][0][1];
    log.append("Input: levelAdjustSplit:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] splitGrayscaleImage(int[][][] colorArray, int splitPercentage) {
    int pixel = colorArray[0][0][1];
    log.append("Input: grayscaleSplit:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] compress(int[][][] image, int threshold) {
    int pixel = image[0][0][1];
    log.append("Input: compress:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][][] adjustLevel(int[][][] image, int b, int m, int w) {
    int pixel = image[0][0][1];
    log.append("Input: adjustLevel:").append(pixel);
    return new int[0][][];
  }

  @Override
  public int[][] generateHistogram(int[][][] inputArray) {
    int pixel = inputArray[0][0][1];
    log.append("Input: histogram").append(pixel);
    return new int[0][];
  }

  @Override
  public int[][][] colorCorrect(int[][][] inputArray) {
    int pixel = inputArray[0][0][1];
    log.append("Input: color-correct").append(pixel);
    return new int[0][][];
  }
}