package model;

import Controller.ControllerImpl;
import view.ViewImpl;

public class Main {

    public static void main(String[] args) {
        NameThatVideoImpl model = new NameThatVideoImpl();
        ViewImpl view = new ViewImpl();
        ControllerImpl controller = new ControllerImpl(model, view);
        controller.progress();
    }
}