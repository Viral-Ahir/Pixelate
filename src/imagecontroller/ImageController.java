package imagecontroller;

/**
 * The CommandHandler interface defines the contract for handling script commands.
 */
public interface ImageController {

  /**
   * Handle the specified command with the given tokens.
   *
   * @param tokens An array of command tokens.
   */
  void executeCommand(String tokens);
}