package Controller;

import model.NameThatVideo;
import view.View;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ControllerImpl extends JOptionPane implements IController {
    private NameThatVideo model;
    private View view;
    String in;
    String output;
    String[] inputArray;
    JOptionPane errorHandler;
    boolean includesOutFile;

    public ControllerImpl(String[] input) {
        in = "";
        output = "";
        inputArray = input;

    }

    @Override
    public void progress() {

    }
}
