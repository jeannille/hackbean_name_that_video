
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.VideoStill;

public class ViewImpl implements View {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel imageLabel;
    private JTextField guessField;
    private JLabel scoreLabel;
    private JLabel hintLabel;
    private int score = 0;
    private String lastGuess = "";
    private boolean guessEntered = false;

    public ViewImpl() {
        // Create and setup the window
        frame = new JFrame("Name That 90's Video Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create image label
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        // Create score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Create hint label
        hintLabel = new JLabel();
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        // Create input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        guessField = new JTextField();
        guessField.addActionListener(e -> {
            lastGuess = guessField.getText();
            guessEntered = true;
            guessField.setText("");
        });

        // Add components to panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(scoreLabel, BorderLayout.WEST);
        topPanel.add(hintLabel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        inputPanel.add(new JLabel("Enter your guess: "), BorderLayout.WEST);
        inputPanel.add(guessField, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void render() {
        frame.setVisible(true);
    }

    public void updateImage(VideoStill still) {
        try {
            File imageFile = new File(still.getPath());
            BufferedImage img = ImageIO.read(imageFile);
            Image scaledImg = img.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImg));
            hintLabel.setText("Hint: The song is from the artist who performed \"" + getHint(still.getAnswer()) + "\"");
            frame.revalidate();
            frame.repaint();
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    private String getHint(String videoName) {
        // Keep existing hint logic
        if (videoName.equals("Are You That Somebody?")) {
            return "Try Again";
        } else if (videoName.equals("Bootylicious")) {
            return "Independent Women";
        } else if (videoName.equals("Don't Speak")) {
            return "Just a Girl";
        } else if (videoName.equals("I Saw the Sign")) {
            return "The Sign";
        } else if (videoName.equals("I Want It That Way")) {
            return "Everybody (Backstreet's Back)";
        } else if (videoName.equals("I Want to Dance with Somebody")) {
            return "Greatest Love of All";
        } else if (videoName.equals("Kiss from A Rose")) {
            return "Crazy";
        } else if (videoName.equals("No Strings Attached")) {
            return "Bye Bye Bye";
        } else if (videoName.equals("Say My Name")) {
            return "Bills, Bills, Bills";
        } else if (videoName.equals("Smells Like Teen Spirit")) {
            return "Come As You Are";
        } else if (videoName.equals("Vogue")) {
            return "Like a Prayer";
        } else if (videoName.equals("Waterfalls")) {
            return "No Scrubs";
        } else {
            return "another hit song";
        }
    }

    public void updateScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    public String getGuess() {
        if (guessEntered) {
            guessEntered = false;
            return lastGuess;
        }
        return null;
    }

    public boolean hasNewGuess() {
        return guessEntered;
    }

    public void close() {
        frame.dispose();
    }
}
