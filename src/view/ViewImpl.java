package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.VideoStill;

public class ViewImpl implements View {
  private JFrame mainFrame;
  private JLabel imageLabel;
  private JTextField guessField;
  private JLabel scoreLabel;
  private JButton submitButton;

  public ViewImpl() {
    // Initialize components in a simpler way, similar to the working example
    SwingUtilities.invokeLater(() -> {
      try {
        // Set up the main frame similar to the working example
        mainFrame = new JFrame("Name That 90's Video!");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create image panel with a simple border
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(400, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Create a simple panel for controls
        JPanel controlPanel = new JPanel(new FlowLayout());
        guessField = new JTextField(20);
        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        scoreLabel = new JLabel("Score: 0");

        // Add components to panels
        controlPanel.add(guessField);
        controlPanel.add(submitButton);
        controlPanel.add(scoreLabel);

        // Add panels to frame
        mainFrame.add(imageLabel, BorderLayout.CENTER);
        mainFrame.add(controlPanel, BorderLayout.SOUTH);
      } catch (Exception e) {
        System.err.println("Error in ViewImpl constructor: " + e.getMessage());
        e.printStackTrace();
      }
    });
  }

  // This is the only method required by the View interface
  @Override
  public void render() {
    SwingUtilities.invokeLater(() -> {
      if (mainFrame != null) {
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
      }
    });
  }

  // Additional methods needed for the game functionality
  // These are not in the View interface but are used by the controller

  public void addSubmitListener(ActionListener listener) {
    if (submitButton != null) {
      submitButton.addActionListener(listener);
    }
  }

  public void updateImage(VideoStill still) {
    SwingUtilities.invokeLater(() -> {
      if (imageLabel != null && still != null && still.getImage() != null) {
        try {
          ImageIcon icon = new ImageIcon(still.getImage());
          // Simple scaling to fit the label
          Image scaled = icon.getImage().getScaledInstance(
              Math.min(400, still.getImage().getWidth()), 
              Math.min(300, still.getImage().getHeight()), 
              Image.SCALE_SMOOTH);
          imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
          System.err.println("Error updating image: " + e.getMessage());
          e.printStackTrace();
        }
      }
    });
  }

  public void updateScore(int score) {
    SwingUtilities.invokeLater(() -> {
      if (scoreLabel != null) {
        scoreLabel.setText("Score: " + score);
      }
    });
  }

  public String getGuess() {
    return (guessField != null) ? guessField.getText() : "";
  }
}