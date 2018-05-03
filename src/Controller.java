/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

class Controller implements java.awt.event.ActionListener{
    Model model;
    View view;

    Controller() {
        System.out.println ("Controller initialized");
    } //Controller()

    public void actionPerformed(java.awt.event.ActionEvent e){
        if(e.getActionCommand().equals("Add")){
            addMonitor();
        }
        else if (e.getActionCommand().equals("Remove")){
            removeMonitor();
        }
    }

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
