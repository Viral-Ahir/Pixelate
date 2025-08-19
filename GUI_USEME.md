# GUI for Image Processing Application (IME) using Swing!

Welcome to the Image Processing Application (IME)! This application provides a graphical user
interface for loading, processing, and saving images.

### Command-line Arguments

The program accepts the following three command-line arguments:

You can directly run the jar file provided in the resources directory in the following ways

By opening a command-prompt/terminal and navigating to that folder(resources). Then typing java -jar
NameOfJARFile.jar
and pressing ENTER. The example of it is given in the next section.

1. To run a script file:

   `java -jar Program.jar -file path-of-script-file`

   If a valid filepath is provided, the program should run the script and exit. If the program is
   run without any command line options, then it will allow interactive entry of script commands.

**Example:**
`java -jar assignment6.jar -file testScript.txt`

2. To run in interactive text mode:

   `java -jar Program.jar -text`

   When invoked in this manner, the program should open in an interactive text mode, allowing the
   user to type the script and execute it one line at a time. This is how the program worked in the
   last assignment.

**Example:**
`java -jar assignment6.jar -text`

3. To open the graphical user interface:

   `java -jar Program.jar`

   When invoked in this manner, the program should open the graphical user interface. This is what
   will happen if you simply double-click on the JAR file.

**Example:**
`java -jar Program.jar`

**Note:** Any other command-line arguments are invalid. In such cases, the program should display an
error message suitably and quit.

# Usage

## Loading an Image

1. Click on the "Load" button.
2. Select an image file (supported formats: JPG, PNG, PPM).
3. Click "Open" to load and display the selected image on the screen.

## Saving an Image

1. Click on the "Save" button.
2. Choose the destination folder and enter a file name.
3. Select a file format (JPG, PNG, PPM).
4. Click "Save" to save the image which is currently being shown on the screen.

## Image Processing Features

The UI Implements all the previous Image Processing Functions:

- **Red Component:** Display the red component in the image.
- **Green Component:** Display the green component in the image.
- **Blue Component:** Display the blue component in the image.
- **Horizontal Flip:** Flip the image horizontally.
- **Vertical Flip:** Flip the image vertically.
- **Brighten:** Adjust the brightness level of the image.
- **Blur:** Apply a blur effect to the image.
- **Sharpen:** Apply the sharpness of the image.
- **Sepia:** Apply a sepia tone to the image.
- **Greyscale:** Convert the image to greyscale.
- **Compress:** Compress the image.
- **Color Correct:** Perform color correction on the image.
- **Level Adjust:** Adjust black, mid, and white points in the image.

### Level Adjustment

1. Click on the "Level Adjust" button.
2. Enter values for black, mid, and white points.
3. Click "Enter" to apply the adjustment.

### Brightness Adjustment

1. Click on the "Brighten" button.
2. Enter a brightness level (1-100).
3. Click "Enter" to apply the adjustment.

### Compressing an Image

1. Click on the "Compress" button.
2. Enter a compression percentage (1-100).
3. Click "Enter" to apply compression.

### Histogram Display

- The histogram of the currently displayed image is shown on the right side of the application.
- A histogram in image processing shows the distribution of pixel intensities, indicating how many
  pixels have specific intensity values for each color channel (red, green, and blue).

## Split View Functionality

The Split View functionality allows you to view a split preview of the processed image. To use this
feature:

1. Adjust the Slider according to your choice from 0 to 100. This determines what percentage of the
   image user wants to be displayed split. (e.g., 50) means placing the line halfway
   through the width of the image.
2. Click on the "Split" button, typically located in the Messages interface.
3. The split preview will visually separate the original image and the processed image, making it
   easier to compare the changes.
4. Adjustments made through image processing features like color correction, level adjustment, or
   others can be observed in real-time through the split view.

Note: The split button and the slider for percentage will only be visible when the Image Processing
Function supports it.

## Changes and Justification Made to implement GUI:

The adherence to the MVC architecture has been maintained throughout the implementation, ensuring a
clean separation of concerns between the Model, View, and Controller. This structured design even
after changes not only enhances code organization but also promotes scalability, making it easier to
incorporate additional features in the future without disrupting existing functionality.

- We have not made any changes to any of the classes in model package to implement GUI.By doing so
  we have not changed any of the existing code in the model, and the support for all our previous
  functionality is retained as it is.

- In the controller we have added the `Features` interface, that represents various high-level
  features and abilities of our view. By creating this features interface we have ensured that the
  interface is more application specific, and it reflects exactly the responsibilities a
  controller has to uphold for this view, and also eliminates the need to use the action listener or
  key listener in the controller class. Apart from that this design also ensures that there is loose
  coupling between the view and controller.

- In the ImageView we have created a `addFeatures` method, that takes in this new `Features`
  interface.

- This design also ensures that view specific classes are avoided in the controller.

- We have made some changes in the `ImageControllerImpl`, to add the functionality of View. The main
  method is extracted out in the new class `MainController`. This was not a necessary requirement,
  but it increases the modularity,readability, and increases the separation of concerns within the
  controller package.

- Apart from that, now our `ImageControllerImpl`, implements `Features` interface to implement the
  view related functionality. In this process we have added new methods to the controller class, by
  following the open close principle. We have not changed any of our previous code in the
  controller, we have just added new methods in it to handle this additional view functionality.

- In the `ImageView` we have ensured that the design is easily extensible if needed in the future.
  We have only added view specific functionalities in this class.

## Application UI Preview
![](C:\Users\tanay\OneDrive\Desktop\Tanay\Java11Projects\Assignment6\resources\GUI_ScreenShot.png)