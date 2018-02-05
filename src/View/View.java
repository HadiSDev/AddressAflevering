package View;
import Model.Model;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import Model.Address;

import static Controller.Main.sendInput;



public class View implements Observer{
    JFrame window;
    JTextField userInput;
    JTextArea userOutput;
    Model model;

    public View(Model m)
    {
        model = m;
        model.addObserver(this);
        window = new JFrame("Address Viewer");
        userInput = new JTextField();
        userInput.setPreferredSize(new Dimension(500,30));

        userOutput = new JTextArea();
        userOutput.setPreferredSize(new Dimension(500,300));
        userOutput.setEditable(false);
        window.setLayout(new BorderLayout());
        window.add(userInput, BorderLayout.NORTH);
        window.add(userOutput, BorderLayout.CENTER);

        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        sendInput(userInput, model);


    }


    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        StringBuilder sb = new StringBuilder();
        for (Address a: model) {
            sb.append(a).append("\n\n");

        }
        userOutput.setText(sb.toString());
    }
}