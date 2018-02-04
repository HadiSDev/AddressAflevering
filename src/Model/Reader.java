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


    private List<String> streetnames = new ArrayList<>();
    private HashMap<String, String> postcodes = new HashMap<>();

    public void readStreets() throws IOException
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

    public void readPostCodes() throws IOException
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
    public List<String> getStreetnames() {
        return streetnames;
    }
    public String getKeyFromValue(String value)
    {
        for(String o : postcodes.keySet())
        {
            if(postcodes.get(o).equalsIgnoreCase(value))
            {
                return o;
            }
        }
        return null;
    }
    public Address checkPostCity(Address a, HashMap<String, String> postcodeCity) {
        Address newAdd = a;

        if (a.postcode() == null && a.city() != null) {
            //no Postcode
            //create new Address with new postcode
            String temp = getKeyFromValue(newAdd.city());
            String full = String.format("%s%s%s%s", newAdd.street(), newAdd.house(), temp, newAdd.city());
            newAdd = Address.parse(full);
        }
        else if (a.postcode() != null && a.city() == null) {
            //no city
            //create new Address with new city
            String temp = postcodeCity.get(newAdd.postcode());
            String full="";
            if(newAdd.floor()!=null&&newAdd.side()!=null)
            {
                full = String.format("%s%s%s%s%s%s", newAdd.street(), newAdd.house(), newAdd.floor(), newAdd.side(), newAdd.postcode(), temp);
            }
            else if(newAdd.floor()==null&&newAdd.side()!=null)
            {
                full = String.format("%s%s%s%s%s", newAdd.street(), newAdd.house(), newAdd.side(), newAdd.postcode(), temp);
            }
            else if(newAdd.floor()!=null&&newAdd.side()==null)
            {
                full = String.format("%s%s%s%s%s", newAdd.street(), newAdd.house(), newAdd.floor(), newAdd.postcode(), temp);
            }
            else
            {
                full = String.format("%s%s%s%s", newAdd.street(), newAdd.house(), newAdd.postcode(), temp);
            }
            newAdd = Address.parse(full);
        }
        return newAdd;

    }
    public Address checkStreetName(Address a, ArrayList<String> streetList, Model m)
    {
        Address newAdd = a;
        if(!(streetList.contains(newAdd.street().replaceAll("\\s+",""))))
        {
            m.remove(newAdd);
            System.out.println("Street Name does not exist");
            return null;


        }
        return newAdd;
    }

    public HashMap<String, String> getPostcodes() {
        return postcodes;
    }



}
