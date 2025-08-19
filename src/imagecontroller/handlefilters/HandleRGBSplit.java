package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for split method application call to model.
 */
public class HandleRGBSplit extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleRGBSplit object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleRGBSplit(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 5) {
      System.out.println("Invalid rgb-split command.");
      return;
    }
    String imageName = tokens[1];
    String redImageNameSplit = tokens[2];
    String greenImageNameSplit = tokens[3];
    String blueImageNameSplit = tokens[4];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] redChannel = imageFilter.extractRedComponent(imageFromHashMap);
      int[][][] greenChannel = imageFilter.extractGreenComponent(imageFromHashMap);
      int[][][] blueChannel = imageFilter.extractBlueComponent(imageFromHashMap);
      imageMap.put(redImageNameSplit, redChannel);
      imageMap.put(greenImageNameSplit, greenChannel);
      imageMap.put(blueImageNameSplit, blueChannel);
    }
  }
}
