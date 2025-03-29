package model;

import view.ViewImpl;

public class Play {
    private VideoStill still;
    private String guess;
    private Player player;
    private ViewImpl view;

    public Play(VideoStill still, Player player, ViewImpl view) {
        this.still = still;
        this.player = player;
        this.view = view;
        view.updateImage(still);
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public boolean isCorrectGuess() {
        if (guess.equalsIgnoreCase(still.getAnswer())) {
            this.still.notPlayable();
            this.player.incrementScore();
            view.updateScore(player.getScore());
            return true;
        }
        return false;
    }
}