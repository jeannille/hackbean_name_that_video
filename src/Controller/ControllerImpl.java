package Controller;

import model.NameThatVideo;
import model.VideoStill;
import view.View;
import view.ViewImpl;
import java.util.Map;

public class ControllerImpl implements IController {
    private NameThatVideo model;
    private ViewImpl view;
    private Map<String, VideoStill> imageMap;
    private int currentIndex = 0;
    private String[] videoNames;
    private boolean gameRunning = true;

    public ControllerImpl(NameThatVideo model, ViewImpl view) {
        this.model = model;
        this.view = view;
        this.imageMap = model.getMap();
        this.videoNames = imageMap.keySet().toArray(new String[0]);
        showNextImage();
    }

    private void processGuess() {
        String guess = view.getGuess();
        if (guess == null) {
            return; // No guess available
        }

        if (guess.equalsIgnoreCase("quit") || guess.equalsIgnoreCase("exit")) {
            System.out.println("Game Over! Final score: " + currentIndex);
            gameRunning = false;
            view.close();
            return;
        }

        if (guess.equalsIgnoreCase(videoNames[currentIndex])) {
            System.out.println("Correct! It was \"" + videoNames[currentIndex] + "\"");
            view.updateScore(currentIndex + 1);
            currentIndex++;
            if (currentIndex < videoNames.length) {
                showNextImage();
            } else {
                System.out.println("Congratulations! You've completed all videos. Final score: " + currentIndex);
                gameRunning = false;
                view.close();
            }
        } else {
            System.out.println("Incorrect. Try again!");
        }
    }

    private void showNextImage() {
        if (currentIndex < videoNames.length) {
            VideoStill still = imageMap.get(videoNames[currentIndex]);
            view.updateImage(still);
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
}