package view;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Scanner;
import model.VideoStill;

public class ViewImpl implements View {
  private int score = 0;
  private String lastGuess = "";
  private boolean guessEntered = false;
  private Scanner scanner;
  private Thread inputThread;
  private volatile boolean running = true;

  public ViewImpl() {
    scanner = new Scanner(System.in);
    // Start a background thread to handle user input
    inputThread = new Thread(() -> {
      while (running) {
        System.out.print("Enter your guess: ");
        lastGuess = scanner.nextLine();
        guessEntered = true;

        // Wait for controller to process the guess
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // Ignore
        }
      }
    });
  }

  public void addSubmitListener(ActionListener listener) {
    // Nothing to do - we'll handle input differently
  }

  @Override
  public void render() {
    System.out.println("\n--- Name That 90's Video Game ---");
    System.out.println("(Text-based version to avoid font configuration issues)");
    System.out.println("Score: " + score);

    if (!inputThread.isAlive()) {
      inputThread.start();
    }
  }

  public void updateImage(VideoStill still) {
    System.out.println("\n[New image: " + still.getAnswer() + " from source: " + still.getPath() + "]");
    System.out.println("Hint: The song is from the artist who performed \"" + getHint(still.getAnswer()) + "\"");
    
  }

  private String getHint(String videoName) {
    // Provide a hint based on the correct answer
    // This way we can still play the game without showing images
    if (videoName.equals("Are You That Somebody?")) {
      return "Try Again";
    } else if (videoName.equals("Bootylicious")) {
      return "Independent Women";
    } else if (videoName.equals("Don't Speak")) {
      return "Just a Girl";
    } else if (videoName.equals("I Saw the Sign")) {
      return "The Sign";
    } else if (videoName.equals("I Want It That Way")) {
      return "Everybody (Backstreet's Back)";
    } else if (videoName.equals("I Want to Dance with Somebody")) {
      return "Greatest Love of All";
    } else if (videoName.equals("Kiss from A Rose")) {
      return "Crazy";
    } else if (videoName.equals("No Strings Attached")) {
      return "Bye Bye Bye";
    } else if (videoName.equals("Say My Name")) {
      return "Bills, Bills, Bills";
    } else if (videoName.equals("Smells Like Teen Spirit")) {
      return "Come As You Are";
    } else if (videoName.equals("Vogue")) {
      return "Like a Prayer";
    } else if (videoName.equals("Waterfalls")) {
      return "No Scrubs";
    } else {
      return "another hit song";
    }
  }

  public void updateScore(int score) {
    this.score = score;
    System.out.println("Score updated to: " + score);
  }

  public String getGuess() {
    // Check if there's a new guess available
    if (guessEntered) {
      guessEntered = false;
      return lastGuess;
    }
    return null; // No guess available
  }

  public boolean hasNewGuess() {
    return guessEntered;
  }

  public void close() {
    running = false;
    if (scanner != null) {
      scanner.close();
    }
  }
}