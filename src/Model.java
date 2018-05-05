/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

import stockquoteservice.*;
import ObserverPackage.Subject;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Model extends Subject {

    private List fieldNamesList;
    private ArrayList<ArrayList<Object>> SQData = new ArrayList<>();
    private StockQuoteWSPortType SQPort;

    public Model(){

        System.out.println("Model initialized");
        StockQuoteWS SQservice = new StockQuoteWS();
        SQPort = SQservice.getStockQuoteWSSOAP11PortHttp();
        fieldNamesList = SQPort.getFieldNames().getReturn();

        Timer updateTimer = new Timer();

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Model: Updating quotes");
                updateData();
            }
        }, 5 * 60 * 1000, 5 * 60 * 1000 );  //set a timer to run update every 5 minutes

    } //Model()

    public void addData(String symbol){
        List aList = SQPort.getQuote(symbol);
        ArrayList<Object> myList = convertList(aList);

        SQData.add(myList);

        notifyObservers(SQData);
    }

    public void removeData(int index) {
        SQData.remove(index);

        notifyObservers(SQData);
    }

    public void updateData(){
        for (int i = 0; i < SQData.size(); i++){
            List aList = SQPort.getQuote(SQData.get(i).get(0).toString());
            ArrayList<Object> temp = convertList(aList);
            SQData.set(i, temp);
        }
    }

    private ArrayList<Object> convertList(List aList){
        ArrayList<Object> myList = new ArrayList<>();
        for (Object quote : aList){
            myList.add(quote);
        }
        return myList;
    }

    public List getFieldNames(){
        return fieldNamesList;
    }
} //Model
