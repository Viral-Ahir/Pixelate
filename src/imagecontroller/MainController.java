package imagecontroller;

import imagemodel.ExtendedImageModel;
import imagemodel.ExtendedImageModelImpl;
import imageview.ImageViewImpl;
import java.util.Scanner;

/**
 * This class represents the main class, from where the program execution begins.
 */
public class MainController {

  /**
   * The main method of the controller, the execution begins from here.
   *
   * @param args arguments of type string[].
   */
  public static void main(String[] args) {

    ExtendedImageModel model = new ExtendedImageModelImpl();
    ImageViewImpl view = new ImageViewImpl();
    ImageControllerImpl controller = new ImageControllerImpl(model, view);

    if (args.length > 0 && args[0].equals("-file")) {
      if (args.length > 1) {
        view.setVisible(false);
        String scriptFilePath = args[1];
        controller.executeScriptFromFile(scriptFilePath);
        System.exit(0);
      } else {
        System.out.println("Missing script file path. Usage: -file name-of-script.txt");
        System.exit(0);
      }
    } else if (args.length > 0 && args[0].equals("-text")) {
      // Interactive mode
      view.setVisible(false);
      Scanner scanner = new Scanner(System.in);
      while (true) {
        System.out.println("Enter a command or 'exit' to quit");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
          System.exit(0);
          break;
        }
        controller.executeCommand(input);
      }
      scanner.close();
    } else {
      // Default case: GUI mode
      view.setVisible(true);
    }
  }
}

