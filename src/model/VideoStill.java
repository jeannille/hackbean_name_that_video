package model;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class VideoStill {
    private String answer;
    private BufferedImage img;
//    private int imgId;
    private boolean isPlayable;
    private String path;


    public VideoStill(BufferedImage img, String answer) {
        if (img == null || answer.equals("")) {
            throw new IllegalArgumentException("not valid");
        }

        this.answer = answer;
        this.img = img;
        //this.imgId = imgId;
        this.isPlayable = true;
        this.path = "";
    }



    public String getAnswer() {
        return this.answer;
    }


    public BufferedImage getImage() {
        return this.img;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    /**
     * Make this still not playable.
     */
    public void notPlayable() {
        if (isPlayable) {
            this.isPlayable = false;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}
