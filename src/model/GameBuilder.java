package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameBuilder {
//    private Map<String, VideoStill> imageMap
//    ArrayList<BufferedImage> images;
//    ArrayList<String> imageNames;

    public Map<String, VideoStill> createObjects() {

        //ArrayList<String> imageNames = new ArrayList<>();
        ArrayList<String> imageNames = new ArrayList<>();
        ArrayList<BufferedImage> images = new ArrayList<>();
        Map<String, VideoStill> imageMap = new HashMap<>();
        ArrayList<String> allPaths = new ArrayList<>();
        Map<String,String> imageAndPath = new HashMap<>();

        imageNames.add("Are You That Somebody?");
        imageNames.add("Bootylicious");
        imageNames.add("Don't Speak");
        imageNames.add("I Saw the Sign");
        imageNames.add("I Want It That Way");
        imageNames.add("I Want to Dance with Somebody");
        imageNames.add("Kiss from A Rose");
        imageNames.add("No Strings Attached");
        imageNames.add("Say My Name");
        imageNames.add("Smells Like Teen Spirit");
        imageNames.add("Vogue");
        imageNames.add("Waterfalls");

        File dir = new File("src/model/images");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        ArrayList<String> extension = new ArrayList<String>();

        extension.add("png");
        extension.add("jpeg");
        extension.add("jpg");

        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                for (String a : extension) {
                    //determines if this file is an actual image ERROR DETECTION REYES 2021
                    if (name.endsWith("." + a)) {   //jpeg,
                        return true;
                    }
                }
                return false;
            }
        };

        if (dir.isDirectory()) {
            BufferedImage img = null;
            for (File f : dir.listFiles(filter)) {
                try {
                    img = ImageIO.read(f);
                    //String path = f.getPath();
                    //allPaths.add(path);
//                    System.out.println(img);
                } catch (Exception e) {
//                    System.out.println(e);
                }
                images.add(img);
            }
        }

        // Read paths from data.txt
        Map<String, String> pathMap = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("data.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    pathMap.put(parts[0], parts[1]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Warning: data.txt not found");
        }

        //matching file name and image in map for videoStill
        for (int i = 0; i < images.size(); i++) {
            VideoStill vs = new VideoStill(images.get(i), imageNames.get(i));
            vs.setPath(pathMap.getOrDefault(imageNames.get(i), ""));
            imageMap.put(imageNames.get(i), vs);
        }

        //matching file name and file path in map
//        for (int j = 0; j < images.size(); j++) {
//            imageAndPath.put(imageNames.get(j), allPaths.get(j));
//            //System.out.println("Prints j:" + j);
//        }

//        for (int i = 0; i < images.size(); i++) {
//            System.out.println("Image list:" + imageNames.get(i));
//            System.out.println("Path:" + allPaths.get(i));
//            //System.out.println("Key:" + imageAndPath.keySet());
//        }


        return imageMap;

    }


}

