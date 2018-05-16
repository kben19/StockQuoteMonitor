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
        System.out.println(mySQPort.getSymbols().getReturn());
    }

    @Override
    public List getFieldNames(){
        return mySQPort.getFieldNames().getReturn();
    }

    @Override
    public List getQuote(String symbol){
        return  mySQPort.getStockQuote(symbol);

    }
}
