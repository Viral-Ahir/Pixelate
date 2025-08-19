## Usage of the Image Processing Application

### --- Compilation ---

Before running the program, you need to compile all the Java files in your project, including the
classes and interfaces mentioned above.

`OR`

No need of compilation if you wish to run the program using the provided jar file in the resources'
directory.

### --- Running the Program ---

To run the program and perform image processing operations:
Execute the main method in the ImageControllerImpl class.
The program will start, and you can interact with it through the console, or you can directly run an
external script file having set of commands to execute.

`OR`

You can directly run the jar file provided in the resources directory in the following ways

By opening a command-prompt/terminal and navigating to that folder(resources). Then typing java -jar
   NameOfJARFile.jar
   and pressing ENTER. The example of it is given in the next section.


### --- To Run An External Script File ---

> run script-file-path

Example - `run resources\scripts\compressCat.txt`

-- Load and run the script commands in the specified file. The path can be relative or absolute.

-- For example to run scriptresults.txt file present in the root directory,
we need to write
**run scriptresults.txt**. This will execute the commands in the file.

**Command-Line Options**

To accept a script file as a command-line option, use the following syntax:

java imgControllerImpl -file path-of-script.txt (You should be in the correct directory and you
should give correct file path to run this
command, and the application code should be complied properly before)
-- If a valid filepath is provided, the program should run the script and exit. If the program is
run without any command line options, then it will allow interactive entry of script commands.

`OR`

By opening a command-prompt/terminal and navigating to the directory(resources in our case) that has
jar file. Then typing
> java -jar NameOfJARFile.jar -file path-of-script.txt

and pressing ENTER.

**RUN THIS FOR ALL IMAGE OPERATIONS** -

The results will be stored in the testScriptsResults directory
present in the resources/assets directory.

`java -jar assignment5.jar -file testScript.txt`


### --- Input Commands ---

You can enter commands directly into the program by typing them in the console. For example, you can
use commands like load, save, red-component, brighten, and more to process images.

**The valid format of commands is as follows**

> load image-path image-name

Example - `load resources\assets\dp.jpg image1`

-- Load an image from the specified path and refer it to henceforth in the
program by the given image name.


> save image-path image-name

Example - `save resources\assets\results\Red.jpg redimage1`

-- Save the image with the given name to the specified path which should
include the name of the file.


> red-component image-name dest-image-name

Example - `red-component image1 redimage1`

-- Create an image with the red-component of the image with
the given name, and refer to it henceforth in the program by the given destination name. Similar
commands for green, blue, value, luma, intensity components should be supported. Note that the
images for value, luma and intensity will be greyscale images.


> horizontal-flip image-name dest-image-name

Example - `horizontal-flip image1 horizontalimage1`

-- Flip an image horizontally to create a new image,
referred to henceforth by the given destination name.


> vertical-flip image-name dest-image-name

Example - `vertical-flip image1 verticalimage1`

-- Flip an image vertically to create a new image, referred
to henceforth by the given destination name.


> brighten increment image-name dest-image-name

Example - `brighten 100 image1 brightimage1`

-- brighten the image by the given increment to create a
new image, referred to henceforth by the given destination name. The increment may be positive (
brightening) or negative (darkening).


> rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue

Example - `rgb-split image1 Rimage1 Gimage1 Bimage1`

-- split the given
image into three images containing its red, green and blue components respectively. These would be
the same images that would be individually produced with the red-component, green-component and
blue-component commands.


> rgb-combine image-name red-image green-image blue-image

Example - `rgb-combine outputimage1 Rimage1 Gimage1 Bimage1`

-- Combine the three greyscale images into a
single image that gets its red, green and blue components from the three images respectively.


> blur image-name dest-image-name

Example - `blur image1 blurimage1`

-- blur the given image and store the result in another image with the
given name.


> sharpen image-name dest-image-name

Example - `sharpen image1 sharpenimage1`

-- sharpen the given image and store the result in another image
with the given name.


> sepia image-name dest-image-name

Example - `sepia image1 sepiaimage1`

-- produce a sepia-toned version of the given image and store the
result in another image with the given name.

> compress percentage image-name dest-image-name

Example - `compress 90 image1 compress90`

-- Create a compressed version of the given image with the specified percentage and store the result
in another image with the given name.

> histogram image-name dest-image-name

Example - `histogram compress90 hist3`

-- Generate an image representing the histogram of the given image. The size of this image will be
256x256 and will contain histograms for the red, green, and blue channels as line graphs.

> color-correct image-name dest-image-name

Example - `color-correct image1 dest3`

-- Color-correct an image by aligning the meaningful peaks of its histogram and store the result in
another image with the given name.

> levels-adjust b m w image-name dest-image-name

Example - `levels-adjust 10 100 150 image1 dest2`

-- Adjust the levels of an image using the specified black, mid, and white values, and store the
result in another image with the given name. The These values should be ascending in that order, and
should be within 0 and 255 for this command to work correctly.

> operation image-name dest-image-name split p

Example - `blur image1 dest1 split 50`

-- Perform a specific image processing operation with an optional parameter for the placement of the
splitting line. The output image should show only the relevant part suitably transformed, with the
original image in the remaining part. The operations that support this are blur, sharpen, sepia,
greyscale, color correction and levels adjustment. For ex - for blur image the command will be "blur
image-name dest-image-name" or "blur image-name dest-image split p" in that order where 'p' is a
percentage of the width (e.g. 50 means place the line halfway through the width of the image)

## --- General Conditions to keep in mind when using commands ---

1. Image processing commands should generally be used in a logical order. For example, loading an
   image (load) before applying operations (red-component, compress, save etc.).
2. The commands should be typed in the exact format as provided above, for successful execution, or
   else the program will throw error.
