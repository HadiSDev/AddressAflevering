package Model;

import java.util.regex.*;
import Exception.AddressNotFoundException;

public class Address {
    private final String street, house, floor, side, postcode, city;


    private Address(String _street, String _house, String _floor, String _side, String _postcode, String _city)
    {
        this.street = _street;
        this.house = _house;
        this.floor = _floor;
        this.side = _side;
        this.postcode = _postcode;
        this.city = _city;
    }

    @Override
    public String toString() {
        return (street != null ? street + " " : "") +
                (house != null ? house + " " : "") +
                (floor != null ? floor + " " : "") +
                (side != null ? side + " " : "") +
                (postcode != null ? postcode + " " : "") +
                (city != null ? city + " " : "");
    }

    public static class Builder {
        private String street = "Unknown", house, floor, side, postcode, city;
        public Builder street(String _street) { street = _street; return this; }
        public Builder house(String _house)   { house = _house;   return this; }
        public Builder floor(String _floor)   { floor = _floor;   return this; }
        public Builder side(String _side)     { side = _side;     return this; }
        public Builder postcode(String _postcode) { postcode = _postcode; return this; }
        public Builder city(String _city)     { city = _city;     return this; }
        public Address build()
        {

            return new Address(street, house, floor, side, postcode, city);
        }
    }

    public String street()   { return street; }
    public String house()    { return house; }
    public String floor()    { return floor; }
    public String side()     { return side; }
    public String postcode() { return postcode; }
    public String city()     { return city; }




    public static Address parse(String s) {
        final String regex = "(?<street>([0-9.]{1,3})?[a-zA-ZåæøÅÆØ. ]+)?\\s*(?<house>\\d{1,3}[A-Z]?)?\\s*[,]*\\s*(?<floor>(st|k(\\d{1,2})?|[1-9]{1,2}))?\\s*[,]*\\s*(?<side>(tv|th|mf|[1-9]{1,2}))?\\s*[,]*\\s*(?<postcode>\\d{4})?\\s*(?<city>[a-zA-ZåæøÅÆØ., ]+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        Builder b = new Builder();
        if (matcher.matches()) {
            return b.city(matcher.group("city")).
                    house(matcher.group("house")).
                    floor(matcher.group("floor")).
                    side(matcher.group("side")).
                    postcode(matcher.group("postcode")).
                    street(matcher.group("street").trim()).build();
        }

        throw new AddressNotFoundException("Address not typed correctly!",s);
    }
}
