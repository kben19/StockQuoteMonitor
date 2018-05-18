package Model;

/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
 */

//import stockquoteservice.*;
import ObserverPackage.Subject;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Model extends Subject {

    // Class attributes
    private List fieldNamesList;
    private ArrayList<ArrayList<Object>> SQData = new ArrayList<>();
    private ArrayList<StockQuote> myStockQuote = new ArrayList<>();

    // Model.Model Constructor
    public Model(){

        // Initialized the web service for stock quote
        System.out.println("Model initialized");

        myStockQuote.add(new SQServiceAdapter());
        myStockQuote.add(new SQTimeLapseAdapter());

        fieldNamesList = myStockQuote.get(1).getFieldNames();

        Timer updateTimer = new Timer();

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateData();   // Update data regularly
            }
        }, 5 * 60 * 1000, 5 * 60 * 1000 );  //set a timer to run update every 5 minutes

    } //Model.Model()

    // Add stock quote data into the table
    public void addData(String symbol, int type){
        List aList = myStockQuote.get(type).getQuote(symbol);
        if (isAdded(aList.get(0).toString())){
            //will not add a monitor if it already exists
            System.out.println("Model     : Monitor Added");
            dialogMessage(0, "Error", "Symbol is already exist", 2);
        }
        else if(aList.get(0).equals("invalid symbol submitted") && type == 1){
            //will not add a monitor if there is no such symbol in time lapse
            System.out.println("Model     : Monitor Added");
            dialogMessage(0, "Error", "Symbol does not exist\n" +
                    "Stock quote time lapse symbol that are only available are:\n" +
                    "RIO.AX, QAN.AX, ANZ.AX, CBA.AX, BHP.AX, NAB.AX", 2);
        }
        else if(aList.get(1).equals("Unset") && type == 0) {
            //will not add a monitor if there is no such symbol
            System.out.println("Model     : Monitor Added");
            dialogMessage(0, "Error", "Symbol does not exist\n" +
                    "For available symbols, please visit\nhttp://www.asx.com.au/asx/research/listedCompanies.do", 2);
        }
        else{
            //add monitor
            System.out.println("Model     : Monitor Added");
            ArrayList<Object> myList = convertList(aList);

            SQData.add(myList);

            notifyObservers(SQData);
        }


    }// addData()

    // Remove a certain stock quote data from table
    public void removeData(int index) {
        System.out.println("Model     : Monitor removed");
        SQData.remove(index);

        notifyObservers(SQData);
    }// removeData()

    // Update the entire stock quote data table
    public void updateData(){
        System.out.println("Model     : Updating monitors");
        for (int i = 0; i < SQData.size(); i++){
            List aList = myStockQuote.get(0).getQuote(SQData.get(i).get(0).toString());
            ArrayList<Object> temp = convertList(aList);
            SQData.set(i, temp);
        }

        notifyObservers(SQData);
    }// updateData()

    // Check if the a certain symbol is added in the table
    private boolean isAdded(String symbol){
        for (ArrayList<Object> dataSymbol : SQData){
            if(symbol.equals(dataSymbol.get(0))){
                return true;
            }
        }
        return false;
    }// checkData()

    // Convert list type into array list type
    private ArrayList<Object> convertList(List aList){
        ArrayList<Object> myList = new ArrayList<>();
        for (Object quote : aList){
            myList.add(quote);
        }
        return myList;
    }// convertList()

    // Field names accessor
    public List getFieldNames(){
        return fieldNamesList;
    }
} //Model.Model
