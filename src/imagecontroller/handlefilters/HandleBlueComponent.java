package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for blue-component method application call to model.
 */
public class HandleBlueComponent extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleBlueComponent object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleBlueComponent(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid blue-component command.");
      return;
    }
    String outputImageName = tokens[2];
    String imageName = tokens[1];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] blueComponentArray = imageFilter.extractBlueComponent(imageFromHashMap);
      imageMap.put(outputImageName, blueComponentArray);
    }
  }
}
