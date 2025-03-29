
package view;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.*;
import model.VideoStill;

public class ViewImpl implements View {
    private int score = 0;
    private String currentGuess = "";
    private VideoStill currentImage;
    private ActionListener submitListener;
    private Scanner scanner;
    private volatile boolean isRunning = true;
    private JFrame frame;
    private JLabel imageLabel;
    private JTextArea textArea;

    public ViewImpl() {
        scanner = new Scanner(System.in);
        // Initialize GUI components first
        frame = new JFrame("Name That 90's Video!");
        imageLabel = new JLabel();
        textArea = new JTextArea();
        setupGUI();
    }

    private void setupGUI() {
        EventQueue.invokeLater(() -> {
            frame = new JFrame("Name That 90's Video!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);

            textArea = new JTextArea();
            textArea.setEditable(false);

            frame.setLayout(new BorderLayout());
            frame.add(imageLabel, BorderLayout.CENTER);
            frame.add(textArea, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }




     @Override
        public void render() {
            // Thread to handle user input
            Thread inputThread = new Thread(() -> {
                while (isRunning) {
                    textArea.setText("\n=== Name That 90's Video! ===\nCurrent Score: " + score);
                    System.out.print("Enter your guess (or 'quit' to exit): ");
                    String input = scanner.nextLine();
                    if ("quit".equalsIgnoreCase(input)) {
                        isRunning = false;
                        frame.dispose();
                        break;
                    }
                    currentGuess = input;
                    if (submitListener != null) {
                        submitListener.actionPerformed(null);
                    }
                }
            });
            inputThread.start();
        }
    }


    public void addSubmitListener(ActionListener listener) {
        this.submitListener = listener;
    }

    public void updateImage(VideoStill still) {
        this.currentImage = still;
        if (still != null && still.getImage() != null) {
            imageLabel.setIcon(new ImageIcon(still.getImage()));
            frame.revalidate();
            frame.repaint();
        }
        System.out.println("\nNew image loaded!");
    }

    public void updateScore(int score) {
        this.score = score;
        textArea.setText("\n=== Name That 90's Video! ===\nCurrent Score: " + score);
        System.out.println("Score updated to: " + score);
    }

    public String getGuess() {
        return currentGuess;
    }
}
