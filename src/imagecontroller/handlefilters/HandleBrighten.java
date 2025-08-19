package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for bright method application call to model.
 */
public class HandleBrighten extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleBrighten object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleBrighten(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 4) {
      System.out.println("Invalid brighten command.");
      return;
    }
    int brightness = Integer.parseInt(tokens[1]);
    String outputImageName = tokens[3];
    String imageName = tokens[2];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] brightenedArrayImage = imageFilter.brightenImage(imageFromHashMap, brightness);
      imageMap.put(outputImageName, brightenedArrayImage);
    }
  }
}
