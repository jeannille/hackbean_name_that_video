package model;
import Controller.ControllerImpl;
import view.ViewImpl;

public class Main {
    public static void main(String[] args) {
        try {
            // Set core properties before anything else
            System.setProperty("java.awt.headless", "false");
            System.setProperty("awt.toolkit", "sun.awt.X11.XToolkit");
            System.setProperty("swing.plaf", "javax.swing.plaf.metal.MetalLookAndFeel");

            // Give the system some time to initialize
            Thread.sleep(1000);

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