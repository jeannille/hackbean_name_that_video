
package Controller;

import model.NameThatVideo;
import model.VideoStill;
import view.View;
import view.ViewImpl;

import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.Map;

public class ControllerImpl implements IController {
    private NameThatVideo model;
    private ViewImpl view;
    private Map<String, VideoStill> imageMap;
    private int currentIndex = 0;
    private String[] videoNames;

    public ControllerImpl(NameThatVideo model, ViewImpl view) {
        this.model = model;
        this.view = view;
        this.imageMap = model.getMap();
        this.videoNames = imageMap.keySet().toArray(new String[0]);
        
        setupActionListeners();
        showNextImage();
    }

    private void setupActionListeners() {
        view.addSubmitListener(e -> {
            String guess = view.getGuess();
            if (guess.equalsIgnoreCase(videoNames[currentIndex])) {
                view.updateScore(currentIndex + 1);
                currentIndex++;
                if (currentIndex < videoNames.length) {
                    showNextImage();
                } else {
                    JOptionPane.showMessageDialog(null, "Game Over! Final score: " + currentIndex);
                }
            }
        });
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
    }
}
