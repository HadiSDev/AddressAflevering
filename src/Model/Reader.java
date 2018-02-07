package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import Exception.AddressNotFoundException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Reader extends Observable
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
                streetnames.add(scannerStreet.nextLine().toLowerCase());
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
    public Address correctPostCity(Address a, HashMap<String, String> postcodeCity) {
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
        if(!(streetList.contains((newAdd.street().trim()).toLowerCase())))
        {
            m.remove(newAdd);
            throw new AddressNotFoundException("Streetname not Found!: ", newAdd.street());


        }
        return newAdd;
    }

    public void findSuggestedAddresses(String inputText) //TODO: this method is not workin corectly yet!!
    {
        final String regex = "(?<street>([0-9.]{1,3})?[a-zA-ZåæøÅÆØ. ]+)?\\s*(?<house>\\d{1,3}[A-Z]?)?\\s*[,]*\\s*(?<floor>(st|k(\\d{1,2})?|[1-9]{1,2}))?\\s*[,]*\\s*(?<side>(tv|th|mf|[1-9]{1,2}))?\\s*[,]*\\s*(?<postcode>\\d{4})?\\s*(?<city>[a-zA-ZåæøÅÆØ., ]+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputText);
        StringBuilder sb = new StringBuilder();
        int counter = 0;

        if(matcher.matches())
        {
            if(matcher.group("street")!=null)
            {
                try(Stream<String> streetStream = Files.lines(Paths.get("src//externals//streetnames.txt")).filter(s-> s.toLowerCase().contains(matcher.group("street").toLowerCase()))) {

                    streetStream.forEach(street->{
                        if(inputText.length()>3)
                        {

                            sb.append(street);
                            setChanged();
                            notifyObservers(sb.toString());
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(matcher.group("house")!= null)
                {
                    sb.append(matcher.group("house")+", ");
                    counter++;
                }
                if(matcher.group("floor")!=null)
                {
                    sb.append(matcher.group("floor")+", ");
                    counter++;

                }
                if(matcher.group("side")!=null)
                {
                    sb.append(matcher.group("side")+", ");
                    counter++;
                }
                if(matcher.group("postcode")!=null)
                {
                    checkPostCity(matcher.group("postcode"), sb);
                    counter++;
                }
                if(matcher.group("city")!=null)
                {
                    checkPostCity(matcher.group("city"), sb);
                    counter++;

                }
                setChanged();
                notifyObservers(sb.toString());


            }
        }

    }
    public void checkPostCity(String input, StringBuilder sb)
    {
        try (Stream<String> postCityStream = Files.lines(Paths.get("src//externals//postnumre.txt")).filter(s -> s.toLowerCase().contains(input.toLowerCase())))
        {
            postCityStream.forEach(e->{
                sb.append(e.toString());
                setChanged();
                notifyObservers(sb.toString());
            });


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public HashMap<String, String> getPostcodes() {
        return postcodes;
    }



}
