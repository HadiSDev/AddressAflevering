package Exception;

import javax.swing.*;

public class AddressNotFoundException extends IllegalArgumentException
{
    public AddressNotFoundException(String message, String input)
    {
        super(message);
        JOptionPane.showMessageDialog(new JFrame(), message+":"+ ". The entered address does not exist: "+input );
        

    }
}
