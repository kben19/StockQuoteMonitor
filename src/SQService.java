/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

public class SQService {

    public SQService() {

        //create Model and View
        Model myModel 	= new Model();
        View myView 	= new View(myModel);

        //create Controller. tell it about Model and View, initialise model
        Controller myController = new Controller();
        myController.addModel(myModel);
        myController.addView(myView);

        //attach observer
        myModel.attach(myView);

        //tell View about Controller
        myView.addController(myController);

    } //SQService()

} //SQService
