/**
 * Created by benzali on 5/1/2018.
 */

class Controller {
    Model model;
    View view;

    Controller() {
        System.out.println ("Controller initialized");
    } //Controller()

    //invoked when a button is pressed
    public void addMonitor() {
        System.out.println("Controller: Adding a monitor");
        model.addData(view.getTextField());
    }

    public void removeMonitor() {
        System.out.println("Controller: Removing a monitor");
        model.removeData(view.getSelectedRow());
    }

    public void addModel(Model m){
        this.model = m;
        System.out.println("Controller: connected to Model");
    }

    public void addView(View v) {
        this.view = v;
        System.out.println("Controller: connected to View");
    }


} //Controller
