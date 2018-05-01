/**
 * Created by benzali on 5/1/2018.
 */
class Controller implements java.awt.event.ActionListener {

    Model model;
    View view;

    Controller() {
        System.out.println ("Controller()");
    } //Controller()

    //invoked when a button is pressed
    public void actionPerformed(java.awt.event.ActionEvent e){

        System.out.println("Controller: acting on Model");
        model.addData(view.getTextField());
        view.update();
    } //actionPerformed()


    public void addModel(Model m){
        System.out.println("Controller: adding model");
        this.model = m;
    } //addModel()

    public void addView(View v){
        System.out.println("Controller: adding view");
        this.view = v;
    } //addView()


} //Controller
