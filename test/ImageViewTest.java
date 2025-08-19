import static org.junit.Assert.assertEquals;

import imageview.ImageViewImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test the view in MVC.
 */
public class ImageViewTest {

  private imageview.ImageViewImpl imageView;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    imageView = new ImageViewImpl();
  }

  @Test
  public void testDisplayMessage() {
    String expectedOutput = "This is a test message";

    imageView.displayMessage(expectedOutput);

    assertEquals(expectedOutput + System.lineSeparator(), outContent.toString());
  }

  @Test
  public void testDisplayMessageWithMultipleLines() {
    String message = "Line 1" + System.lineSeparator()
        + "Line 2" + System.lineSeparator()
        + "Line 3";

    imageView.displayMessage(message);

    assertEquals(message + System.lineSeparator(), outContent.toString());
  }
}