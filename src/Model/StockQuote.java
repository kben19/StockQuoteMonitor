package Model;

import java.util.List;

/**
 * Created by benzali on 5/15/2018.
 */
public interface StockQuote {

    List getFieldNames();
    List getQuote(String symbol);
}
