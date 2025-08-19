package imagecontroller.handlefilters;

import imagemodel.ExtendedImageModel;
import imagemodel.ImageConvert;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Class for save method application call to model.
 */
public class HandleSave extends HandleConstruct implements HandleInterfaceCommand {

  private final ImageConvert imageConvertor = new ImageConvert();

  /**
   * Constructs a HandleSave object with the specified ImageModel and image map.
   *
   * @param imageFilter The ImageModel to be used for processing.
   * @param imageMap    The image map containing image data.
   */
  public HandleSave(ExtendedImageModel imageFilter, Map<String, int[][][]> imageMap) {
    super(imageFilter, imageMap);
  }

  @Override
  public void apply(String[] tokens) {
    if (tokens.length != 3) {
      System.out.println("Invalid save command.");
      return;
    }
    String outputPath = tokens[1];
    String imageName = tokens[2];
    int[][][] imageToSave = imageMap.get(imageName);
    if (imageToSave == null) {
      System.out.println("No image present in storage for this image Name.");
    } else {
      BufferedImage imageBuf = imageConvertor.convertToBufferedImage(imageToSave);
      saveImage(imageBuf, outputPath);
    }
  }

  /**
   * Saves the provided BufferedImage to the specified output path, supporting JPG, PNG, and PPM
   * formats.
   *
   * @param image      The BufferedImage to be saved.
   * @param outputPath The path where the image should be saved.
   */
  private void saveImage(BufferedImage image, String outputPath) {
    try {
      if (outputPath.endsWith("jpg")) {
        ImageIO.write(image, "jpg", new File(outputPath));
      } else if (outputPath.endsWith("png")) {
        ImageIO.write(image, "png", new File(outputPath));
      } else if (outputPath.endsWith("ppm")) {
        if (isValidPPMPath(outputPath)) {
          int[][][] savePPM = imageConvertor.convertToArray(image);
          savePPM(savePPM, outputPath);
        } else {
          System.out.println("Invalid PPM file path: " + outputPath);
        }
      }
    } catch (IOException e) {
      e.printStackTrace(System.out);
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
    File parentDir = file.getParentFile();

    if (parentDir == null || !parentDir.exists()) {
      System.out.println("Parent directory does not exist: " + path);
      return false;
    }

    if (file.exists() && !file.isFile()) {
      System.out.println("Path is not a file: " + path);
      return false;
    }
    return true;
  }

  /**
   * Saves the provided pixel array in PPM format to the specified output path.
   *
   * @param pixelArray The pixel array to be saved.
   * @param outputPath The path where the PPM image should be saved.
   */
  private static void savePPM(int[][][] pixelArray, String outputPath) {
    int height = pixelArray.length;
    int width = pixelArray[0].length;

    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(outputPath));

      writer.println("P3");
      writer.println(width + " " + height);
      writer.println("255");

      for (int[][] ints : pixelArray) {
        for (int j = 0; j < width; j++) {
          int r = ints[j][0];
          int g = ints[j][1];
          int b = ints[j][2];
          writer.print(r + " " + g + " " + b + " ");
        }
        writer.println();
      }
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error saving the ppm image to " + outputPath);
    }
  }
}
