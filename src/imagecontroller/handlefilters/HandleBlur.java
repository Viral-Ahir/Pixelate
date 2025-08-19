package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * Class for blur method application call to model.
 */
public class HandleBlur extends HandleConstruct implements HandleInterfaceCommand {

  /**
   * Constructs a HandleBlur object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleBlur(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length < 3) {
      System.out.println("Invalid blur command.");
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
          if (splitPercentage < 0 || splitPercentage > 100) {
            throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
          }
        } catch (IllegalArgumentException e) {
          System.out.println("Error: " + e.getMessage());
          return;
        }
        int[][][] splitBlurredArray = imageFilter.splitBlurImage(imageFromHashMap,
            splitPercentage);
        imageMap.put(outputImageName, splitBlurredArray);
      } else {
        int[][][] blurredArray = imageFilter.blurImage(imageFromHashMap);
        imageMap.put(outputImageName, blurredArray);
      }
    }
  }
}
