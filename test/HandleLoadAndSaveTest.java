import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import imagecontroller.handlefilters.HandleLoad;
import imagecontroller.handlefilters.HandleSave;
import java.io.File;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test load and save.
 */
public class HandleLoadAndSaveTest {

  private HandleLoad handleLoad;

  private HandleSave handleSave;
  private HashMap<String, int[][][]> imageMap;
  private final String testImageFolder = "resources\\scripts\\ScriptResults\\";


  @Before
  public void setUp() {
    imageMap = new HashMap<>();
    handleLoad = new HandleLoad(null, imageMap);
    handleSave = new HandleSave(null, imageMap);
  }

  @Test
  public void testLoadJPGImage() {
    String imagePath = "resources\\assets\\dp.jpg";
    String imageName = "loaded_image";

    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad.apply(loadCommand);

    // Check if the image has been loaded into the HashMap
    assertTrue(imageMap.containsKey(imageName));
    int[][][] loadedImage = imageMap.get(imageName);

    int height = loadedImage.length;
    int width = loadedImage[0].length;

    assertEquals(1200, width);
    assertEquals(1600, height);
  }

  @Test
  public void testLoadPNGImage() {
    String imagePath = "resources\\assets\\cat24bit.png";
    String imageName = "loaded_image";

    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad.apply(loadCommand);

    // Check if the image has been loaded into the HashMap
    assertTrue(imageMap.containsKey(imageName));
    int[][][] loadedImage = imageMap.get(imageName);

    int height = loadedImage.length;
    int width = loadedImage[0].length;

    assertEquals(600, width);
    assertEquals(401, height);
  }

  @Test
  public void testLoadPPMImage() {
    String imagePath = "resources\\assets\\testppm.ppm";
    String imageName = "loaded_image";

    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad.apply(loadCommand);

    // Check if the image has been loaded into the HashMap
    assertTrue(imageMap.containsKey(imageName));
    int[][][] loadedImage = imageMap.get(imageName);

    int height = loadedImage.length;
    int width = loadedImage[0].length;

    assertEquals(5, width);
    assertEquals(5, height);
  }

  @Test
  public void testSaveImageAsJPG() {
    int[][][] sampleImage = new int[2][2][3];
    // Assuming a simple 2x2 red image
    sampleImage[0][0][0] = 0xFF0000; // Red
    imageMap.put("sample_image", sampleImage);

    String imageName = "sample_image";

    String outputPath = testImageFolder + imageName + ".jpg";

    String[] saveCommand = {"save", outputPath, imageName};
    handleSave.apply(saveCommand);

    File savedImageFile = new File(outputPath);
    assertTrue(savedImageFile.exists());
    assertTrue(savedImageFile.getAbsolutePath().endsWith(".jpg"));
  }

  @Test
  public void testSaveImageAsPNG() {
    // Create a sample image in the imageMap
    int[][][] sampleImage = new int[2][2][3];
    // Assuming a simple 2x2 green image
    sampleImage[0][0][1] = 0x00FF00; // Green
    imageMap.put("sample_image", sampleImage);

    String imageName = "sample_image";

    String outputPath = testImageFolder + imageName + ".png";

    String[] saveCommand = {"save", outputPath, imageName};
    handleSave.apply(saveCommand);

    // Check if the image file has been saved as PNG
    File savedImageFile = new File(outputPath);
    assertTrue(savedImageFile.exists());
    assertTrue(savedImageFile.getAbsolutePath().endsWith(".png"));
  }

  @Test
  public void testSaveImageAsPPM() {
    // Create a sample image in the imageMap
    int[][][] sampleImage = new int[2][2][3];
    // Assuming a simple 2x2 blue image
    sampleImage[0][0][2] = 0x0000FF; // Blue
    imageMap.put("sample_image", sampleImage);

    String imageName = "sample_image";

    String ppmOutputPath = testImageFolder + "output_image.ppm";
    String[] saveCommand = {"save", ppmOutputPath, imageName};
    handleSave.apply(saveCommand);

    // Check if the image file has been saved as PPM
    File savedImageFile = new File(ppmOutputPath);
    assertTrue(savedImageFile.exists());
    assertTrue(savedImageFile.getAbsolutePath().endsWith(".ppm"));
  }


  @Test
  public void testMultipleLoad() {
    String imagePath = "C:\\Users\\tanay\\OneDrive\\Desktop\\testppm.ppm";
    String imageName = "loaded_image";

    String[] loadCommand = {"load", imagePath, imageName};
    handleLoad.apply(loadCommand);

    String imagePath2 = "resources\\assets\\dp.jpg";
    String imageName2 = "loaded_image";

    String[] loadCommand2 = {"load", imagePath2, imageName2};
    handleLoad.apply(loadCommand2);

    assertTrue(imageMap.containsKey(imageName));
    assertTrue(imageMap.containsKey(imageName2));
  }

  @Test
  public void testMultipleSave() {

    int[][][] sampleImage = new int[2][2][3];
    sampleImage[0][0][2] = 0x0000FF;
    imageMap.put("sample_image", sampleImage);
    String imageName = "sample_image";
    String ppmOutputPath = testImageFolder + "output_image.ppm";
    String[] saveCommand = {"save", ppmOutputPath, imageName};
    handleSave.apply(saveCommand);

    int[][][] sampleImage2 = new int[2][2][3];
    sampleImage2[0][0][0] = 0xFF0000;
    imageMap.put("sample_image", sampleImage2);
    String imageName2 = "sample_image";
    String outputPath2 = testImageFolder + imageName2 + ".jpg";
    String[] saveCommand2 = {"save", outputPath2, imageName2};
    handleSave.apply(saveCommand2);

    File savedImageFile = new File(ppmOutputPath);
    assertTrue(savedImageFile.exists());
    File savedImageFile2 = new File(outputPath2);
    assertTrue(savedImageFile2.exists());

  }
}