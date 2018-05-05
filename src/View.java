/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 3 May 2018
 */

import ObserverPackage.Observer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.List;
import java.util.ArrayList;


class View implements Observer {

    //class attributes
    private TextField myTextField;
    private Button addMonitorButton;
    private Button removeMonitorButton;
    private JTable table;
    private Frame frame;
    private StockMouseListener stockMouseListener;


    View(Model aModel) {
        System.out.println("View initialized");
        stockMouseListener = new StockMouseListener();

        //local attributes
        frame = new Frame("Stock Quote Service");

        JPanel header = renderHeader();
        JPanel body = renderBody(aModel.getFieldNames());
        JPanel footer = renderFooter();

        frame.add(header, BorderLayout.NORTH);
        frame.add(body, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.addWindowListener(new CloseListener());
        frame.addMouseListener(stockMouseListener);
        frame.setSize(600,300);
        frame.setLocation(100,100);
        frame.setVisible(true);
    } //View()

    private JPanel renderHeader() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        header.add(new Label("Symbol:"));

        //add text field to input symbols
        myTextField = new TextField(5);
        header.add(myTextField);

        addMonitorButton = new Button("Add");
        header.add(addMonitorButton);

        return header;
    }

    private JPanel renderBody(List fieldNames) {
        JPanel body = new JPanel();

        String[] columnNames = new String[fieldNames.size()];

        for (int i = 0; i < fieldNames.size(); i++){
            columnNames[i] = fieldNames.get(i).toString();
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

        body.setLayout(new BorderLayout());
        body.add(table.getTableHeader(), BorderLayout.PAGE_START);
        body.add(table, BorderLayout.CENTER);

        return body;
    }

    private JPanel renderFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));

        removeMonitorButton = new Button("Remove");
        removeMonitorButton.setEnabled(false);
        footer.add(removeMonitorButton);

        return footer;
    }

    public String getTextField(){
        return this.myTextField.getText();
    }

    public int getSelectedRow() {
        return this.table.getSelectedRow();
    }

    private void updateRemoveButton() {
        if (getSelectedRow() == -1) {
            removeMonitorButton.setEnabled(false);
        } else {
            removeMonitorButton.setEnabled(true);
        }
    }// updateRemoveButton()

    @Override
    public void update(ArrayList<ArrayList<Object>> anObject) {      //called every time observers receive notification from subject
        // Get latest stock quote data
        Object[][] data = new String[anObject.size()][4];

        // Clear table row data
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Insert all row data
        for (int i = 0; i < anObject.size(); i++){
            for(int j = 0; j < 4; j++){
                data[i][j] = anObject.get(i).get(j);
            }
            model.addRow(data[i]);
        }
        updateRemoveButton();
    }// update()

    @Override
    public void message(String title, String message, int type){
        System.out.println("View      : Show message dialog");
        JOptionPane.showMessageDialog(frame, message, title, type);
    }// message()

    public void addController(ActionListener controller) {
        System.out.println("View      : adding controller");

        //add listener for adding monitor
        addMonitorButton.addActionListener(controller);

        //add listener for removing monitor
        removeMonitorButton.addActionListener(controller);
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
    }//StockMouseListener
} //View
