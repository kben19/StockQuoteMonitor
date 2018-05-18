package Model;

import stockquotetimelapse.*;
import java.util.List;

/**
 * Created by benzali on 5/15/2018.
 */
public class SQTimeLapseAdapter implements StockQuote {

    private StockQuoteTimeLapseServicePortType mySQPort;

    SQTimeLapseAdapter(){
        StockQuoteTimeLapseService mySQTimeLapse = new StockQuoteTimeLapseService();
        mySQPort = mySQTimeLapse.getStockQuoteTimeLapseServiceHttpSoap11Endpoint();
    }

    @Override
    public List getFieldNames(){
        return mySQPort.getFieldNames().getReturn();
    }

    @Override
    public List getQuote(String symbol){
        List aList = mySQPort.getStockQuote(symbol);
        //Convert into dollars
        for (int i : new int[]{1, 4, 5, 6, 7}) {
            aList.set(i, Double.parseDouble(aList.get(i).toString()) / 100);
        }
        return  aList;

    }
}
