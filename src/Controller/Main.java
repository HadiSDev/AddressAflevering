package Controller;

import java.util.*;

import Model.Model;
import Model.Address;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import Model.Reader;

public class Main {
    private static Reader r;
    private static ArrayList streetList;
    private static HashMap<String, String> zipCityList;

    private static void initialize() {
        try {

            r = new Reader();
            r.readStreets();
            r.readPostnumbersCity();
            streetList = new ArrayList(r.getStreetnames());
            zipCityList = new HashMap(r.getPostnumre());

            System.out.println(streetList.size());
            System.out.println(zipCityList.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        initialize();

        SwingUtilities.invokeLater(() -> {
            Model m = new Model();
            View v = new View(m);
        });
    }

    public static void sendInput(JTextField textField, Model model) {
        try {
            textField.addActionListener(a -> {
                Address addr = Address.parse(textField.getText());
                model.add(check(addr));
                textField.setText("");
            });

        } catch (Exception e) {

        }

    }

    public static Address check(Address a) {
        Address newAdd = a;
        ///TODO: Make a check on both Zip and City (T/F, F/T) else T/T
        if (a.postcode() == null && a.city() != null) {
            //no Postcode
            //create new Address with new postcode
            String temp = r.getKeyFromValue(newAdd.city());
            String full = String.format("%s%s%s%s", newAdd.street(), newAdd.house(), temp, newAdd.city());
            newAdd = Address.parse(full);
        }
        else if (a.postcode() != null && a.city() == null) {
            //no City
        }
        return newAdd;

    }
}
