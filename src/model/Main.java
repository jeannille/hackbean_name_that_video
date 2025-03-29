package model;
import Controller.ControllerImpl;
import view.ViewImpl;

public class Main {
    public static void main(String[] args) {
        try {
            // Basic X11 configuration
            System.setProperty("java.awt.headless", "false");
            System.setProperty("awt.toolkit", "sun.awt.X11.XToolkit");
            
            // Disable font-related features
            System.setProperty("sun.java2d.font.DisableCanvasAccess", "true");
            System.setProperty("sun.awt.fontconfig", "false");
            System.setProperty("sun.java2d.xrender", "false");
            
            // Force simple graphics environment
            System.setProperty("java.awt.graphicsenv", "sun.awt.X11GraphicsEnvironment");
            System.setProperty("DISPLAY", ":0");

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