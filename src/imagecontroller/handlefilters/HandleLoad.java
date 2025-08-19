package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import imagemodel.ImageConvert;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Class for load method application call to model.
 */
public class HandleLoad extends HandleConstruct implements HandleInterfaceCommand {
  

  private final ImageConvert imageConvertor = new ImageConvert();

  /**
   * Constructs a HandleLoad object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleLoad(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid load command.");
      return;
    }
    String imagePath = tokens[1];
    String imageName = tokens[2];
    int[][][] image = loadImage(imagePath);

    imageMap.put(imageName, image);
  }

  /**
   * Loads an image from the specified file path, supporting JPG, PNG, and PPM formats.
   *
   * @param filePath The path to the image file.
   * @return A 3D array representing the loaded image, or null if an error occurs.
   */
  private int[][][] loadImage(String filePath) {
    int[][][] imagearray;
    try {

      if (filePath.endsWith("ppm")) {
        if (!isValidPPMPath(filePath)) {
          return null;
        }
        imagearray = readPPM(filePath);
      } else {
        BufferedImage image = ImageIO.read(new File(filePath));
        imagearray = imageConvertor.convertToArray(image);
      }
      return imagearray;
    } catch (IOException e) {
      e.printStackTrace(System.out);
      return null;
    }
  }

  /**
   * Checks if the given path is a valid PPM file path.
   *
   * @param path The path to be validated.
   * @return True if the path is valid, false otherwise.
   */
  private boolean isValidPPMPath(String path) {
    File file = new File(path);

    if (!file.exists() || !file.isFile()) {
      System.out.println("Invalid PPM file path: " + path);
      return false;
    }
    return true;
  }

  /**
   * Reads a PPM file from the specified file path.
   *
   * @param filepath The path to the PPM file.
   * @return A 3D array representing the pixels of the PPM image, or null if an error occurs.
   */
  private static int[][][] readPPM(String filepath) {
    Scanner sc;
    int[][][] pixelArray;

    try {
      sc = new Scanner(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filepath + " not found!");
      return null;
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    pixelArray = new int[height][width][3];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelArray[i][j][0] = r;
        pixelArray[i][j][1] = g;
        pixelArray[i][j][2] = b;
      }
    }
    return pixelArray;
  }
}
