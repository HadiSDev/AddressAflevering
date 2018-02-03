package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Reader
{


    private static List<String> streetnames = new ArrayList<>();
    private static HashMap<String, String> postnumre = new HashMap<>();

    public static void readStreets() throws IOException
    {

        File street = new File("C:/Users/mulle/Desktop/AddressV2/src/Model/streetnames.txt");
        try
        {
            Scanner scannerStreet = new Scanner(street);

            //br returns as stream and convert it into a List
            while (scannerStreet.hasNextLine())
            {
                streetnames.add(scannerStreet.nextLine());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        streetnames.forEach(System.out::println);

    }

    public static void readPostnumbersCity() throws IOException
    {
        String filePath = "C:/Users/mulle/Desktop/AddressV2/src/Model/postnumre.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                String key = parts[0];
                String value = parts[1];
                postnumre.put(key, value);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }

        for (String key : postnumre.keySet())
        {
            System.out.println(key + " " + postnumre.get(key));
        }
        reader.close();
    }
    public static List<String> getStreetnames() {
        return streetnames;
    }

    public static HashMap<String, String> getPostnumre() {
        return postnumre;
    }
    public static String getKeyFromValue(String value)
    {
        for(String o : postnumre.keySet())
        {
            if(postnumre.get(o).equals(value))
            {
                return o;
            }
        }
        return null;
    }


}
