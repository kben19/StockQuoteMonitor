package Model;

import ObserverPackage.Subject;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * authors: Andre Christian & Kelvin Benzali
 */
public class Model extends Subject {
    // constants
    private final int SQHISTORY_LIMIT = 10;
    private final int WS_UPDATE_DURATION = 300;

    // Class attributes
    private List<String> fieldNamesList;
    private ArrayList<ArrayList<String>> SQData;
    private ArrayList<ArrayList<String[]>> SQHistory;
    private SQServiceAdapter myStockQuote;
    private int WSUpdateCounter;

    // Model.Model Constructor
    public Model() {
        SQData = new ArrayList<>();
        SQHistory = new ArrayList<>();
        myStockQuote = new SQServiceAdapter();
        WSUpdateCounter = 0;

        // Initialized the web service for stock quote
        System.out.println("Model initialized");

        fieldNamesList = myStockQuote.getFieldNames();

        Timer updateTimer = new Timer();

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                WSUpdateCounter += 5;
                updateData();   // Update data regularly
            }
        }, 5000, 5000); //set a timer to run upda te every 5 seconds

    } //Model.Model()

    // Add stock quote data into the table
    public void addData(String symbol, SQType type){
        List<String> aList = myStockQuote.getQuote(symbol, type);

        if (isAdded(aList.get(0))){
            //will not add a monitor if it already exists
            dialogMessage(0, "Error", "Symbol already exists", 2);
        }
        else if(aList.get(0).equals("invalid symbol submitted") && type == SQType.STOCK_QUOTE_TIMELAPSE_WS){
            //will not add a monitor if there is no such symbol in time lapse
            dialogMessage(0, "Error", "Symbol does not exist\n" +
                    "Available stock quote time lapse symbols are:\n" +
                    "RIO.AX, QAN.AX, ANZ.AX, CBA.AX, BHP.AX, NAB.AX", 2);
        }
        else if(aList.get(1).equals("Unset") && type == SQType.STOCK_QUOTE_WS) {
            //will not add a monitor if there is no such symbol
            dialogMessage(0, "Error", "Symbol does not exist\n" +
                    "For available symbols, please visit\nhttp://www.asx.com.au/asx/research/listedCompanies.do", 2);
        }
        else{
            //add monitor
            System.out.println("Model     : Monitor Added");

            ArrayList<String> myList = convertList(aList);
            ArrayList<String[]> myHistory = new ArrayList<>();
            myHistory.add(new String[]{myList.get(1), myList.get(3)});

            SQData.add(myList);

            //In case of update failed, generate the default data
            if (myList.get(3).contains("Update failed")) {
                myHistory.remove(0);
                myHistory.add(new String[]{"0", "0:00"});
            }

            SQHistory.add(myHistory);

            notifyObservers(SQData);
        }


    }// addData()

    // Remove a certain stock quote data from table
    public void removeData(int index) {
        System.out.println("Model     : Monitor removed");
        SQData.remove(index);
        SQHistory.remove(index);

        notifyObservers(SQData);
        notifyCharts(SQHistory);
    }// removeData()

    // Update the entire stock quote data table
    private void updateData(){
        System.out.println("Model     : Updating monitors");
        for (int i = 0; i < SQData.size(); i++){
            String symbolString = SQData.get(i).get(0);
            SQType type = (symbolString.contains(".")) ? SQType.STOCK_QUOTE_TIMELAPSE_WS : SQType.STOCK_QUOTE_WS;

            //update if monitory type is time lapse or when 5 minutes has passed
            if (type == SQType.STOCK_QUOTE_TIMELAPSE_WS || WSUpdateCounter == WS_UPDATE_DURATION) {
                List<String> aList = myStockQuote.getQuote(symbolString, type);

                ArrayList<String> temp = convertList(aList);
                SQData.set(i, temp);

                //In case of successful update
                if (!temp.get(3).contains("Update failed")) {
                    //In case of previous failed update, remove the default data
                    if (SQHistory.get(i).get(0) == new String[]{"0", "0:00"}) {
                        SQHistory.get(i).remove(0);
                    }
                    SQHistory.get(i).add(new String[]{temp.get(1), temp.get(3)});

                    // maintain only 10 entries of history
                    if (SQHistory.get(i).size() > this.SQHISTORY_LIMIT) {
                        SQHistory.get(i).remove(0);
                    }
                }
            }
        }

        // reset timer that updates StockQuoteWS
        if(WSUpdateCounter == WS_UPDATE_DURATION) {
            WSUpdateCounter = 0;
        }

        notifyObservers(SQData);
        notifyCharts(SQHistory);
    }// updateData()

    public ArrayList<ArrayList<String[]>> getDataHistory() {
        return SQHistory;
    }// getDataHistory()

    // Check if the a certain symbol is added in the table
    private boolean isAdded(String symbol){
        for (ArrayList<String> dataSymbol : SQData){
            if(symbol.equals(dataSymbol.get(0))){
                return true;
            }
        }
        return false;
    }// checkData()

    // Convert list type into array list type
    private ArrayList<String> convertList(List<String> aList){
        ArrayList<String> myList = new ArrayList<>();
        for (String quote : aList){
            myList.add(quote);
        }
        return myList;
    }// convertList()

    // Field names accessor
    public List<String> getFieldNames(){
        return fieldNamesList;
    }
} //Model.Model
