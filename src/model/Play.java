package model;

import java.util.ArrayList;
import java.util.Scanner;


public class Play {
    private VideoStill still;
    private String guess;
    private Player player;


    /**
     * Constructor for an instance that relates the video still image and player interaction.
     * @param still
     * @param player
     */
    public Play(VideoStill still, Player player){
        //kick still to to view here?
        this.still = still;
        this.player = player;
    }

    public void setGuess() {
        Scanner getGuess = new Scanner(System.in);
        System.out.println("Enter your guess: ");
        String guess = getGuess.nextLine();
        this.guess = guess;
    }

    /**
     * Verifies player answer, removes VideoStill from playing deck and increments player score.
     */
    public void isCorrectGuess() {
        if (guess.equalsIgnoreCase(still.getAnswer())){
            this.still.notPlayable();
            this.player.incrementScore();
        }
    }



}
