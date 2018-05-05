/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

package ObserverPackage;

import java.util.ArrayList;

public interface Observer {
    void update(ArrayList<ArrayList<Object>> anObject);
    void message(String title, String message, int type);
}// Observer
