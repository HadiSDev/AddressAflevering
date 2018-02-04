package View;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import Model.Address;
import Model.Reader;


import static Controller.Main.getSuggestions;
import static Controller.Main.sendInput;



public class View implements Observer{
    JFrame window;
    JTextField userInput;
    JTextArea userOutput;
    Model model;
    AutoSuggestor autoSuggestor;


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

        //autoSuggestor = new AutoSuggestor(userInput, window, getSuggestions(),Color.WHITE.brighter(),Color.BLUE, Color.RED, 0.75f);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        // Actually should be part of the controller
        sendInput(userInput, model);


    }
/*    public void populateSuggestions()
    {
        for(String s: getStreetnames())
        {
            suggestions.add(s);
        }
        for (Map.Entry<String, String> entry : getPostcodes().entrySet())
        {
            suggestions.add(getPostcodes().get(entry.getKey()));
        }
    }*/

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
