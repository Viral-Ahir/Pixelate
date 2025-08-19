package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import java.util.Map;

/**
 * A class the constructs object for all handle classes.
 */
public class HandleConstruct {

  protected final ExtendedImageModel imageFilter;
  protected final Map<String, int[][][]> imageMap;

  /**
   * Constructs a HandleConstruct object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleConstruct(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    this.imageFilter = imageFilter;
    this.imageMap = imageMap;
  }
}
