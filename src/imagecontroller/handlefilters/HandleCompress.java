package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for compress method application call to model.
 */
public class HandleCompress extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleCompress object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleCompress(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 4) {
      System.out.println("Invalid compress command.");
      return;
    }
    int percentage = Integer.parseInt(tokens[1]);

    String outputImageName = tokens[3];
    String imageName = tokens[2];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      int[][][] compressedArray = imageFilter.compress(imageFromHashMap, percentage);
      imageMap.put(outputImageName, compressedArray);
    }
  }
}