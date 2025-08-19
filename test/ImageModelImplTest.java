import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import imagecontroller.handlefilters.HandleBlur;
import imagecontroller.handlefilters.HandleCompress;
import imagecontroller.handlefilters.HandleLevelAdjust;
import imagecontroller.handlefilters.HandleLoad;
import imagemodel.ExtendedImageModelImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test the Model in MVC.
 */
public class ImageModelImplTest {

  private ExtendedImageModelImpl filters;
  private int[][][] inputImage;
  private HandleLoad handleLoad;
  private HandleCompress handleCompress;
  private HashMap<String, int[][][]> imageMap;

  @Before
  public void setUp() {
    filters = new ExtendedImageModelImpl();
    inputImage = new int[][][]{
        {{255, 0, 0}, {0, 255, 255}, {128, 128, 128}},
        {{0, 0, 255}, {255, 255, 0}, {128, 128, 128}},
        {{255, 255, 0}, {0, 0, 255}, {128, 128, 128}}
    };
  }

  @Test
  public void testInvalidSplitPercentage() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    HandleBlur handleBlur = new HandleBlur(filters, imageMap);

    String[] tokens = {"blur", "loaded_image", "outputImage", "split", "150"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleBlur.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals("Error: Split percentage must be between 0 and 100.", errorMessage);
  }

  @Test
  public void testInvalidSplitPercentage2() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    HandleBlur handleBlur = new HandleBlur(filters, imageMap);

    String[] tokens = {"blur", "loaded_image", "outputImage", "split", "-150"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleBlur.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals("Error: Split percentage must be between 0 and 100.", errorMessage);
  }

  @Test
  public void testInvalidCompressPercentage1() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    handleCompress = new HandleCompress(filters, imageMap);

    String[] tokens = {"compress", "110", "loaded_image", "outputImage"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleCompress.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals("Error: Compression percentage must be between 0 and 100.", errorMessage);
  }

  @Test
  public void testInvalidCompressPercentage2() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    handleCompress = new HandleCompress(filters, imageMap);

    String[] tokens = {"compress", "-10", "loaded_image", "outputImage"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleCompress.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals("Error: Compression percentage must be between 0 and 100.", errorMessage);
  }

  @Test
  public void testInvalidBMWValue1() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    HandleLevelAdjust handleLevelAdjust = new HandleLevelAdjust(filters, imageMap);

    String[] tokens = {"levels-adjust", "100", "10", "200", "loaded_image", "outputImage"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleLevelAdjust.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals(
        "Error: Values b, m, and w must be in ascending order and within the range of 0 to 255.",
        errorMessage);
  }

  @Test
  public void testInvalidBMWValue2() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    HandleLevelAdjust handleLevelAdjust = new HandleLevelAdjust(filters, imageMap);

    String[] tokens = {"levels-adjust", "-10", "100", "200", "loaded_image", "outputImage"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleLevelAdjust.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals(
        "Error: Values b, m, and w must be in ascending order and within the range of 0 to 255.",
        errorMessage);
  }

  @Test
  public void testInvalidBMWValue3() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";
    imageMap = new HashMap<>();
    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad = new HandleLoad(null, imageMap);
    handleLoad.apply(loadCommand);
    HandleLevelAdjust handleLevelAdjust = new HandleLevelAdjust(filters, imageMap);

    String[] tokens = {"levels-adjust", "27", "32", "333", "loaded_image", "outputImage"};
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    handleLevelAdjust.apply(tokens);
    String errorMessage = outContent.toString().trim();

    assertEquals(
        "Error: Values b, m, and w must be in ascending order and within the range of 0 to 255.",
        errorMessage);
  }

  @Test
  public void testExtractRedComponent() {

    int[][][] expectedOutput = {
        {{255, 0, 0}, {0, 0, 0}, {128, 0, 0}},
        {{0, 0, 0}, {255, 0, 0}, {128, 0, 0}},
        {{255, 0, 0}, {0, 0, 0}, {128, 0, 0}}
    };

    int[][][] result = filters.extractRedComponent(inputImage);
    assertArrayEquals(expectedOutput, result);
  }

  @Test
  public void testExtractGreenComponent() {
    int[][][] expectedOutput = {
        {{0, 0, 0}, {0, 255, 0}, {0, 128, 0}},
        {{0, 0, 0}, {0, 255, 0}, {0, 128, 0}},
        {{0, 255, 0}, {0, 0, 0}, {0, 128, 0}}
    };

    int[][][] result = filters.extractGreenComponent(inputImage);
    assertArrayEquals(expectedOutput, result);
  }

  @Test
  public void testExtractBlueComponent() {

    int[][][] expectedOutput = {
        {{0, 0, 0}, {0, 0, 255}, {0, 0, 128}},
        {{0, 0, 255}, {0, 0, 0}, {0, 0, 128}},
        {{0, 0, 0}, {0, 0, 255}, {0, 0, 128}}
    };

    int[][][] result = filters.extractBlueComponent(inputImage);
    assertArrayEquals(expectedOutput, result);
  }

  @Test
  public void testHorizontalFlip() {
    int[][][] expectedOutput = {
        {{128, 128, 128}, {0, 255, 255}, {255, 0, 0}},
        {{128, 128, 128}, {255, 255, 0}, {0, 0, 255}},
        {{128, 128, 128}, {0, 0, 255}, {255, 255, 0}}
    };

    int[][][] flippedImage = filters.flipHorizontally(inputImage);

    assertArrayEquals(expectedOutput, flippedImage);
  }

  @Test
  public void testVerticalFlip() {
    int[][][] expectedOutput = {
        {{255, 255, 0}, {0, 0, 255}, {128, 128, 128}},
        {{0, 0, 255}, {255, 255, 0}, {128, 128, 128}},
        {{255, 0, 0}, {0, 255, 255}, {128, 128, 128}}
    };

    int[][][] flippedImage = filters.flipVertically(inputImage);

    assertArrayEquals(expectedOutput, flippedImage);
  }

  @Test
  public void testConvertToSepia() {
    int[][][] expectedOutput = {
        {{100, 88, 69}, {244, 217, 169}, {172, 153, 119}},
        {{48, 42, 33}, {255, 255, 205}, {172, 153, 119}},
        {{255, 255, 205}, {48, 42, 33}, {172, 153, 119}}
    };

    int[][][] sepiaImage = filters.convertToSepia(inputImage);

    assertArrayEquals(expectedOutput, sepiaImage);
  }

  @Test
  public void testBrightenImage() {
    int[][][] expectedOutput = {
        {{255, 100, 100}, {100, 255, 255}, {228, 228, 228}},
        {{100, 100, 255}, {255, 255, 100}, {228, 228, 228}},
        {{255, 255, 100}, {100, 100, 255}, {228, 228, 228}}
    };

    int adjustment = 100;
    int[][][] brightenedImage = filters.brightenImage(inputImage, adjustment);

    assertArrayEquals(expectedOutput, brightenedImage);
  }

  @Test
  public void testDarkenImage() {
    int[][][] expectedOutput = {
        {{155, 0, 0}, {0, 155, 155}, {28, 28, 28}},
        {{0, 0, 155}, {155, 155, 0}, {28, 28, 28}},
        {{155, 155, 0}, {0, 0, 155}, {28, 28, 28}},
    };

    int adjustment = -100;
    int[][][] brightenedImage = filters.brightenImage(inputImage, adjustment);

    assertArrayEquals(expectedOutput, brightenedImage);
  }

  @Test
  public void testBlurImage() {
    int[][][] expectedOutput = {
        {{159, 63, 95}, {111, 159, 143}, {111, 159, 143}},
        {{127, 95, 127}, {127, 143, 127}, {127, 143, 127}},
        {{159, 159, 95}, {111, 111, 143}, {111, 111, 143}}
    };

    int[][][] blurredImage = filters.blurImage(inputImage);

    assertArrayEquals(expectedOutput, blurredImage);
  }

  @Test
  public void testSharpenImage() {
    int[][][] expectedOutput = {
        {{175, 0, 79}, {0, 255, 255}, {80, 255, 175}},
        {{0, 15, 255}, {175, 239, 79}, {80, 175, 175}},
        {{175, 238, 79}, {0, 15, 255}, {80, 80, 175}}
    };

    int[][][] sharpenImage = filters.sharpenImage(inputImage);

    assertArrayEquals(expectedOutput, sharpenImage);
  }

  @Test
  public void testValueImage() {
    int[][][] expectedOutput = {
        {{255, 255, 255}, {255, 255, 255}, {128, 128, 128}},
        {{255, 255, 255}, {255, 255, 255}, {128, 128, 128}},
        {{255, 255, 255}, {255, 255, 255}, {128, 128, 128}},
    };

    int[][][] valueImage = filters.calculateValue(inputImage);

    assertArrayEquals(expectedOutput, valueImage);
  }

  @Test
  public void testLumaImage() {
    int[][][] expectedOutput = {
        {{54, 54, 54}, {200, 200, 200}, {128, 128, 128}},
        {{18, 18, 18}, {236, 236, 236}, {128, 128, 128}},
        {{236, 236, 236}, {18, 18, 18}, {128, 128, 128}},
    };

    int[][][] lumaImage = filters.calculateLuma(inputImage);

    assertArrayEquals(expectedOutput, lumaImage);
  }

  @Test
  public void testIntensityImage() {
    int[][][] expectedOutput = {
        {{85, 85, 85}, {170, 170, 170}, {128, 128, 128}},
        {{85, 85, 85}, {170, 170, 170}, {128, 128, 128}},
        {{170, 170, 170}, {85, 85, 85}, {128, 128, 128}},
    };

    int[][][] intensityImage = filters.calculateIntensity(inputImage);

    assertArrayEquals(expectedOutput, intensityImage);
  }

  @Test
  public void testGrayscaleImage() {
    int[][][] expectedOutput = {
        {{255, 255, 255}, {255, 255, 255}, {128, 128, 128}},
        {{255, 255, 255}, {255, 255, 255}, {128, 128, 128}},
        {{255, 255, 255}, {255, 255, 255}, {128, 128, 128}},
    };

    int[][][] grayscale = filters.calculateValue(inputImage);

    assertArrayEquals(expectedOutput, grayscale);
  }

  @Test
  public void testRgbSplitAndCombine() {
    int[][][] redComponent = filters.extractRedComponent(inputImage);
    int[][][] greenComponent = filters.extractGreenComponent(inputImage);
    int[][][] blueComponent = filters.extractBlueComponent(inputImage);

    int[][][] combinedImage = filters.combineRGBImage(redComponent, greenComponent, blueComponent);

    assertArrayEquals(inputImage, combinedImage);
  }

  @Test
  public void testCompress50Image() {
    int[][][] expectedOutput = {
        {{255, 0, 0}, {0, 231, 199}, {136, 152, 120}},
        {{8, 0, 247}, {183, 231, 0}, {136, 152, 120}},
        {{215, 215, 0}, {8, 8, 247}, {88, 88, 72}}
    };

    int[][][] array3D = filters.compress(inputImage, 50);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testCompress90Image() {
    int[][][] expectedOutput = {
        {{72, 0, 72}, {72, 168, 72}, {72, 168, 72}},
        {{72, 0, 72}, {72, 168, 72}, {72, 168, 72}},
        {{72, 72, 72}, {72, 72, 72}, {72, 72, 72}}
    };

    int[][][] array3D = filters.compress(inputImage, 90);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void test0SplitImage() {
    int[][][] expectedOutput = {
        {{255, 0, 0}, {0, 255, 255}, {128, 128, 128}},
        {{0, 0, 255}, {255, 255, 0}, {128, 128, 128}},
        {{255, 255, 0}, {0, 0, 255}, {128, 128, 128}}
    };

    int[][][] array3D = filters.splitBlurImage(inputImage, 0);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void test100SplitImage() {
    int[][][] expectedOutput = {
        {{159, 63, 95}, {111, 159, 143}, {111, 159, 143}},
        {{127, 95, 127}, {127, 143, 127}, {127, 143, 127}},
        {{159, 159, 95}, {111, 111, 143}, {111, 111, 143}}
    };

    int[][][] array3D = filters.splitBlurImage(inputImage, 100);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testBlurSplitImage() {
    int[][][] expectedOutput = {
        {{191, 0, 63}, {0, 255, 255}, {128, 128, 128}},
        {{127, 63, 127}, {255, 255, 0}, {128, 128, 128}},
        {{191, 191, 63}, {0, 0, 255}, {128, 128, 128}}
    };

    int[][][] array3D = filters.splitBlurImage(inputImage, 50);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testSharpenSplitImage() {
    int[][][] expectedOutput = {
        {{127, 0, 127}, {0, 255, 255}, {128, 128, 128}},
        {{0, 0, 255}, {255, 255, 0}, {128, 128, 128}},
        {{127, 255, 127}, {0, 0, 255}, {128, 128, 128}}
    };

    int[][][] array3D = filters.splitSharpenImage(inputImage, 50);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testSepiaSplitImage() {
    int[][][] expectedOutput = {
        {{100, 88, 69}, {0, 255, 255}, {128, 128, 128}},
        {{48, 42, 33}, {255, 255, 0}, {128, 128, 128}},
        {{255, 255, 205}, {0, 0, 255}, {128, 128, 128}}
    };

    int[][][] array3D = filters.splitSepiaImage(inputImage, 50);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testGrayscaleSplitImage() {
    int[][][] expectedOutput = {
        {{54, 54, 54}, {0, 255, 255}, {128, 128, 128}},
        {{18, 18, 18}, {255, 255, 0}, {128, 128, 128}},
        {{236, 236, 236}, {0, 0, 255}, {128, 128, 128}}
    };

    int[][][] array3D = filters.splitGrayscaleImage(inputImage, 50);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testLevelAdjustSplitImage() {
    int[][][] expectedOutput = {
        {{255, 0, 0}, {0, 255, 255}, {128, 128, 128}},
        {{0, 0, 255}, {255, 255, 0}, {128, 128, 128}},
        {{255, 255, 0}, {0, 0, 255}, {128, 128, 128}}
    };

    int[][][] array3D = filters.splitLevelAdjustImage(inputImage, 10, 133, 200, 50);

    assertArrayEquals(expectedOutput, array3D);
  }

  @Test
  public void testHistogram() {
    int[][][] inputImage1 = new int[][][]{
        {{1, 0, 0}, {0, 1, 2}, {1, 2, 2}},
        {{0, 0, 1}, {2, 2, 0}, {1, 1, 1}},
        {{2, 2, 0}, {0, 0, 2}, {1, 1, 1}}
    };

    int[][] expectedOutput = new int[3][256];

    for (int i = 0; i < 3; i++) {
      Arrays.fill(expectedOutput[i], 0);
    }
    expectedOutput[0][0] = 3;
    expectedOutput[0][1] = 4;
    expectedOutput[0][2] = 2;
    expectedOutput[1][0] = 3;
    expectedOutput[1][1] = 3;
    expectedOutput[1][2] = 3;
    expectedOutput[2][0] = 3;
    expectedOutput[2][1] = 3;
    expectedOutput[2][2] = 3;

    int[][] resultHistogram = filters.generateHistogram(inputImage1);

    assertArrayEquals(expectedOutput, resultHistogram);
  }

  @Test
  public void testAllComponentsHistogram() {
    int[][][] inputImage1 = new int[][][]{
        {{1, 0, 0}, {0, 1, 2}, {1, 2, 2}},
        {{0, 0, 1}, {2, 2, 0}, {1, 1, 1}},
        {{2, 2, 0}, {0, 0, 2}, {1, 1, 1}}
    };

    int[] expectedRedHistogram = new int[256];
    int[] expectedGreenHistogram = new int[256];
    int[] expectedBlueHistogram = new int[256];

    for (int i = 0; i < expectedRedHistogram.length; i++) {
      expectedRedHistogram[i] = 0;
      expectedGreenHistogram[i] = 0;
      expectedBlueHistogram[i] = 0;
    }

    expectedRedHistogram[0] = 3;
    expectedRedHistogram[1] = 4;
    expectedRedHistogram[2] = 2;

    expectedGreenHistogram[0] = 3;
    expectedGreenHistogram[1] = 3;
    expectedGreenHistogram[2] = 3;

    expectedBlueHistogram[0] = 3;
    expectedBlueHistogram[1] = 3;
    expectedBlueHistogram[2] = 3;

    int[] resultRedHistogram = filters.generateComponentHistogram(inputImage1, 0);
    int[] resultGreenHistogram = filters.generateComponentHistogram(inputImage1, 1);
    int[] resultBlueHistogram = filters.generateComponentHistogram(inputImage1, 2);

    assertArrayEquals(expectedRedHistogram, resultRedHistogram);
    assertArrayEquals(expectedGreenHistogram, resultGreenHistogram);
    assertArrayEquals(expectedBlueHistogram, resultBlueHistogram);
  }

  @Test
  public void testColorCorrect() {
    int[][][] inputImage = new int[][][]{
        {{0, 0, 0}, {11, 12, 13}, {0, 0, 0}},
        {{0, 0, 0}, {11, 12, 13}, {0, 0, 0}},
        {{0, 0, 0}, {11, 12, 13}, {0, 0, 0}}
    };

    int[][] expectedOutput = new int[3][256];

    for (int i = 0; i < 3; i++) {
      Arrays.fill(expectedOutput[i], 0);
    }
    expectedOutput[0][1] = 6;
    expectedOutput[0][12] = 3;
    expectedOutput[1][0] = 6;
    expectedOutput[1][12] = 3;
    expectedOutput[2][0] = 6;
    expectedOutput[2][12] = 3;

    int[][][] result = filters.colorCorrect(inputImage);
    int[][] resultHistogram = filters.generateHistogram(result);

    assertArrayEquals(expectedOutput, resultHistogram);
  }
}
