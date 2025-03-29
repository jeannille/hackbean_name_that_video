package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface NameThatVideo {



    boolean isGameOver();


    /**
     * String representation of game state. Maybe tallys?
     */
    String getGameState();


    void removeImage(Image img);

    void addPlayer();

    void removePlayer();

    Map<String, VideoStill> getMap();





}
