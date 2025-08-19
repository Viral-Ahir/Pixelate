package imagecontroller;

/**
 * Interface defining the features that an image controller should support.
 */
public interface Features {

  /**
   * Handles the action when the load button is pressed.
   */
  void handleLoadButton();

  /**
   * Handles the action when the save button is pressed.
   */
  void handleSaveButton();

  /**
   * Handles the image processing button with specified command and split support.
   *
   * @param command      The command for image processing.
   * @param supportSplit Indicates whether the operation supports splitting the image.
   */
  void handleImageProcessingButton(String command, Boolean supportSplit);

  /**
   * Handles the brighten button with a specified brightness level.
   *
   * @param brighten The brightness level for brightening the image.
   */
  void handleBrightenButton(String brighten);

  /**
   * Handles the compress button with a specified compression setting.
   *
   * @param compress The compression setting for image compression.
   */
  void handleCompressButton(String compress);

  /**
   * Handles the levels adjust button with a specified adjustment parameter.
   *
   * @param s The adjustment parameter for levels adjustment.
   */
  void handleLevelsAdjustButton(String s);

  /**
   * Handles the split button with a specified command.
   *
   * @param currentCommand      The command for splitting the image.
   * @param splitPercentageView The Split percentage for splitting image.
   */
  void handleSplitButton(String currentCommand, int splitPercentageView);

  /**
   * Handles the enter button with a specified command.
   *
   * @param currentCommand The command to be processed on enter button press.
   */
  void handleEnterButton(String currentCommand);
}