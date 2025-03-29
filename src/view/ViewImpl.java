
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
    // Ensure we're not in headless mode
    System.setProperty("java.awt.headless", "false");
    
    mainFrame = new JFrame("Name That 90's Video!");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanel = new JPanel(new BorderLayout(10, 10));
    setupUI();
    mainFrame.setVisible(true);
  }

  private void setupUI() {
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    imageLabel = new JLabel();
    imageLabel.setPreferredSize(new Dimension(400, 300));
    
    controlPanel = new JPanel(new FlowLayout());
    guessField = new JTextField(20);
    JButton submitButton = new JButton("Submit Guess");
    submitButton.setActionCommand("submit");
    
    scoreLabel = new JLabel("Score: 0");
    
    controlPanel.add(guessField);
    controlPanel.add(submitButton);
    controlPanel.add(scoreLabel);
    
    mainPanel.add(imageLabel, BorderLayout.CENTER);
    mainPanel.add(controlPanel, BorderLayout.SOUTH);
    
    mainFrame.add(mainPanel);
    mainFrame.pack();
  }

  public void addSubmitListener(ActionListener listener) {
    for (Component c : controlPanel.getComponents()) {
      if (c instanceof JButton && ((JButton)c).getActionCommand().equals("submit")) {
        ((JButton)c).addActionListener(listener);
      }
    }
  }

  @Override
  public void render() {
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setVisible(true);
    // Ensure the frame is properly sized
    mainFrame.pack();
  }

  public void updateImage(VideoStill still) {
    ImageIcon icon = new ImageIcon(still.getImage());
    Image scaled = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
    imageLabel.setIcon(new ImageIcon(scaled));
  }

  public void updateScore(int score) {
    scoreLabel.setText("Score: " + score);
  }

  public String getGuess() {
    return guessField.getText();
  }
}
