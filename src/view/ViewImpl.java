package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.VideoStill;

public class ViewImpl implements View {
  private JFrame mainFrame;
  private JPanel mainPanel;
  private JTextField guessField;
  private JLabel scoreLabel;
  private JLabel imageLabel;
  private JPanel controlPanel;

  public ViewImpl() {
    // Skip UI initialization in constructor - we'll do it in render() instead
    // This avoids immediate font access during class loading
  }

  private void initializeUI() {
    try {
      // Set these properties before creating any Swing components
      System.setProperty("java.awt.headless", "false");
      System.setProperty("awt.toolkit", "sun.awt.X11.XToolkit");
      // Disable font configuration - use a very simple approach
      System.setProperty("swing.plaf", "javax.swing.plaf.metal.MetalLookAndFeel");

      // Create basic components without any fancy styling
      mainFrame = new JFrame("Name That 90's Video!");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainPanel = new JPanel(new BorderLayout(10, 10));

      // Create a very simple panel for the image
      imageLabel = new JLabel("Image will appear here");
      imageLabel.setPreferredSize(new Dimension(400, 300));

      // Create a simple panel for controls
      controlPanel = new JPanel(new FlowLayout());
      guessField = new JTextField(20);

      // Create a basic button without fancy styling
      JButton submitButton = new JButton("Submit");
      submitButton.setActionCommand("submit");

      // Create a simple label for score
      scoreLabel = new JLabel("Score: 0");

      // Add components to panels
      controlPanel.add(guessField);
      controlPanel.add(submitButton);
      controlPanel.add(scoreLabel);

      mainPanel.add(imageLabel, BorderLayout.CENTER);
      mainPanel.add(controlPanel, BorderLayout.SOUTH);

      mainFrame.add(mainPanel);

      // Set a basic size instead of using pack() which might trigger font metrics
      mainFrame.setSize(500, 400);

      // Add listener to button
      for (Component c : controlPanel.getComponents()) {
        if (c instanceof JButton && ((JButton)c).getActionCommand().equals("submit")) {
          // We'll add the actual listener later
        }
      }
    } catch (Exception e) {
      System.err.println("Error in initializeUI: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void addSubmitListener(ActionListener listener) {
    // Make sure the UI is initialized
    if (controlPanel == null) {
      return;
    }

    for (Component c : controlPanel.getComponents()) {
      if (c instanceof JButton && ((JButton)c).getActionCommand().equals("submit")) {
        ((JButton)c).addActionListener(listener);
      }
    }
  }

  @Override
  public void render() {
    // Initialize UI just before rendering - this is key to avoiding the error
    // By delaying initialization until render() is called, we give the system more time to set up
    if (mainFrame == null) {
      initializeUI();
    }

    if (mainFrame != null) {
      // Show the frame without using pack() which triggers font metrics
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
    }
  }

  public void updateImage(VideoStill still) {
    if (imageLabel == null || still == null || still.getImage() == null) {
      return;
    }

    try {
      // Use a simplified approach to display the image
      ImageIcon icon = new ImageIcon(still.getImage());
      imageLabel.setIcon(icon);
      // Skip scaling to avoid potential issues
    } catch (Exception e) {
      System.err.println("Error updating image: " + e.getMessage());
    }
  }

  public void updateScore(int score) {
    if (scoreLabel == null) {
      return;
    }

    scoreLabel.setText("Score: " + score);
  }

  public String getGuess() {
    return (guessField != null) ? guessField.getText() : "";
  }
}