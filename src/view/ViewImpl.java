package view;
import java.awt.event.ActionListener;
import java.util.Scanner;
import model.VideoStill;
import java.io.Console;

public class ViewImpl implements View {
    private int score = 0;
    private String currentGuess = "";
    private VideoStill currentImage;
    private ActionListener submitListener;
    private Scanner scanner;
    private volatile boolean isRunning = true;

    public ViewImpl() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void render() {
        // Start input thread
        Thread inputThread = new Thread(() -> {
            while (isRunning) {
                System.out.println("\n=== Name That 90's Video! ===");
                System.out.println("Current Score: " + score);
                System.out.println("[Image would be displayed here]");
                System.out.print("Enter your guess (or 'quit' to exit): ");

                String input = scanner.nextLine();
                if ("quit".equalsIgnoreCase(input)) {
                    isRunning = false;
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

    public void addSubmitListener(ActionListener listener) {
        this.submitListener = listener;
    }

    public void updateImage(VideoStill still) {
        this.currentImage = still;
        System.out.println("\nNew image loaded!");
    }

    public void updateScore(int score) {
        this.score = score;
        System.out.println("Score updated to: " + score);
    }

    public String getGuess() {
        return currentGuess;
    }
}