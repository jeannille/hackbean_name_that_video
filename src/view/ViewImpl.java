
package view;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.event.ActionListener;
import java.io.*;
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
            String html = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Name That 90's Video!</title>
                    <style>
                        body { font-family: Arial; text-align: center; padding: 20px; }
                        img { max-width: 400px; max-height: 300px; margin: 20px; }
                        input { padding: 5px; margin: 10px; }
                        button { padding: 5px 15px; }
                    </style>
                </head>
                <body>
                    <h1>Name That 90's Video!</h1>
                    <div>
                        <img src="%s" alt="Video Still">
                    </div>
                    <div>
                        <input type="text" id="guess" placeholder="Enter your guess">
                        <button onclick="submitGuess()">Submit</button>
                    </div>
                    <div>Score: %d</div>
                    <script>
                        function submitGuess() {
                            const guess = document.getElementById('guess').value;
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
                currentStill != null ? currentStill.getPath() : "",
                score
            );
            
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, html.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(html.getBytes());
            }
        }
    }

    class GuessHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String guess = exchange.getRequestURI().getQuery().split("=")[1];
            if (submitListener != null) {
                // Simulate button click event
                submitListener.actionPerformed(null);
            }
            String response = "incorrect";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
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

    public String getGuess() {
        return ""; // Handled through HTTP now
    }
}
