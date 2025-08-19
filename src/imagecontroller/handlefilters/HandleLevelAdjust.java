package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for level-adjust method application call to model.
 */
public class HandleLevelAdjust extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleLevelAdjust object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleLevelAdjust(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length < 6) {
      System.out.println("Invalid levels-adjust command.");
      return;
    }
    int b = Integer.parseInt(tokens[1]);
    int m = Integer.parseInt(tokens[2]);
    int w = Integer.parseInt(tokens[3]);

    String imageName = tokens[4];
    String outputImageName = tokens[5];

    int[][][] imageFromHashMap = imageMap.get(imageName);
    if (imageFromHashMap == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {

      if (tokens.length == 8 && tokens[6].equals("split")) {
        int splitPercentage;
        try {
          splitPercentage = Integer.parseInt(tokens[7]);
        } catch (NumberFormatException e) {
          System.out.println("Invalid percentage value for split.");
          return;
        }
        int[][][] splitBlurredArray = imageFilter.splitLevelAdjustImage(imageFromHashMap, b, m, w,
            splitPercentage);
        imageMap.put(outputImageName, splitBlurredArray);
      } else {
        int[][][] adjustedImage = imageFilter.adjustLevel(imageFromHashMap, b, m, w);
        imageMap.put(outputImageName, adjustedImage);
      }
    }
  }
}
