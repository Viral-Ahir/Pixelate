package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for red method application call to model.
 */
public class HandleRedComponent extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleRedComponent object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleRedComponent(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid red-component command.");
      return;
    }
    String outputImageName = tokens[2];
    String imageName = tokens[1];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] redComponentArray = imageFilter.extractRedComponent(imageFromHashMap);
      imageMap.put(outputImageName, redComponentArray);
    }
  }
}
