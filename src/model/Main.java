package model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Scanner;
import view.ViewImpl;

public class Main {

    public static void main(String[] args) {
        NameThatVideoImpl startup = new NameThatVideoImpl();
        ViewImpl view = new ViewImpl();
        view.render();

        Map<String, VideoStill> imageMap = startup.getMap();
        // Display first video still
        if (!imageMap.isEmpty()) {
            String firstKey = imageMap.keySet().iterator().next();
            VideoStill firstStill = imageMap.get(firstKey);
            view.updateImage(firstStill);
        }
        startup.addPlayer();
        startup.addPlayer();
        startup.addPlayer();

        //turn counter
        int count = 0;
        for (String each: imageMap.keySet()) {
            Player currentPlayer = startup.getPlayers().get(count % startup.getPlayers().size());
            Play currentPlay = new Play(imageMap.get(each), currentPlayer, view);

            System.out.println("Make a guess from the following: \n"
                    + each + "\n"
                    + "Hot Cross Buns\n");

            Scanner scanner = new Scanner(System.in);
            String guess = scanner.nextLine();
            currentPlay.setGuess(guess);
            currentPlay.isCorrectGuess();
            startup.getGameState();
            count++;
        }
    }
}