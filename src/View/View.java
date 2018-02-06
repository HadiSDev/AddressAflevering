package View;
import Model.Model;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import Model.Address;
import Model.Reader;

import static Controller.Main.sendInput;



public class View implements Observer{
    JFrame window;
    JTextField userInput;
    Model model;
    Reader reader;
    JList<String> suggestions;
    DefaultListModel<String> modelSuggestion;
    public View(Model m, Reader r)
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception e)
        {
            System.out.println("LAF Failed");
        }
        model = m;
        model.addObserver(this);
        reader = r;
        reader.addObserver(this);
        window = new JFrame("Address Viewer");
        JPanel northpanel = new JPanel(new GridLayout(0,3));
        JLabel helptext = new JLabel("Enter your address");
        helptext.setHorizontalAlignment(SwingConstants.CENTER);
        northpanel.add(helptext);

        userInput = new JTextField();
        //userInput.setPreferredSize(new Dimension(500,30));
        northpanel.add(userInput);

        JButton enter = new JButton("Create Address");
        northpanel.add(enter);

        modelSuggestion = new DefaultListModel<>();
        suggestions = new JList<>(modelSuggestion);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(suggestions);
        JPanel centerpane = new JPanel();
        centerpane.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(500,400));


        window.setLayout(new BorderLayout());
        window.add(northpanel, BorderLayout.NORTH);
        window.add(centerpane, BorderLayout.CENTER);

        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        enter.addActionListener(e ->
        {
            sendInput(userInput.getText(), model);
            userInput.setText("");
        });

        userInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                setSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                setSuggestions();

            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                setSuggestions();
            }
            public void setSuggestions()
            {
                modelSuggestion.removeAllElements();
                r.findSuggestedAddresses(userInput.getText());


            }
        });


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
    public void update(Observable o, Object arg)
    {
        modelSuggestion.addElement((String) arg+"\n\n");
    }

}