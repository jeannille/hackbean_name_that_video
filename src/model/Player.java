
package model;

public class Player {
    private String name;
    private int score;
    private boolean isPlaying;

    /**
     * Upon creation of a Player object, the user chooses their name.
     * By default, the user's score is set to zero and their turn is set to false.
     * @param name of players
     */

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.isPlaying = true;
    }

    /**
     * Returns the name of the player.
     * */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the score of the player.
     * */
    public int getScore() {
        return this.score;
    }

    /**
     * Method changes a players playability status.
     * */
    private void changePlayingStatus() {
        if (isPlaying) {
            this.isPlaying = false;
        } else {
            this.isPlaying = true;
        }
    }

    public void incrementScore() {
        this.score ++;
    }
}
