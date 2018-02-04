package Controller;

import java.util.*;

import Model.Model;
import Model.Address;
import View.View;

import javax.swing.*;
import java.io.IOException;
import java.util.stream.Stream;

import Model.Reader;

import static Model.Reader.getPostcodes;
import static Model.Reader.getStreetnames;

public class Main
{
    private static Reader r;
    private static ArrayList streetList;
    private static HashMap<String, String> zipCityList;
    private static ArrayList<String> city;
    private static ArrayList<String> zipCode;
    private static ArrayList<String> suggestions;

    private static Model m;

    private static void initialize() {
        try {

            r = new Reader();
            r.readStreets();
            r.readPostCodes();
            streetList = new ArrayList(r.getStreetnames());
            zipCityList = new HashMap(r.getPostcodes());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        initialize();
        suggestions= new ArrayList<>();
        city = new ArrayList<String>(zipCityList.values());
        zipCode = new ArrayList<>(zipCityList.keySet());
        suggestions.addAll(city);
        suggestions.addAll(streetList);
        suggestions.addAll(zipCode);

        SwingUtilities.invokeLater(() -> {
            m = new Model();
            View v = new View(m);
        });
    }

    public static void sendInput(JTextField textField, Model model) {
        try {
            textField.addActionListener(a -> {
                Address addr =Address.parse(textField.getText());
                if(checkStreetName(checkPostCity(addr))!=null)
                {
                    model.add(checkStreetName(checkPostCity(addr)));
                }

                textField.setText("");
            });

        } catch (Exception e) {

        }

    }

    public static Address checkPostCity(Address a) {
        Address newAdd = a;

        if (a.postcode() == null && a.city() != null) {
            //no Postcode
            //create new Address with new postcode
            String temp = r.getKeyFromValue(newAdd.city());
            String full = String.format("%s%s%s%s", newAdd.street(), newAdd.house(), temp, newAdd.city());
            newAdd = Address.parse(full);
        }
        else if (a.postcode() != null && a.city() == null) {
            //no city
            //create new Address with new city
            String temp = r.getPostcodes().get(newAdd.postcode());
            String full = String.format("%s%s%s%s%s%s", newAdd.street(), newAdd.house(), newAdd.house(), newAdd.floor(), newAdd.postcode(), temp);
            newAdd = Address.parse(full);
        }
        return newAdd;

    }
    public static Address checkStreetName(Address a)
    {
        Address newAdd = a;
        if(!(r.getStreetnames().contains(newAdd.street().replaceAll("\\s+",""))))
        {
            m.remove(newAdd);
            System.out.println("Street Name does not exist");
            return null;


        }
        return newAdd;
    }

    public static ArrayList<String> getSuggestions() {
        return suggestions;
    }
}
