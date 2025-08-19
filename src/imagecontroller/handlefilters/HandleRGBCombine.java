package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;


/**
 * Class for combine method application call to model.
 */
public class HandleRGBCombine extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleRGBCombine object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleRGBCombine(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 5) {
      System.out.println("Invalid rgb-combine command.");
      return;
    }
    String redImageName = tokens[2];
    String greenImageName = tokens[3];
    String blueImageName = tokens[4];
    String outputImageName = tokens[1];

    int[][][] redImageFromHashMap = imageMap.get(redImageName);
    int[][][] greenImageFromHashMap = imageMap.get(greenImageName);
    int[][][] blueImageFromHashMap = imageMap.get(blueImageName);
    if (redImageFromHashMap == null || greenImageFromHashMap == null
        || blueImageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] combinedArrayImage = imageFilter.combineRGBImage(redImageFromHashMap,
          greenImageFromHashMap,
          blueImageFromHashMap);
      imageMap.put(outputImageName, combinedArrayImage);
    }
  }
}
