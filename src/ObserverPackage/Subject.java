/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

package ObserverPackage;

import java.util.ArrayList;

public abstract class Subject {
    ArrayList<Observer> observers = new ArrayList<>();

    public Subject() {}

    public void attach(Observer o) {
        this.observers.add(o);
    }

    public void detach(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers(ArrayList<ArrayList<Object>> anObject) {
        for( Observer o : observers ) {
            o.update(anObject);
        }
    }
}
