/**
 * Created by benzali on 5/1/2018.
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

        //tell View about Controller
        myView.addController(myController);

    } //SQService

} //SQService
