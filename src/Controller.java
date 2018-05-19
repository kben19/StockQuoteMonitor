import Model.Model;
import Model.SQType;


/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
 */

class Controller implements java.awt.event.ActionListener{
    // Class variables
    Model model;
    View view;

    Controller() {
        System.out.println ("Controller initialized");
    } //Controller()

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e){
        if(e.getActionCommand().equals("Add")){
            addMonitor();
        }
        else if (e.getActionCommand().equals("Remove")){
            removeMonitor();
        }
        else if (e.getActionCommand().equals("Add Time Lapse")){
            addTimeLapse();
        }
        else if (e.getActionCommand().equals("View")){
            viewChart();
        }
    }//actionPerformed

    // Model.Model mutator
    public void addModel(Model m){
        this.model = m;
        System.out.println("Controller: connected to Model.Model");
    }//addModel

    // View mutator
    public void addView(View v) {
        this.view = v;
        System.out.println("Controller: connected to View");
    }//addView

    // Add stock quote data
    private void addMonitor() {
        System.out.println("Controller: Adding a monitor");
        model.addData(view.getTextField(), SQType.STOCK_QUOTE_WS);
    }//addMonitor

    // Remove stock quote data
    private void removeMonitor() {
        System.out.println("Controller: Removing a monitor");
        if (view.getSelectedRow() == view.getSelectedDataIndex()) {
            view.setSelectedDataIndex(-1);
        }
        else if (view.getSelectedRow() < view.getSelectedDataIndex()){
            view.setSelectedDataIndex(view.getSelectedDataIndex()-1);
        }
        model.removeData(view.getSelectedRow());
    }//removeMonitor

    private void addTimeLapse(){
        System.out.println("Controller: Adding a time lapse monitor");
        model.addData(view.getTextField(), SQType.STOCK_QUOTE_TIMELAPSE_WS);
    }

    private void viewChart(){
        System.out.println("Controller: View the selected monitor chart");
        view.setSelectedDataIndex(view.getSelectedRow());
        model.notifyCharts(model.getDataHistory());
    }


} //Controller
