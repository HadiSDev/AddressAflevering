package Controller;

import java.util.*;

import Model.Model;
import Model.Address;

import javax.swing.*;
import java.io.IOException;

import Model.Reader;
import View.View;

import static javafx.application.Application.launch;

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

    public static void sendInput(String input, Model model) {
        try {
            Address addr =Address.parse(input);
            if(r.checkStreetName(r.correctPostCity(addr, zipCityList),streetList,m)!=null)
            {
                model.add(r.checkStreetName(r.correctPostCity(addr, zipCityList),streetList,m));
            }

        } catch (Exception e) {

        }

    }




    public static ArrayList<String> getSuggestions() {
        return suggestions;
    }
}
