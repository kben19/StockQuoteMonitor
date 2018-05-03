/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

import ObserverPackage.Observer;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.List;
import java.util.ArrayList;


class View implements Observer {

    //class attributes
    private TextField myTextField;
    private Button addMonitorButton;
    private Button removeMonitorButton;
    private Model myModel;
    private JTable table;
    private Controller myController;

    View(Model aModel) {
        System.out.println("View initialized");
        StockMouseListener stockMouseListener = new StockMouseListener();
        myModel = aModel;

        //local attributes
        Frame frame = new Frame("Stock Quote Service");

        //create header
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        header.add(new Label("Symbol:"));

        //add text field to input symbols
        myTextField = new TextField(5);
        header.add(myTextField);

        addMonitorButton = new Button("Add");
        header.add(addMonitorButton);

        // Get field names from model
        List aList = myModel.getFieldNames();
        Object[] columnNames = new Object[aList.size()];

        for (int i = 0; i < aList.size(); i++){
            columnNames[i] = aList.get(i).toString();
        }

        // Initialize table
        table = new JTable(new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //make cells non-editable
                return false;
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    //allow only one selection at a time
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.addMouseListener(stockMouseListener);

        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());
        body.add(table.getTableHeader(), BorderLayout.PAGE_START);
        body.add(table, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));

        removeMonitorButton = new Button("Remove");
        removeMonitorButton.setEnabled(false);
        footer.add(removeMonitorButton);

        frame.add(header, BorderLayout.NORTH);
        frame.add(body, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.addWindowListener(new CloseListener());
        frame.addMouseListener(stockMouseListener);
        frame.setSize(600,300);
        frame.setLocation(100,100);
        frame.setVisible(true);

    } //View()

    public String getTextField(){
        return this.myTextField.getText();
    }

    public int getSelectedRow() {
        return this.table.getSelectedRow();
    }

    public void updateRemoveButton() {
        if (getSelectedRow() == -1) {
            removeMonitorButton.setEnabled(false);
        } else {
            removeMonitorButton.setEnabled(true);
        }
    }

    @Override
    public void update() {      //called every time observers receive notification from subject
        // Get latest stock quote data
        ArrayList<ArrayList<Object>> aList = myModel.getStockQuote();
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
        updateRemoveButton();
    }

    public void addController(Controller controller) {
        System.out.println("View      : adding controller");

        myController = controller;

        //add listener for adding monitor
        addMonitorButton.addActionListener((ActionEvent e) -> {
            myController.addMonitor();
        });

        //add listener for removing monitor
        removeMonitorButton.addActionListener((ActionEvent e) -> {
            myController.removeMonitor();
        });
    } //addController()

    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        } //windowClosing()
    } //CloseListener

    public class StockMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            updateRemoveButton();
        }
    }
} //View
