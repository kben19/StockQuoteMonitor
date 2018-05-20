package Model;

import stockquoteservice.*;
import stockquotetimelapse.StockQuoteTimeLapseService;
import stockquotetimelapse.StockQuoteTimeLapseServicePortType;
import java.util.List;

/**
 * authors: Andre Christian & Kelvin Benzali
 */
public class SQServiceAdapter {

    private StockQuoteWSPortType mySQWSPort;
    private StockQuoteTimeLapseServicePortType mySQTimeLapsePort;

    public SQServiceAdapter(){
        StockQuoteWS mySQService = new StockQuoteWS();
        StockQuoteTimeLapseService mySQTimeLapse = new StockQuoteTimeLapseService();
        mySQWSPort = mySQService.getStockQuoteWSSOAP11PortHttp();
        mySQTimeLapsePort = mySQTimeLapse.getStockQuoteTimeLapseServiceHttpSoap11Endpoint();
    }

    public List<String> getFieldNames(){
        return mySQTimeLapsePort.getFieldNames().getReturn();
    }

    public List<String> getQuote(String symbol, SQType type){
        List<String> quoteData;
        if(type == SQType.STOCK_QUOTE_WS) {
            quoteData = mySQWSPort.getQuote(symbol);

            //Add the gap column with None
            for (int i = 0; i < 5; i++){
                quoteData.add("None");
            }
        } else {
            quoteData = mySQTimeLapsePort.getStockQuote(symbol);
            if (quoteData.size() > 1) {
                for (int i : new int[]{1, 4, 5, 6, 7}) {
                    Double newVal = Double.parseDouble(quoteData.get(i)) / 100;
                    quoteData.set(i, newVal.toString());
                }
            }
        }

        return quoteData;
    }
}
