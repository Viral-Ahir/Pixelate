import static org.junit.Assert.assertEquals;

import imagecontroller.ImageController;
import imagecontroller.ImageControllerImpl;
import imageview.ImageViewImpl;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class to test controller using mock model.
 */
public class ImageControllerTest {


  private ImageController imageController;
  private MockImageModel mockModel;
  private ImageViewImpl imageView;

  @Before
  public void setUp() {
    imageView = new ImageViewImpl();
    StringBuilder log = new StringBuilder();
    mockModel = new MockImageModel(log);
    imageController = new ImageControllerImpl(mockModel, imageView);
    String commandload = "load resources\\assets\\dp.jpg image1";
    imageController.executeCommand(commandload);
  }

  @Test
  public void testTokenizationRedCommand() {
    String command = "red-component image1 red1";
    imageController.executeCommand(command);
    assertEquals("Input: red:141", mockModel.log.toString());
  }


  @Test
  public void testTokenizationGreenCommand() {
    String command = "green-component image1 green1";
    imageController.executeCommand(command);

    assertEquals("Input: green:141", mockModel.log.toString());
  }

  @Test
  public void testTokenizationBlueCommand() {
    String command = "blue-component image1 blue1";
    imageController.executeCommand(command);

    assertEquals("Input: blue:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteHorizontalFlipCommand() {
    String command = "horizontal-flip image1 hflip1";
    imageController.executeCommand(command);

    assertEquals("Input: hflip:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteVerticalFlipCommand() {
    String command = "vertical-flip image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: vflip:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteSepiaCommand() {
    String command = "sepia image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: sepia:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteBrightenCommand() {
    String command = "brighten 100 image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: bright:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteBlurCommand() {
    String command = "blur image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: blur:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteSharpenCommand() {
    String command = "sharpen image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: sharpen:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteValueCommand() {
    String command = "value-component image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: value:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteIntensityCommand() {
    String command = "intensity-component image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: intensity:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteLumaCommand() {
    String command = "luma-component image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: luma:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteRGBCombineCommand() {
    String command = "rgb-split image1 Rimage1 Gimage1 Bimage1";
    String command2 = "rgb-combine outputimage1 Rimage1 Gimage1 Bimage1";
    imageController.executeCommand(command);
    imageController.executeCommand(command2);

    assertEquals("Input: red:141Input: green:141Input: blue:141Input: combine",
        mockModel.log.toString());
  }

  @Test
  public void testExecuteCommandWithInvalidCommand() {
    String invalidCommand = "invalid-command";
    String expectedErrorMessage = "Invalid command: The length of token is less then 2 or in is "
        + "just a just blank line.";

    imageController.executeCommand(invalidCommand);

    assertEquals(expectedErrorMessage, imageView.getLastMessageDisplayed());
  }

  @Test
  public void testExecuteCommandWithInvalidCommand2() {
    String invalidCommand = "invalid-command aaaaaaaaaaaa  bbbbbbbbb  bbbbbbbbbbb";
    String expectedErrorMessage = "Unknown command: Operation is misspelled or doesn't exist.";

    imageController.executeCommand(invalidCommand);

    assertEquals(expectedErrorMessage, imageView.getLastMessageDisplayed());
  }


  @Test
  public void testExecuteCompressCommand() {
    String command = "compress 50 image1 vflip1";
    imageController.executeCommand(command);

    assertEquals("Input: compress:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteLevelAdjustCommand() {
    String command = "levels-adjust 10 140 250 image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: levelAdjustSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteColorCorrectCommand() {
    String command = "color-correct image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: colorCorrectSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteBlurSplitCommand() {
    String command = "blur image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: blurSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteSharpenSplitCommand() {
    String command = "sharpen image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: sharpenSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteSepiaSplitCommand() {
    String command = "sepia image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: sepiaSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteGrayscaleSplitCommand() {
    String command = "greyscale image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: grayscaleSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteLevelAdjustSplitCommand() {
    String command = "levels-adjust 10 140 250 image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: levelAdjustSplit:141", mockModel.log.toString());
  }

  @Test
  public void testExecuteColorCorrectSplitCommand() {
    String command = "color-correct image1 vflip1 split 50";
    imageController.executeCommand(command);

    assertEquals("Input: colorCorrectSplit:141", mockModel.log.toString());
  }
}