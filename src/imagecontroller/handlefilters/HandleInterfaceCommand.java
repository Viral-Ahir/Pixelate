package imagecontroller.handlefilters;

/**
 * Interface for all HandleMethods.
 */
public interface HandleInterfaceCommand {

  /**
   * Apply method for applying specific operations.
   *
   * @param tokens command passes as tokens.
   */
  void apply(String[] tokens);
}
