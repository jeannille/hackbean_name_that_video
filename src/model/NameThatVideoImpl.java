package model;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class NameThatVideoImpl implements NameThatVideo {
    private ArrayList<Player> players;
    private ArrayList<Play> gameLog;
    private Map<String, VideoStill> images;
    private final int maxPlayers;


    public NameThatVideoImpl() {
        maxPlayers = 4;
        players = new ArrayList<>();
        gameLog = new ArrayList<>();
        images = new GameBuilder().createObjects();
    }

    public Map<String, VideoStill> getMap() {
        return this.images;
    }


    //return false if there are still images to show, return true if no more images to show or quit
    //chosen from menu options
    @Override
    public boolean isGameOver() {
        if (this.images.size() != 0) {
            return false;
        }
        return true;
    }

    //print out players current scores
    @Override
    public String getGameState() {
        String results = "";
        for (Player each : players) {
            int playerIndex = players.indexOf(each);
            System.out.println("Score for " + players.get(playerIndex).getName()
                    + ":" + Integer.toString(players.get(playerIndex).getScore()));
        }
        return results;
    }




    //update videoStill "database"
    @Override
    public void removeImage(Image img) {

    }


    /**
     * Solicits user input for a name, uses that input to instantiate a new
     * Player object, and adds said player to the master list of players.
     */
    @Override
    public void addPlayer() {
        if (players.size() <= maxPlayers) {
            Scanner getName = new Scanner(System.in);
            System.out.println("Enter new player's name");
            String name = getName.nextLine();
            Player newPlayer = new Player(name);

            players.add(newPlayer);
        }

    }

    @Override
    public void removePlayer() {

    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    public String printGame() {
        String entireGame = "";
        for (int i = 0; i < gameLog.size(); i++) {
            Play current = gameLog.get(i);
            entireGame = entireGame + current.toString();

        }
        return entireGame;
    }


}
