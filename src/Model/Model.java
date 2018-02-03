package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class Model extends Observable implements Iterable<Address>{


    private static List<Address> addresses;

    public Model() {
        addresses = new ArrayList<>();
    }

    public void add(Address a) {
        addresses.add(a);

        System.out.println("Added address " + a);
        setChanged();
        notifyObservers();
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Address> iterator() {
        return addresses.iterator();
    }

}
