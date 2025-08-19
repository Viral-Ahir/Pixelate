import imagecontroller.Features;
import imageview.ImageView;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Class that represents mock View for testing.
 */
public class MockImageView implements ImageView {

  final StringBuilder log;

  public MockImageView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void displayMessage(String message) {
    log.append("Display Message: ").append(message).append("\n");
  }

  @Override
  public void setPhoto(Image image) {
    log.append("Set Photo\n");
  }

  @Override
  public void generateHistogramView(String hist, Map<String, int[][][]> imageMap) {
    log.append("Generate Histogram View: ").append(hist).append("\n");
  }

  @Override
  public void setHistogram(Image image) {
    log.append("Set Histogram\n");
  }

  @Override
  public void addFeatures(Features features) {
    //void method, nothing to return or append.
  }

  @Override
  public void throwError(String error) {
    log.append("Error\n");
  }

  @Override
  public String handleBrightenButtonView(String brighten) {
    log.append("Handle Brighten Button View\n");
    return "mockBrightenResult";
  }

  @Override
  public String handleCompressButtonView(String compress) {
    log.append("Handle Compress Button View\n");
    return "mockCompressResult";
  }

  @Override
  public String[] handleLevelsAdjustButtonView(String levelAdjust) {
    log.append("Handle Levels Adjust Button View\n");
    return new String[]{"mockLevel1", "mockLevel2", "mockLevel3"};
  }

  @Override
  public String handleLoadButton() {
    log.append("Handle Load Button\n");
    return "mockLoadResult";
  }

  @Override
  public String handleSaveButton() {
    log.append("Handle Save Button\n");
    return "mockSaveResult";
  }

  @Override
  public BufferedImage createBufferedImage(int[][][] processedImageArray) {
    log.append("Create Buffered Image\n");
    return new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public Image getProcessedImage(String image1, Map<String, int[][][]> imageMap) {
    log.append("Get Processed Image: ").append(image1).append("\n");
    return null;
  }

  @Override
  public void handleImageProcessingButtonView(String command, Boolean supportSplit) {
    log.append("Handle Image Processing Button View\n");
  }

  @Override
  public String[] getBMWValues() {
    log.append("get B M W values\n");
    return new String[0];
  }
}
