package model;
import Controller.ControllerImpl;
import view.ViewImpl;

public class Main {
    public static void main(String[] args) {
        try {
            // Set critical system properties
            System.setProperty("java.awt.headless", "false");

            // Explicitly disable font use
            System.setProperty("sun.java2d.font.DisableCanvasAccess", "true");

            // Explicitly set X11 backend
            System.setProperty("awt.toolkit", "sun.awt.X11.XToolkit");

            // Create model first
            NameThatVideoImpl model = new NameThatVideoImpl();

            // Wait before creating view
            Thread.sleep(2000);

            // Create view
            ViewImpl view = new ViewImpl();

            // Create controller
            ControllerImpl controller = new ControllerImpl(model, view);

            // Start application
            controller.progress();
        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}