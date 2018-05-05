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
                updateData();
            }
        }, 5 * 60 * 1000, 5 * 60 * 1000 );  //set a timer to run update every 5 minutes

    } //Model()

    public void addData(String symbol){
        List aList = SQPort.getQuote(symbol);
        if (checkData(symbol)){
            System.out.println("Model     : Monitor cancelled");
            dialogMessage(0, "Error", "Symbol is already exist", 2);
        }
        else if(aList.get(1).equals("Unset")) {
            System.out.println("Model     : Monitor cancelled");
            dialogMessage(0, "Error", "Symbol does not exist", 2);
        }
        else{
            System.out.println("Model     : Monitor Added");
            ArrayList<Object> myList = convertList(aList);

            SQData.add(myList);

            notifyObservers(SQData);
        }


    }// addData()

    public void removeData(int index) {
        System.out.println("Model       : Monitor removed");
        SQData.remove(index);

        notifyObservers(SQData);
    }// removeData()

    private boolean checkData(String symbol){
        for (ArrayList<Object> dataSymbol : SQData){
            if(symbol.equals(dataSymbol.get(0))){
                return true;
            }
        }
        return false;
    }// checkData()

    public void updateData(){
        System.out.println("Model     : Updating monitors");
        for (int i = 0; i < SQData.size(); i++){
            List aList = SQPort.getQuote(SQData.get(i).get(0).toString());
            ArrayList<Object> temp = convertList(aList);
            SQData.set(i, temp);
        }

        notifyObservers(SQData);
    }// updateData

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
