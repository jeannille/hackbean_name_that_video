package model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        NameThatVideoImpl startup = new NameThatVideoImpl();
        ViewImpl view = new ViewImpl();
        view.render();

        Map<String, VideoStill> imageMap = startup.getMap();
        startup.addPlayer();
        startup.addPlayer();
        startup.addPlayer();

        //turn counter
        int count = 0;
        for (String each: imageMap.keySet()) {
            Player currentPlayer = startup.getPlayers().get(count % startup.getPlayers().size());
            Play currentPlay = new Play(imageMap.get(each), currentPlayer, view);

            System.out.println("Current player: " + currentPlayer.getName());
            System.out.println("Make a guess from the following: \n" + each + "\n");
            
            Scanner scanner = new Scanner(System.in);
            String guess = scanner.nextLine();
            currentPlay.setGuess(guess);
            currentPlay.isCorrectGuess();
            startup.getGameState();
            count++;
        }

            count ++;
        }

    }
}
