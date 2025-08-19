package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for green-component method application call to model.
 */
public class HandleGreenComponent extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleGreenComponent object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleGreenComponent(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid green-component command.");
      return;
    }
    String outputImageName = tokens[2];
    String imageName = tokens[1];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] greenComponentArray = imageFilter.extractGreenComponent(imageFromHashMap);
      imageMap.put(outputImageName, greenComponentArray);
    }
  }

}
