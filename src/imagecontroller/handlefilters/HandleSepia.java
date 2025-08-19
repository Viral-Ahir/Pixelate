package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for sepia method application call to model.
 */
public class HandleSepia extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleSepia object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleSepia(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length < 3) {
      System.out.println("Invalid sepia command.");
      return;
    }
    String outputImageName = tokens[2];
    String imageName = tokens[1];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {

      if (tokens.length == 5 && tokens[3].equals("split")) {
        int splitPercentage;
        try {
          splitPercentage = Integer.parseInt(tokens[4]);
        } catch (NumberFormatException e) {
          System.out.println("Invalid percentage value for split.");
          return;
        }
        int[][][] splitBlurredArray = imageFilter.splitSepiaImage(imageFromHashMap,
            splitPercentage);
        imageMap.put(outputImageName, splitBlurredArray);
      } else {
        int[][][] sepiaArray = imageFilter.convertToSepia(imageFromHashMap);
        imageMap.put(outputImageName, sepiaArray);
      }
    }
  }
}
