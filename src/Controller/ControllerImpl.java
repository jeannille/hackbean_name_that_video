
package Controller;
import java.util.Map;
import javax.swing.JOptionPane;
import model.NameThatVideo;
import model.VideoStill;
import view.ViewImpl;


public class ControllerImpl implements IController {
    private NameThatVideo model;
    private ViewImpl view;
    private Map<String, VideoStill> imageMap;
    private int currentIndex = 0;
    private String[] videoNames;
    private boolean gameRunning = true; // Add this field

    public ControllerImpl(NameThatVideo model, ViewImpl view) {
        this.model = model;
        this.view = view;
        this.imageMap = model.getMap();
        this.videoNames = imageMap.keySet().toArray(new String[0]);

        // No need to set up action listeners since we're using the game loop approach
        showNextImage();
    }

   

    private void showNextImage() {
        if (currentIndex < videoNames.length) {
            VideoStill still = imageMap.get(videoNames[currentIndex]);
            view.updateImage(still);
            System.out.println("Showing image: " + videoNames[currentIndex]);
        }
    }

    @Override
    public void progress() {
        view.render();

        // Game loop
        while (gameRunning) {
            try {
                if (view.hasNewGuess()) {
                    processGuess();
                }
                Thread.sleep(100); // Small delay to prevent CPU hogging
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

 private void processGuess() {
        String guess = view.getGuess();
        if (guess == null || guess.isEmpty()) {
            return;
        }

        System.out.println("Processing guess: " + guess);

        // Normalize the guess and answer for more flexible matching
        String normalizedGuess = guess.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String normalizedAnswer = videoNames[currentIndex].replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        if (normalizedGuess.equals(normalizedAnswer)) {
            System.out.println("Correct guess!");
            view.updateScore(currentIndex + 1);
            currentIndex++;
            if (currentIndex < videoNames.length) {
                showNextImage();
            } else {
                System.out.println("Game over! All videos guessed correctly.");
                gameRunning = false;
                view.close();
            }
        } else {
            System.out.println("Incorrect guess. Expected: " + videoNames[currentIndex]);
        }
    }
}
