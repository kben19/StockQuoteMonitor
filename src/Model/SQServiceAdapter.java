package Model;

/**
 * Created by benzali on 5/15/2018.
 */

import stockquoteservice.*;
import java.util.List;

public class SQServiceAdapter implements StockQuote {

    private StockQuoteWSPortType mySQPort;

    public SQServiceAdapter(){
        StockQuoteWS mySQService = new StockQuoteWS();
        mySQPort = mySQService.getStockQuoteWSSOAP11PortHttp();
    }

    @Override
    public List getFieldNames(){
        return mySQPort.getFieldNames().getReturn();
    }

    @Override
    public List getQuote(String symbol){
        List tempList = mySQPort.getQuote(symbol);
        for (int i = 0; i < 5; i++){
            tempList.add("None");
        }
        return tempList;
    }
}
