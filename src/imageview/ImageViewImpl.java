package imageview;

import imagecontroller.Features;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class representing the View of MVC, outputs messages to the user.
 */
public class ImageViewImpl extends JFrame implements ImageView {

  private final JLabel photoLabel;
  private final JLabel photoLabel2;
  private final PrintStream outputStream;
  private String lastMessageDisplayed;
  private final JButton loadButton;
  private final JButton saveButton;
  private final JButton redButton;
  private final JButton blueButton;
  private final JButton greenButton;
  private final JButton horizontalFlip;
  private final JButton verticalFlip;
  private final JButton brighten;
  private final JButton blur;
  private final JButton sharpen;
  private final JButton sepia;
  private final JButton greyScale;
  private final JButton compress;
  private final JButton colorCorrect;
  private final JButton levelAdjust;
  private final JButton split;
  private final JButton enterButton;
  private final JSlider sliderSplit;
  private final JTextField inputField;
  private final JTextField blackPointField;
  private final JTextField midPointField;
  private final JTextField whitePointField;
  private final JScrollPane imagePanel;
  private final JScrollPane histogramPanel;
  private final JLabel messageLabel;
  private int splitPercentageView;
  private String currentCommand = "null";
  private boolean isImageSaved = false;
  private final JSplitPane mainPanel;

  /**
   * Constructor for the View class, having outStream for printing.
   */
  public ImageViewImpl() {

    super("IME - Image Processing Application");
    // Setting main Frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1500, 800));
    setMinimumSize(new Dimension(1200, 800));
    setResizable(true);
    setVisible(true);

    // Setting Main Split Panel
    mainPanel = new JSplitPane();
    JPanel leftSubPanel = new JPanel();
    leftSubPanel.setLayout(new BoxLayout(leftSubPanel, BoxLayout.Y_AXIS));

    JPanel loadSavePanel = new JPanel(new GridLayout(1, 0, 5, 5));
    loadSavePanel.setMaximumSize(new Dimension(1500, 300));
    JPanel featurePanel = new JPanel(new GridLayout(13, 0, 5, 5));
    JSplitPane rightSubPanel = new JSplitPane();
    JPanel userEntryPanel = new JPanel();
    messageLabel = new JLabel("WELCOME!");
    messageLabel.setFont(new Font("Cambria", Font.BOLD, 20));
    userEntryPanel.add(messageLabel);

    leftSubPanel.add(loadSavePanel);
    leftSubPanel.add(featurePanel);
    leftSubPanel.add(userEntryPanel);

    // Adding buttons to the loadSavePanel
    loadSavePanel.setBorder(BorderFactory.createTitledBorder("Load / Save Image"));
    loadButton = createButton("Load", "load_button");
    loadSavePanel.add(loadButton);
    saveButton = createButton("Save", "save_button");
    loadSavePanel.add(saveButton);

    // Adding buttons to the featuresPanel
    featurePanel.setBorder(BorderFactory.createTitledBorder("Features"));
    userEntryPanel.setBorder(BorderFactory.createTitledBorder("Messages"));

    redButton = createButton("Red Component", "red-component");
    featurePanel.add(redButton);

    blueButton = createButton("Blue Component", "blue-component");
    featurePanel.add(blueButton);

    greenButton = createButton("Green Component", "green-component");
    featurePanel.add(greenButton);

    horizontalFlip = createButton("Horizontal Flip", "horizontal-flip");
    featurePanel.add(horizontalFlip);

    verticalFlip = createButton("Vertical Flip", "vertical-flip");
    featurePanel.add(verticalFlip);

    brighten = createButton("Brighten", "brighten");
    featurePanel.add(brighten);

    blur = createButton("Blur", "blur");
    featurePanel.add(blur);

    sharpen = createButton("Sharpen", "sharpen");
    featurePanel.add(sharpen);

    sepia = createButton("Sepia", "sepia");
    featurePanel.add(sepia);

    greyScale = createButton("Greyscale", "greyscale");
    featurePanel.add(greyScale);

    compress = createButton("Compress", "compress");
    featurePanel.add(compress);

    colorCorrect = createButton("Color Correct", "color-correct");
    featurePanel.add(colorCorrect);

    levelAdjust = createButton("Level Adjust", "levels-adjust");
    featurePanel.add(levelAdjust);

    split = createButton("Split", "split_view");
    split.setVisible(false);

    enterButton = createButton("Enter", "enter");
    enterButton.setVisible(false);

    getContentPane().add(mainPanel);
    getContentPane().setLayout(new GridLayout());

    mainPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    mainPanel.setDividerLocation(300);
    mainPanel.setLeftComponent(leftSubPanel);
    mainPanel.setRightComponent(rightSubPanel);
    mainPanel.setContinuousLayout(true);

    photoLabel = new JLabel();
    photoLabel2 = new JLabel();
    histogramPanel = new JScrollPane();
    imagePanel = new JScrollPane();
    photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    photoLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    imagePanel.setPreferredSize(new Dimension(600, 800));
    imagePanel.setViewportView(photoLabel);
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 800));
    histogramPanel.setViewportView(photoLabel2);

    rightSubPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    rightSubPanel.setDividerLocation(900);
    rightSubPanel.setLeftComponent(imagePanel);
    rightSubPanel.setRightComponent(histogramPanel);
    rightSubPanel.setContinuousLayout(true);

    inputField = new JTextField(5);
    inputField.setFont(new Font("Cambria", Font.PLAIN, 14));
    inputField.setBorder(BorderFactory.createLineBorder(new Color(118, 181, 197), 2));
    inputField.setVisible(false);
    userEntryPanel.add(inputField);

    blackPointField = new JTextField(5);
    blackPointField.setFont(new Font("Cambria", Font.PLAIN, 14));
    blackPointField.setBorder(BorderFactory.createLineBorder(new Color(118, 181, 197), 2));
    blackPointField.setVisible(false);
    midPointField = new JTextField(5);
    midPointField.setFont(new Font("Cambria", Font.PLAIN, 14));
    midPointField.setBorder(BorderFactory.createLineBorder(new Color(118, 181, 197), 2));
    midPointField.setVisible(false);
    whitePointField = new JTextField(5);
    whitePointField.setFont(new Font("Cambria", Font.PLAIN, 14));
    whitePointField.setBorder(BorderFactory.createLineBorder(new Color(118, 181, 197), 2));
    whitePointField.setVisible(false);

    userEntryPanel.add(blackPointField);
    userEntryPanel.add(midPointField);
    userEntryPanel.add(whitePointField);
    userEntryPanel.add(enterButton);
    userEntryPanel.add(split);
    sliderSplit = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    sliderSplit.setMajorTickSpacing(20);
    sliderSplit.setMinorTickSpacing(5);
    sliderSplit.setPaintTicks(true);
    sliderSplit.setPaintLabels(true);
    sliderSplit.setLabelTable(sliderSplit.createStandardLabels(10));
    sliderSplit.setVisible(false);
    sliderSplit.addChangeListener(evt -> splitPercentageView = sliderSplit.getValue());
    userEntryPanel.add(sliderSplit, BorderFactory.createLineBorder(new Color(118, 181, 197), 2));
    this.outputStream = System.out;
    pack();
  }

  @Override
  public void addFeatures(Features feature) {
    loadButton.addActionListener(evt ->
        feature.handleLoadButton());
    saveButton.addActionListener(evt ->
        feature.handleSaveButton());
    redButton.addActionListener(evt ->
        feature.handleImageProcessingButton("red-component", false));
    greenButton.addActionListener(evt ->
        feature.handleImageProcessingButton("green-component", false));
    blueButton.addActionListener(evt ->
        feature.handleImageProcessingButton("blue-component", false));
    horizontalFlip.addActionListener(evt ->
        feature.handleImageProcessingButton("horizontal-flip", false));
    verticalFlip.addActionListener(evt ->
        feature.handleImageProcessingButton("vertical-flip", false));
    brighten.addActionListener(evt ->
        feature.handleBrightenButton("brighten"));
    blur.addActionListener(evt ->
        feature.handleImageProcessingButton("blur", true));
    sharpen.addActionListener(evt ->
        feature.handleImageProcessingButton("sharpen", true));
    sepia.addActionListener(evt ->
        feature.handleImageProcessingButton("sepia", true));
    greyScale.addActionListener(evt ->
        feature.handleImageProcessingButton("greyscale", true));
    compress.addActionListener(evt ->
        feature.handleCompressButton("compress"));
    colorCorrect.addActionListener(evt ->
        feature.handleImageProcessingButton("color-correct", true));
    levelAdjust.addActionListener(evt ->
        feature.handleLevelsAdjustButton("levels-adjust"));
    split.addActionListener(evt -> feature.handleSplitButton(currentCommand, splitPercentageView));
    enterButton.addActionListener(evt -> feature.handleEnterButton(currentCommand));
  }


  /**
   * Creates and configures a JButton with customized appearance and behavior.
   *
   * @param label         The text displayed on the button.
   * @param actionCommand The action command associated with the button.
   * @return A JButton with customized properties.
   */
  private JButton createButton(String label, String actionCommand) {
    JButton button = new JButton(label);

    Color startColor = new Color(65, 70, 110);
    Color endColor = new Color(50, 53, 82);

    Border outerBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    Border innerBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    CompoundBorder compoundBorder = new CompoundBorder(outerBorder, innerBorder);

    button.setOpaque(true);
    button.setBackground(startColor);
    button.setBorder(compoundBorder);

    button.addMouseListener(new java.awt.event.MouseAdapter() {
      /**
       * Invoked when the mouse enters the button.
       * Changes the button background to the end color.
       */
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(endColor);
      }

      /**
       * Invoked when the mouse exits the button.
       * Restores the button background to the start color.
       */
      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(startColor);
      }
    });

    button.setForeground(Color.white);
    button.setMargin(new Insets(10, 20, 10, 20));
    button.setFont(new Font("Cambria", Font.BOLD, 16));
    button.setBorder(BorderFactory.createLineBorder(new Color(118, 181, 197), 2));
    button.setActionCommand(actionCommand);

    return button;
  }


  /**
   * Method to reset the view to default for the user.
   */
  private void resetView() {
    inputField.setText(null);
    inputField.setVisible(false);
    blackPointField.setText(null);
    blackPointField.setVisible(false);
    midPointField.setText(null);
    midPointField.setVisible(false);
    whitePointField.setText(null);
    whitePointField.setVisible(false);
    split.setVisible(false);
    sliderSplit.setVisible(false);
    enterButton.setVisible(false);
    sliderSplit.setValue(0);
  }

  @Override
  public String handleLoadButton() {
    resetView();
    checkSave();
    currentCommand = "load";
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG, PNG & PPM Images", "jpg", "gif", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(ImageViewImpl.this);

    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String filepath = f.getAbsolutePath();
      try {
        BufferedImage imageXXX = ImageIO.read(new File(filepath));
        setPhoto(imageXXX);
        displayMessage("Image Successfully Loaded!");
      } catch (IOException ex) {
        displayMessage("Error loading image: " + ex.getMessage());
      }
      return filepath;
    } else {
      return null;
    }
  }

  /**
   * Private method to check if the user has saved the image or not.
   */
  private void checkSave() {
    if (!isImageSaved && !("null".equals(currentCommand))) {
      throwError(
          "The current Image is not saved, close the load dialog box and save image if you wish");
    }
  }

  @Override
  public String handleSaveButton() {
    resetView();
    currentCommand = "save";
    JFileChooser fileChooser = new JFileChooser();
    int retValue = fileChooser.showSaveDialog(ImageViewImpl.this);
    String saveFilePath = "";
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      saveFilePath = selectedFile.getAbsolutePath();
      displayMessage("Image Successfully Saved!");
    }
    isImageSaved = true;
    return saveFilePath;
  }

  @Override
  public void handleImageProcessingButtonView(String command, Boolean supportSplit) {
    resetView();
    split.setVisible(supportSplit);
    sliderSplit.setVisible(supportSplit);
    currentCommand = command;
  }

  @Override
  public void throwError(String error) {
    JOptionPane.showMessageDialog(mainPanel,
        error);
  }

  @Override
  public String handleBrightenButtonView(String brighten) {
    String brightenValue = inputField.getText();
    resetView();
    enterButton.setVisible(true);
    inputField.setVisible(true);
    inputField.requestFocus();
    currentCommand = brighten;
    return brightenValue;
  }

  @Override
  public String handleCompressButtonView(String compress) {
    String compressValue = inputField.getText();
    resetView();
    enterButton.setVisible(true);
    inputField.setVisible(true);
    inputField.requestFocus();
    currentCommand = compress;
    return compressValue;
  }

  @Override
  public String[] handleLevelsAdjustButtonView(String levelAdjust) {
    String[] bmwValues = getBMWValues();
    resetView();
    enterButton.setVisible(true);
    split.setVisible(true);
    sliderSplit.setVisible(true);
    blackPointField.setVisible(true);
    midPointField.setVisible(true);
    whitePointField.setVisible(true);
    split.setVisible(true);
    blackPointField.requestFocus();
    currentCommand = levelAdjust;
    return bmwValues;
  }

  @Override
  public String[] getBMWValues() {
    String[] array = new String[3];

    array[0] = blackPointField.getText();
    array[1] = midPointField.getText();
    array[2] = whitePointField.getText();

    return array;
  }

  @Override
  public void setPhoto(Image image) {
    photoLabel.setIcon(new ImageIcon(image));
    imagePanel.setViewportView(photoLabel);
  }

  @Override
  public void generateHistogramView(String hist, Map<String, int[][][]> imageMap) {
    Image histogramImage = getProcessedImage("hist1", imageMap);
    setHistogram(histogramImage);
  }

  @Override
  public void setHistogram(Image image) {
    photoLabel2.setIcon(new ImageIcon(image));
    histogramPanel.setViewportView(photoLabel2);
  }

  @Override
  public Image getProcessedImage(String imageName, Map<String, int[][][]> imageMap) {
    int[][][] processedImageArray = imageMap.get(imageName);
    if (processedImageArray != null) {
      BufferedImage processedBufferedImage = createBufferedImage(processedImageArray);
      ImageIcon icon = new ImageIcon(processedBufferedImage);
      return icon.getImage();
    } else {
      return null;
    }
  }

  @Override
  public BufferedImage createBufferedImage(int[][][] imageArray) {
    int width = imageArray[0].length;
    int height = imageArray.length;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = imageArray[y][x][0] << 16 | imageArray[y][x][1] << 8 | imageArray[y][x][2];
        bufferedImage.setRGB(x, y, rgb);
      }
    }
    return bufferedImage;
  }

  @Override
  public void displayMessage(String message) {
    outputStream.println(message);
    lastMessageDisplayed = message;
    messageLabel.setText(message);
    messageLabel.setFont(new Font("Cambria", Font.BOLD, 16));
  }

  /**
   * Method to get the last message displayed by the view.
   *
   * @return returns the last message.
   */
  public String getLastMessageDisplayed() {
    return lastMessageDisplayed;
  }
}