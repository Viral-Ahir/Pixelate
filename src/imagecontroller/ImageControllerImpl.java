package imagecontroller;

import imagecontroller.handlefilters.HandleBlueComponent;
import imagecontroller.handlefilters.HandleBlur;
import imagecontroller.handlefilters.HandleBrighten;
import imagecontroller.handlefilters.HandleColorCorrection;
import imagecontroller.handlefilters.HandleCompress;
import imagecontroller.handlefilters.HandleGreenComponent;
import imagecontroller.handlefilters.HandleHistogram;
import imagecontroller.handlefilters.HandleHorizontalFlip;
import imagecontroller.handlefilters.HandleIntensityComponent;
import imagecontroller.handlefilters.HandleLevelAdjust;
import imagecontroller.handlefilters.HandleLoad;
import imagecontroller.handlefilters.HandleLumaComponent;
import imagecontroller.handlefilters.HandleRGBCombine;
import imagecontroller.handlefilters.HandleRGBSplit;
import imagecontroller.handlefilters.HandleRedComponent;
import imagecontroller.handlefilters.HandleSave;
import imagecontroller.handlefilters.HandleSepia;
import imagecontroller.handlefilters.HandleSharpen;
import imagecontroller.handlefilters.HandleValueComponent;
import imagecontroller.handlefilters.HandleVerticalFlip;
import imagemodel.ExtendedImageModel;
import imageview.ImageView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for Controlling the I/Os and assigning tasks to model and view. It implements the
 * ImageController interface.
 */
public class ImageControllerImpl implements ImageController, Features {

  private final ExtendedImageModel imageFilter;
  private final ImageView imageView;
  private final Map<String, CommandHandler> commandHandlers;
  private final Map<String, int[][][]> imageMap;

  private String[] currBMWValues;

  /**
   * Constructor for our Controller class.
   *
   * @param model the model which is initialized by controller
   * @param view  the view which is initialized by controller
   */
  public ImageControllerImpl(ExtendedImageModel model, ImageView view) {
    imageFilter = model;
    imageView = view;
    imageView.addFeatures(this);
    commandHandlers = new HashMap<>();
    imageMap = new HashMap<>();
    initializeCommandHandlers();
  }

  /**
   * Method to initialize and assign the correct operations to corresponding actions, accessed via
   * commandHandlers hashMap.
   */
  private void initializeCommandHandlers() {
    commandHandlers.put("load", new HandleLoad(imageFilter, imageMap)::apply);
    commandHandlers.put("save", new HandleSave(imageFilter, imageMap)::apply);
    commandHandlers.put("red-component", new HandleRedComponent(imageFilter, imageMap)::apply);
    commandHandlers.put("green-component", new HandleGreenComponent(imageFilter, imageMap)::apply);
    commandHandlers.put("blue-component", new HandleBlueComponent(imageFilter, imageMap)::apply);
    commandHandlers.put("horizontal-flip", new HandleHorizontalFlip(imageFilter, imageMap)::apply);
    commandHandlers.put("vertical-flip", new HandleVerticalFlip(imageFilter, imageMap)::apply);
    commandHandlers.put("brighten", new HandleBrighten(imageFilter, imageMap)::apply);
    commandHandlers.put("rgb-split", new HandleRGBSplit(imageFilter, imageMap)::apply);
    commandHandlers.put("rgb-combine", new HandleRGBCombine(imageFilter, imageMap)::apply);
    commandHandlers.put("blur", new HandleBlur(imageFilter, imageMap)::apply);
    commandHandlers.put("sharpen", new HandleSharpen(imageFilter, imageMap)::apply);
    commandHandlers.put("sepia", new HandleSepia(imageFilter, imageMap)::apply);
    commandHandlers.put("run", this::handleRunScriptFromFile);
    commandHandlers.put("luma-component", new HandleLumaComponent(imageFilter, imageMap)::apply);
    commandHandlers.put("greyscale", new HandleLumaComponent(imageFilter, imageMap)::apply);
    commandHandlers.put("value-component", new HandleValueComponent(imageFilter, imageMap)::apply);
    commandHandlers.put("compress", new HandleCompress(imageFilter, imageMap)::apply);
    commandHandlers.put("histogram", new HandleHistogram(imageFilter, imageMap)::apply);
    commandHandlers.put("color-correct", new HandleColorCorrection(imageFilter, imageMap)::apply);
    commandHandlers.put("levels-adjust", new HandleLevelAdjust(imageFilter, imageMap)::apply);
    commandHandlers.put("intensity-component",
        new HandleIntensityComponent(imageFilter, imageMap)::apply);
  }

  /**
   * This is a private interface to handle Commands by user.
   */
  private interface CommandHandler {

    /**
     * Method to handle the input string of tokens.
     *
     * @param tokens it is the tokenized form of the input command.
     */
    void handleCommand(String[] tokens);
  }

  @Override
  public void executeCommand(String command) {
    String[] tokens = command.split(" ");
    if (!tokens[0].startsWith("#")) {
      if (tokens.length < 2) {
        imageView.displayMessage(
            "Invalid command: The length of token is less then 2 or in is just a just blank line.");
        return;
      }
      String operation = tokens[0];

      CommandHandler handler = commandHandlers.get(operation);
      if (handler != null) {
        handler.handleCommand(tokens);
      } else {
        imageView.displayMessage("Unknown command: Operation is misspelled or doesn't exist.");
      }
    }
  }

  /**
   * This is a method to run script from text file.
   *
   * @param tokens parsed tokens from the text file.
   */
  private void handleRunScriptFromFile(String[] tokens) {
    if (tokens.length != 2) {
      imageView.displayMessage("Invalid command: less than 2 tokens given.");
      return;
    }
    String scriptFile = tokens[1];
    executeScriptFromFile(scriptFile);
  }

  /**
   * A helper method to execute the commands of script file.
   *
   * @param scriptFile the file provided as a script file.
   */
  protected void executeScriptFromFile(String scriptFile) {
    try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        executeCommand(line);
      }
    } catch (IOException e) {
      imageView.displayMessage("Error reading the script file:");
      String error = e.getMessage();
      imageView.displayMessage(error);
    }
  }

  @Override
  public void handleLoadButton() {
    String filepath = imageView.handleLoadButton();
    String input = "load " + filepath + " image1";
    executeCommand(input);
    generateHistogram("image1", imageMap);
  }

  @Override
  public void handleSaveButton() {
    String saveFilePath = imageView.handleSaveButton();
    String input = "save " + saveFilePath + " image1";
    executeCommand(input);
  }

  /**
   * Checks if an image is loaded or not.
   *
   * @return returns True is loaded, False if not.
   */
  private Boolean checkImageLoaded() {
    if (imageMap.get("image1") == null) {
      imageView.throwError("Please load the image first");
      return false;
    }
    return true;
  }

  @Override
  public void handleImageProcessingButton(String command, Boolean supportSplit) {
    if ((checkImageLoaded())) {
      imageView.handleImageProcessingButtonView(command, supportSplit);
      String input = command + " image1 image1";
      imageMap.put("prevImage", imageMap.get("image1"));
      executeCommand(input);
      imageView.setPhoto(imageView.getProcessedImage("image1", imageMap));
      generateHistogram("image1", imageMap);
      imageView.displayMessage("Displaying " + command);
    }
  }

  @Override
  public void handleBrightenButton(String brighten) {
    if ((checkImageLoaded())) {
      String inputBrightness = imageView.handleBrightenButtonView(brighten);
      if (inputBrightness.isEmpty()) {
        imageView.displayMessage("Please enter a brightness level.");
        return;
      }
      try {
        int brightnessLevel = Integer.parseInt(inputBrightness);
        if (brightnessLevel < 1 || brightnessLevel > 100) {
          imageView.throwError("Brightness level should be between 1 and 100.");
          return;
        }
        abstractCompressBrighten(brighten, brightnessLevel);
        imageView.displayMessage("Displaying Brighten (Level: " + brightnessLevel + ")");
      } catch (NumberFormatException ex) {
        imageView.throwError(
            "Invalid brightness level. Please enter a number between 1 and 100.");
      }
    }
  }

  @Override
  public void handleCompressButton(String compress) {
    if ((checkImageLoaded())) {
      String compressPercentage = imageView.handleCompressButtonView(compress);
      if (compressPercentage.isEmpty()) {
        imageView.displayMessage("Please enter Compression %.");
        return;
      }

      try {
        int compressLevel = Integer.parseInt(compressPercentage);
        if (compressLevel < 1 || compressLevel > 100) {
          imageView.throwError("Compression percentage must be between 0 and 100.");
          return;
        }
        abstractCompressBrighten(compress, compressLevel);
        imageView.displayMessage("Displaying Compress (Level: " + compressLevel + ")");
      } catch (NumberFormatException ex) {
        imageView.throwError(
            "Invalid Compression percentage. Please enter a number between 1 and 100.");
      }
    }
  }

  /**
   * Private method for common code in handleBrightenButton and handleCompressButton Method.
   *
   * @param operationName either compress or brighten.
   * @param level         the compress or brighten value.
   */
  private void abstractCompressBrighten(String operationName, int level) {
    imageMap.put("prevImage", imageMap.get("image1"));
    String input = operationName + " " + level + " image1 image1";
    executeCommand(input);

    String image1 = "image1";
    imageView.setPhoto(imageView.getProcessedImage(image1, imageMap));
    generateHistogram(image1, imageMap);
  }

  @Override
  public void handleLevelsAdjustButton(String levelAdjust) {
    if ((checkImageLoaded())) {
      String[] bmwArray = imageView.handleLevelsAdjustButtonView(levelAdjust);
      currBMWValues = bmwArray;
      String bInput = bmwArray[0];
      String mInput = bmwArray[1];
      String wInput = bmwArray[2];
      if (bInput.isEmpty() || mInput.isEmpty() || wInput.isEmpty()) {
        imageView.displayMessage("Enter Black, Mid, White values here:");
        return;
      }

      try {
        int b = Integer.parseInt(bInput);
        int m = Integer.parseInt(mInput);
        int w = Integer.parseInt(wInput);

        if (!(b >= 0 && b <= m && m <= w && w <= 255)) {
          imageView.throwError(
              "Values b, m, and w must be in ascending order and within the range of 0 to 255.");
          return;
        }
        imageMap.put("prevImage", imageMap.get("image1"));
        String input = levelAdjust + " " + b + " " + m + " " + w + " image1 image1";
        executeCommand(input);
        String image1 = "image1";
        imageView.setPhoto(imageView.getProcessedImage(image1, imageMap));
        generateHistogram(image1, imageMap);
        imageView.displayMessage(
            "Displaying Levels Adjust (b: " + b + ", m: " + m + ", w: " + w + ")");
      } catch (NumberFormatException ex) {
        imageView.throwError("Invalid input. Please enter valid integer values.");
      }
    }
  }

  @Override
  public void handleSplitButton(String currentCommand, int splitPercentageView) {
    String command;
    if ("levels-adjust".equals(currentCommand)) {
      command =
          currentCommand + " " + currBMWValues[0] + " " + currBMWValues[1] + " " + currBMWValues[2]
              + " prevImage split1 split " + splitPercentageView;
    } else {
      command = currentCommand + " prevImage split1 split " + splitPercentageView;
    }
    executeCommand(command);
    String splitImage = "split1";
    imageView.setPhoto(imageView.getProcessedImage(splitImage, imageMap));
    generateHistogram(splitImage, imageMap);
    imageView.displayMessage("Split preview is ON, (Slide to 0 - OFF)");
  }

  @Override
  public void handleEnterButton(String currentCommand) {
    if ("levels-adjust".equals(currentCommand)) {
      handleLevelsAdjustButton(currentCommand);
    }
    if ("compress".equals(currentCommand)) {
      handleCompressButton(currentCommand);
    }
    if ("brighten".equals(currentCommand)) {
      handleBrightenButton(currentCommand);
    }
  }

  /**
   * Method to generate Histogram of the image displayed to the user.
   *
   * @param hist     Image Name to be used for histogram.
   * @param imageMap Map where the image is stored.
   */
  private void generateHistogram(String hist, Map<String, int[][][]> imageMap) {
    String histInput = "histogram " + hist + " hist1";
    executeCommand(histInput);
    imageView.generateHistogramView("hist1", imageMap);
  }
}