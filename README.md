# Image Processing Program

## --- Overview ---

This image processing program is designed to perform various operations on images of several
formats. It consists of
several classes and interfaces that work together to load, process, and save images. Below is an
overview of the key components of the program:

## --- Classes and Interfaces ---

1.**imagecontroller**: This interface defines the contract for handling script commands. It provides
a
method executeCommand to execute commands based on user input. It is in the main controller file of
the program.

2.**ImageControllerImpl**: This class implements the imagecontroller interface and serves as the
main
controller for processing image commands. It initializes image models and views and handles user
commands.

3.**imagemodel**: This interface defines image processing operations, such as extracting color
components,
flipping, and applying filters. It provides methods for some of these operations.

4.**ImageModelImpl**: This class implements the imagemodel interface and provides actual
implementations
of some image processing operations.

5.**ImageConvert**: This class provides methods for converting images between 3D integer arrays and
BufferedImage objects.

6.**imageview**: This interface defines the contract for displaying messages to the user.

7.**ImageViewImpl**: This class implements the imageview interface and displays messages to the
console
using the System.out stream.

8.**HandleFilters package**: This package contains classes that handles the implement specific image
processing
operations (e.g., extracting color components, flipping, applying filters).

9.**ExtendedImageModel Interface**: This interface defines some additional image processing
operations such as Split view support, compress, etc.

10.**ExtendedImageModelImpl Class**: This class implements the ExtendedImageModel interface and
provides actual
implementations of additional functionalities support of image processing operations.

## --- Image Source ---

The image **dp.jpg** present inside the resources/assets directory of this project is my own image,
and I am the owner of it.

The image **cat24bit.png** present inside the resources/assets directory of this project is
downloaded image,
from https://depositphotos.com/photos/small-cat.html.

The image **leaf24bit.png** present inside the resources/assets directory of this project is
downloaded image,
from https://en.m.wikipedia.org/wiki/File:24_bit.png.

I authorize all the users running this program to use it for the purposes related to this program
only.

## --- Changes made to implement new features ---

### -- General Description of our changes and design justifications. --

The adherence to the MVC architecture has been maintained throughout the implementation, ensuring a
clean separation of concerns between the Model, View, and Controller. This structured design even
after changes not only enhances code organization but also promotes scalability, making it easier to
incorporate additional features in the future without disrupting existing functionality.

We have not made any changes in our original model interface `ImageModel` and its
implementation `ImageModelImpl`.Similarly, the `ImageView` interface and its
implementation `ImageViewImpl` is unmodified.

To implement the new functionality, we have created a new `ExtendedImageModel` interface that
extends our `ImageModel`. And we have also created its implementation `ExtendedImageModelImpl`
class, that extends `ImageModelImpl`. By doing so we have not changed any of the existing code in
the model, and the support for all our previous functionality is retained as it is.

And as we used command design pattern in the controller, so adding features was easy for us without
any modification
in existing code. To add the support for new commands, we have added the new commands in
the `ComandHandler` hash map present in the `ImageControllerImpl` class.

Moreover, we are now passing ExtendedImageModel object inplace
of ImageModel object while constructing controller object.
so that the controller can handle all the new functionalities as well as the old
functionalities.

### -- Compress --

- **Description**: Create a compressed version of the given image with the specified percentage and
  store the result in another image with the given name.
- **Changes Made**: Added a new `HandleCompress` class in the `handleFilters` package in the
  controller. This class
  calls the `compress` method of the extended model. The `compress` method in the model uses several
  private methods in `ExtendedImageModelImpl`. No modifications were made in the existing code. Code
  was easily integrated in the existing design.

### -- Histogram --

- **Description**: Generate an image representing the histogram of the given image. The size of this
  image will be 256x256 and will contain histograms for the red, green, and blue channels as line
  graphs.
- **Changes Made**: Added a new `HandleHistogram` class in the `handleFilters` package in the
  controller. This class
  calls the `generateHistogram` method of the extended model. The `generateHistogram` method in the
  model
  uses `generateComponentHistogram` in `ExtendedImageModelImpl`. No modifications were made in the
  existing
  code. Code was easily integrated in the existing design.

### -- Color Correct --

- **Description**: Color-correct an image by aligning the meaningful peaks of its histogram and
  store the result in another image with the given name.
- **Changes Made**: Added a new `HandleColorCorrection` class in the `handleFilters` package in the
  controller. This
  class calls the `colorCorrect` method of the extended model. The `colorCorrect` method in the
  model uses
  the private method `findMeaningFullPeaks` in `ExtendedImageModelImpl`. No modifications were made
  in the
  existing code.Code was easily integrated in the
  existing design.

### -- Level Adjust --

- **Description**: Adjust the levels of an image using the specified black, mid, and white values,
  and store the result in another image with the given name. These values should be ascending in
  that order and should be within 0 and 255 for this command to work correctly.
- **Changes Made**: Added a new `HandleLevelAdjust` class in the `handleFilters` package in the
  controller. This class
  calls the `adjustLevel` method of the model. The `adjustLevel` method in the model uses the
  private method `applyLevelAdjustment` in `ExtendedImageModelImpl`. No modifications were made in
  the
  existing
  code. Code was easily integrated in the existing
  design.

### -- Split Parameter Support --

No modifications have been made in the command handler map that stores all the commands. The split
parameter is treated as optional for several image manipulation commands, preserving the
extensibility of the command handling mechanism.

#### Implementation Details

To support split functionality for specific image manipulation commands, conditional statements have
been strategically added in the relevant `handlefilters` classes within the controller. These
conditional statements check for the presence of an optional split argument. If the split parameter
is present, the corresponding split method from the Extended model is invoked, if not then the
regular
operation is performed.

#### Introduction of ExtendedImageModelImpl

A new class, `ExtendedImageModelImpl`, has been introduced to implement the `ExtendedImageModel`
interface. This class serves as an extension to the existing `ImageModelImpl`, catering to the
specific requirements of split operations.

#### Model-Level Changes

No changes are made in the already existing `ImageModelImpl`, new split methods have been
incorporated for each split
operation in the `ExtendedImageImpl` for the specific image manipulation operation to be performed.

### Justification of changes made for Split Parameter Support

#### 1. Minimizing Code changes.

The decision to implement the split command as separate methods in the `ExtendedImageImpl` class
ensures
that the core functionalities of the original `ImageModelImpl` remain unaltered. This design choice
adheres to the principle of minimizing code changes in existing, well-established components.

#### 2. Maintaining Modularity

The addition of conditional statements in the `handleFilters` classes enables the program to
selectively invoke split functionality. This modular approach allows for efficient handling of split
operations in specific scenarios, without affecting the previous functionality.

### -- Script file as a command-line option support --

To run script file as command-line option, we have added an if statement within the main method of
the
ImageControllerImpl to address it. The newly added code does not entail any alterations to the
pre-existing codebase, ensuring the preservation of our prior functionality. The logic for executing
the script file was already present in the code, so inside the if statement, we are just calling
and reusing the `executeScriptFromFile`private method in the controller. 