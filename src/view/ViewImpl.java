
package view;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.net.InetSocketAddress;
import model.VideoStill;

public class ViewImpl implements View {
    private HttpServer server;
    private VideoStill currentStill;
    private int score = 0;
    private ActionListener submitListener;

    public ViewImpl() {
        try {
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", 5000), 0);
            server.createContext("/", new MainHandler());
            server.createContext("/guess", new GuessHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 5000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MainHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Convert image to base64
            String imageData = "";
            if (currentStill != null && currentStill.getImage() != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(currentStill.getImage(), "png", outputStream);
                imageData = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }

            String html = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Name That 90's Video!</title>
                    <style>
                        body { font-family: Arial; text-align: center; padding: 20px; }
                        .image-container { width: 400px; height: 300px; margin: 20px auto; border: 2px solid #ccc; 
                                         display: flex; align-items: center; justify-content: center; }
                        img { max-width: 100%%; max-height: 100%%; }
                        input { padding: 5px; margin: 10px; }
                        button { padding: 5px 15px; }
                    </style>
                </head>
                <body>
                    <h1>Name That 90's Video!</h1>
                    <div class="image-container">
                        <img src="data:image/png;base64,%s" alt="Video Still">
                    </div>
                    <div>
                        <p><strong>Hint:</strong> This song came out in the 90s and was a huge hit!</p>
                    </div>
                    <div>
                        <input type="text" id="guess" placeholder="Enter your guess">
                        <button onclick="submitGuess()">Submit</button>
                    </div>
                    <div>Score: %d</div>
                    <div style="margin-top: 20px; color: #666;">Debug - Current Answer: %s</div>
                    <div style="margin-top: 10px; color: #666;">Last submitted guess: <span id="lastGuess">None</span></div>
                    <script>
                        function submitGuess() {
                            const guess = document.getElementById('guess').value;
                            document.getElementById('lastGuess').textContent = guess;
                            fetch('/guess?guess=' + encodeURIComponent(guess))
                                .then(response => response.text())
                                .then(result => {
                                    if (result === 'correct') {
                                        location.reload();
                                    } else {
                                        alert('Try again!');
                                    }
                                });
                        }
                    </script>
                </body>
                </html>
                """, 
                imageData,
                score,
                currentStill != null ? currentStill.getAnswer() : "No answer available"
            );
            
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, html.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(html.getBytes());
            }
        }
    }

    private String lastGuess = "";
    private boolean guessEntered = false;

    // Add this field to your ViewImpl class
    private boolean lastGuessCorrect = false;

    class GuessHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                String query = exchange.getRequestURI().getQuery();
                String guess = "";

                if (query != null && query.startsWith("guess=")) {
                    guess = java.net.URLDecoder.decode(query.substring(6), "UTF-8");
                    System.out.println("Received guess: " + guess);

                    // Store the guess for the controller (still needed for score updates)
                    lastGuess = guess;
                    guessEntered = true;

                    // Directly check if guess is correct
                    boolean isCorrect = false;
                    if (currentStill != null) {
                        String normalizedGuess = guess.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                        String normalizedAnswer = currentStill.getAnswer().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                        isCorrect = normalizedGuess.equals(normalizedAnswer);
                        System.out.println("Comparing: '" + normalizedGuess + "' with '" + normalizedAnswer + "' - Match: " + isCorrect);
                    }

                    // Trigger controller processing (for score updates and next image)
                    if (submitListener != null) {
                        submitListener.actionPerformed(null);
                    }

                    // Determine HTTP response
                    String response = isCorrect ? "correct" : "incorrect";
                    System.out.println("Sending HTTP response: " + response);

                    exchange.getResponseHeaders().set("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(200, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    return; // Exit early after processing
                }

                // If we get here, no valid guess was provided
                String response = "error: no valid guess provided";
                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(400, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                String errorMsg = "Error processing guess: " + e.getMessage();
                System.err.println(errorMsg);
                e.printStackTrace();

                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(500, errorMsg.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(errorMsg.getBytes());
                }
            }
        }
    }

    @Override
    public void render() {
        // Server is already running
    }

    public void addSubmitListener(ActionListener listener) {
        this.submitListener = listener;
    }

    public void updateImage(VideoStill still) {
        this.currentStill = still;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    // Update the getGuess method to return the actual guess
    public String getGuess() {
        if (guessEntered) {
            guessEntered = false;
            return lastGuess;
        }
        return null;
    }

    // Add this method for the controller to check if there's a new guess
    public boolean hasNewGuess() {
        return guessEntered;
    }

    // Add this method to your ViewImpl class
    public void close() {
        if (server != null) {
            server.stop(0); // Stop the server with a 0-second delay
            System.out.println("Server stopped");
        }
    }



    
}
