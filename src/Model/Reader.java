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
    private static HashMap<String, String> postcodes = new HashMap<>();

    public static void readStreets() throws IOException
    {

        File street = new File("src\\Externals\\streetnames.txt");
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

/*        streetnames.forEach(System.out::println);*/

    }

    public static void readPostCodes() throws IOException
    {
        String filePath = "src\\Externals\\postnumre.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                String key = parts[0];
                String value = parts[1];
                postcodes.put(key, value);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }

/*        for (String key : postcodes.keySet())
        {
            System.out.println(key + " " + postcodes.get(key));
        }*/
        reader.close();
    }
    public static List<String> getStreetnames() {
        return streetnames;
    }

    public static HashMap<String, String> getPostcodes() {
        return postcodes;
    }
    public static String getKeyFromValue(String value)
    {
        for(String o : postcodes.keySet())
        {
            if(postcodes.get(o).equals(value))
            {
                return o;
            }
        }
        return null;
    }


}
