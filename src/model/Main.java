package model;
import Controller.ControllerImpl;
import view.ViewImpl;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Starting text-based game to avoid GUI issues...");

            // Create model
            NameThatVideoImpl model = new NameThatVideoImpl();

            // Create text-based view
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