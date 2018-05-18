/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
 */

package ObserverPackage;

import java.util.ArrayList;

public interface Observer {
    void update(ArrayList<ArrayList<Object>> anObject);

    void updateChart(ArrayList<ArrayList<String[]>> aDataset);

    void showDialog(String title, String message, int type);
}// Observer
