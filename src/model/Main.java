package model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.Map;

public class Main {

    public static void main(String[] args) {


        NameThatVideoImpl startup = new NameThatVideoImpl();

        Map<String, VideoStill> imageMap = startup.getMap();
        startup.addPlayer();
        startup.addPlayer();
        startup.addPlayer();


        //turn counter
        int count = 0;
        for (String each: imageMap.keySet()) {
            Player currentPlayer = startup.getPlayers().get(count % startup.getPlayers().size());
            Play currentPlay = new Play(imageMap.get(each),currentPlayer);


            VideoStill current = imageMap.get(each);
            BufferedImage img = current.getImage();
            ImageIcon icon = new ImageIcon(img);
            JFrame frame=new JFrame();
            frame.setVisible(false);

            frame.setLayout(new FlowLayout());
            frame.setSize(200,300);
            JLabel lbl=new JLabel();
            lbl.setSize(50,50);
            Image imageIcon = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(),
                    Image.SCALE_SMOOTH);
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




            System.out.println("Make a guess from the following: \n"
                    + each + "\n"
                    + "Hot Cross Buns\n");

            currentPlay.setGuess();
            currentPlay.isCorrectGuess();
            startup.getGameState();

            count ++;
        }

    }
}
