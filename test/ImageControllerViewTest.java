import static org.junit.Assert.assertEquals;

import imagecontroller.ImageControllerImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test the controller using a mock view.
 */
public class ImageControllerViewTest {

  private ImageControllerImpl imageController;
  private MockImageView mockView;

  @Before
  public void setUp() {
    StringBuilder log = new StringBuilder();
    MockImageModel mockModel = new MockImageModel(log);
    mockView = new MockImageView(log);
    imageController = new ImageControllerImpl(mockModel, mockView);
  }

  @Test
  public void testHandleLoadButton() {
    imageController.handleLoadButton();
    assertEquals(
        "Handle Load Button\n"
            + "Generate Histogram View: hist1\n",
        mockView.log.toString()
    );
  }

  @Test
  public void testHandleSaveButton() {
    imageController.handleSaveButton();
    assertEquals("Handle Save Button\n", mockView.log.toString());
  }

  @Test
  public void testHandleImageProcessingButton() {
    String command = "compress";
    imageController.handleImageProcessingButton(command, false);
    assertEquals(
        "Error\n", mockView.log.toString());
  }

  @Test
  public void testHandleBrightenButton() {
    String brightenValue = "100";
    imageController.handleBrightenButton(brightenValue);
    assertEquals("Error\n",
        mockView.log.toString());
  }

  @Test
  public void testHandleCompressButton() {
    String compressValue = "50";
    imageController.handleCompressButton(compressValue);
    assertEquals("Error\n",
        mockView.log.toString());
  }

  @Test
  public void testHandleSplitButtonView() {
    imageController.handleSplitButton("compress", 50);
    assertEquals("Get Processed Image: split1\n"
            + "Set Photo\n"
            + "Generate Histogram View: hist1\n"
            + "Display Message: Split preview is ON, (Slide to 0 - OFF)\n",
        mockView.log.toString());
  }

  @Test
  public void testHandleEnterButtonView() {
    imageController.handleEnterButton("compress");
    assertEquals("Error\n",
        mockView.log.toString());
  }

  @Test
  public void testHandleLevelsAdjustButton() {
    String levelsValue = "someValue";
    imageController.handleLevelsAdjustButton(levelsValue);
    assertEquals("Error\n",
        mockView.log.toString());
  }

  @Test
  public void testHandleInvalidCommand() {
    String invalidCommand = "invalidCommand";
    imageController.handleImageProcessingButton(invalidCommand, false);
    assertEquals(
        "Error\n",
        mockView.log.toString()
    );
  }

}
