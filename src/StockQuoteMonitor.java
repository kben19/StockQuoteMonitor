import Model.Model;

/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
 */

public class StockQuoteMonitor {

    public StockQuoteMonitor() { } //SQServiceAdapter()

    public void run() {
        //create Model.Model and View
        Model myModel 	= new Model();
        View myView 	= new View(myModel);

        //create Controller. tell it about Model.Model and View, initialise model
        Controller myController = new Controller();
        myController.addModel(myModel);
        myController.addView(myView);

        //attach observer
        myModel.attach(myView);

        //tell View about Controller
        myView.addController(myController);
    }
} //SQServiceAdapter
