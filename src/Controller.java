/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
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
    }//actionPerformed

    public void addModel(Model m){
        this.model = m;
        System.out.println("Controller: connected to Model");
    }//addModel

    public void addView(View v) {
        this.view = v;
        System.out.println("Controller: connected to View");
    }//addView

    private void addMonitor() {
        System.out.println("Controller: Adding a monitor");
        model.addData(view.getTextField());
    }//addMonitor

    private void removeMonitor() {
        System.out.println("Controller: Removing a monitor");
        model.removeData(view.getSelectedRow());
    }//removeMonitor


} //Controller
