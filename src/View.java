/**
 * Created by benzali on 5/1/2018.
 */
import java.awt.*;
import java.awt.event.WindowEvent;	//for CloseListener()
import java.awt.event.WindowAdapter;	//for CloseListener()
import java.awt.event.ActionListener;	//for addController()
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

class View {

    //class attributes
    private TextField myTextField;
    private Button button;
    private Model myModel;
    private JTable table;

    private Object[][] data = {};
    private Object[] columnNames;

    View(Model aModel) {
        System.out.println("View()");
        myModel = aModel;

        //local attributes
        Frame frame = new Frame("Stock Quote Service");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(new Label("Symbol:"));

        myTextField = new TextField(5);
        panel1.add(myTextField);

        button = new Button("Add");
        panel1.add(button);

        // Get field names from model
        List aList = myModel.getFieldNames();
        Object[] columnNames = new Object[aList.size()];

        for (int i = 0; i < aList.size(); i++){
            columnNames[i] = aList.get(i).toString();
        }

        // Initialize table
        table = new JTable(new DefaultTableModel(columnNames, 0));
        table.setEnabled(false);    //non writable
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.add(table.getTableHeader(), BorderLayout.PAGE_START);
        panel2.add(table, BorderLayout.CENTER);


        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.addWindowListener(new CloseListener());
        frame.setSize(600,300);
        frame.setLocation(100,100);
        frame.setVisible(true);

    } //View()

    public String getTextField(){
        return myTextField.getText();
    }

    // Called from the Model
    public void update() {
        // Get latest stock quote data
        ArrayList<ArrayList<Object>> aList = myModel.getQuote();
        Object[][] data = new String[aList.size()][4];

        // Clear table row data
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Insert all row data
        for (int i = 0; i < aList.size(); i++){
            for(int j = 0; j < 4; j++){
                data[i][j] = aList.get(i).get(j);
            }
            model.addRow(data[i]);
        }


    } //update()

    public void addModel(Model m){
        myModel = m;
    }


    public void addController(ActionListener controller){
        System.out.println("View      : adding controller");
        button.addActionListener(controller);	//add listener to button using controller class
    } //addController()

    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        } //windowClosing()
    } //CloseListener

} //View
