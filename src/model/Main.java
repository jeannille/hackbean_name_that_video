package model;
import Controller.ControllerImpl;
import view.ViewImpl;

public class Main {
    public static void main(String[] args) {
        try {
            // Configure GUI environment
            System.setProperty("java.awt.headless", "false");
            System.setProperty("awt.toolkit", "sun.awt.X11.XToolkit");
            System.setProperty("DISPLAY", ":1");
            System.setProperty("java.awt.graphicsenv", "sun.awt.X11GraphicsEnvironment");

            // Create components
            NameThatVideoImpl model = new NameThatVideoImpl();
            ViewImpl view = new ViewImpl();
            ControllerImpl controller = new ControllerImpl(model, view);

            // Start the application
            controller.progress();
        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}