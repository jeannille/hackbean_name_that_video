package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import model.VideoStill;

public class ViewImpl implements View {
  // Use Canvas instead of JComponent-based elements
  private Frame mainFrame;
  private Panel mainPanel;
  private TextField guessField;
  private Label scoreLabel;
  private ImageCanvas imageCanvas;
  private Panel controlPanel;
  private Button submitButton;

  // Custom Canvas for displaying images without font dependencies
  private static class ImageCanvas extends Canvas {
    private Image image;

    public ImageCanvas() {
      setBackground(Color.BLACK);
      setPreferredSize(new Dimension(400, 300));
    }

    public void setImage(Image img) {
      this.image = img;
      repaint();
    }

    @Override
    public void paint(Graphics g) {
      if (image != null) {
        // Draw image centered in canvas
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
      } else {
        // Fill with background color if no image
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
      }
    }
  }

  public ViewImpl() {
    // Don't initialize in constructor - wait for render()
  }

  private void setupUI() {
    try {
      // Use AWT components instead of Swing to minimize font dependencies
      mainFrame = new Frame("Name That 90's Video!");
      mainFrame.setSize(500, 400);

      // Use simple layout to avoid font metrics
      mainPanel = new Panel(new BorderLayout(10, 10));

      // Use canvas for image display
      imageCanvas = new ImageCanvas();

      // Use simple AWT controls
      controlPanel = new Panel(new FlowLayout());
      guessField = new TextField(20);
      submitButton = new Button("Submit Guess");

      // Use Label instead of JLabel
      scoreLabel = new Label("Score: 0");

      // Add components using AWT methods
      controlPanel.add(guessField);
      controlPanel.add(submitButton);
      controlPanel.add(scoreLabel);

      mainPanel.add(imageCanvas, BorderLayout.CENTER);
      mainPanel.add(controlPanel, BorderLayout.SOUTH);

      mainFrame.add(mainPanel);

      // Add window closing handler
      mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
          System.exit(0);
        }
      });
    } catch (Exception e) {
      System.err.println("Error in setupUI: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void addSubmitListener(ActionListener listener) {
    if (submitButton == null) {
      return;
    }

    try {
      submitButton.addActionListener(listener);
    } catch (Exception e) {
      System.err.println("Error adding listener: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void render() {
    if (mainFrame == null) {
      setupUI();
    }

    if (mainFrame != null) {
      try {
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
      } catch (Exception e) {
        System.err.println("Error in render: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void updateImage(VideoStill still) {
    if (imageCanvas == null || still == null || still.getImage() == null) {
      return;
    }

    try {
      // Directly set the image to canvas
      imageCanvas.setImage(still.getImage());
    } catch (Exception e) {
      System.err.println("Error updating image: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void updateScore(int score) {
    if (scoreLabel == null) {
      return;
    }

    try {
      scoreLabel.setText("Score: " + score);
    } catch (Exception e) {
      System.err.println("Error updating score: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public String getGuess() {
    return (guessField != null) ? guessField.getText() : "";
  }
}