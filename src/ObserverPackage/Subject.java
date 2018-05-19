/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
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

    public void notifyObservers(ArrayList<ArrayList<String>> anObject) {
        for( Observer o : observers ) {
            o.update(anObject);
        }
    }// notifyObservers()

    public void notifyCharts(ArrayList<ArrayList<String[]>> aDataset) {
        for ( Observer o : observers ) {
            o.updateChart(aDataset);
        }
    }

    public void dialogMessage(int obs, String title, String message, int type){
        observers.get(obs).showDialog(title, message, type);
    }// dialogMessage()
}// Subject
