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
                //DISINIIII
            }
        }, 60 * 1000 * 5 );
    } //Model()

    public void addData(String symbol){
        List aList = SQPort.getQuote(symbol);
        ArrayList<Object> myList = new ArrayList<Object>();
        for (Object quote : aList){
            myList.add(quote);
        }
        SQData.add(myList);

        notifyObservers();
    }

    public void removeData(int index) {
        SQData.remove(index);

        notifyObservers();
    }

    public List getFieldNames(){
        return fieldNamesList;
    }

    public ArrayList<ArrayList<Object>> getStockQuote(){
        return SQData;
    }
} //Model
