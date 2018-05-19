/**
 * authors: Andre Christian & Kelvin Benzali
 * last modified: 6 May 2018
 */

import Model.Model;
import ObserverPackage.Observer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.List;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;


class View implements Observer {

    //class attributes
    private TextField myTextField;
    private Button addMonitorButton, addTimeLapseButton;
    private Button removeMonitorButton, viewMonitorButton;
    private JTable table;
    private Frame frame;
    private StockMouseListener stockMouseListener;
    private JFreeChart myChart;
    private CategoryPlot myPlot;
    private int selectedDataIndex;

    // View constructor
    View(Model aModel) {
        System.out.println("View initialized");
        stockMouseListener = new StockMouseListener();
        selectedDataIndex = -1;

        //local attributes
        frame = new Frame("Stock Quote Service");

        //Initialized panel
        JPanel header = renderHeader();
        JPanel body = renderBody(aModel.getFieldNames());
        JPanel footer = renderFooter();

        //Initialized main frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(body, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        frame.addWindowListener(new CloseListener());
        frame.addMouseListener(stockMouseListener);
        frame.setSize(900,900);
        frame.setLocation(100,100);
        frame.setVisible(true);
    } //View()

    //Initialized header panel
    private JPanel renderHeader() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        header.add(new Label("Symbol:"));

        //add text field to input symbols
        myTextField = new TextField(5);
        header.add(myTextField);

        addMonitorButton = new Button("Add");
        addTimeLapseButton = new Button("Add Time Lapse");
        header.add(addMonitorButton);
        header.add(addTimeLapseButton);

        return header;
    }// renderHeader()

    //Initialized body panel
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

        JScrollPane js = new JScrollPane(table);    // Enable the table to be scrollable

        body.setLayout(new BorderLayout());
        body.add(table.getTableHeader(), BorderLayout.PAGE_START);
        body.add(js);

        return body;
    }// renderBody()

    //Initialized footer panel (buttons and line graph)
    private JPanel renderFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(new BorderLayout());

        removeMonitorButton = new Button("Remove");
        removeMonitorButton.setEnabled(false);
        viewMonitorButton = new Button("View");
        viewMonitorButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(removeMonitorButton);
        buttonPanel.add(viewMonitorButton);
        JPanel chart = renderChart();

        footer.add(buttonPanel, BorderLayout.NORTH);
        footer.add(chart, BorderLayout.CENTER);

        return footer;
    }// renderFooter()

    //Initialized the line graph chart
    private JPanel renderChart() {
        JPanel chart = new JPanel();
        chart.setLayout(new BorderLayout());
        myChart = ChartFactory.createLineChart(
                "", "Time", "Value",
                new DefaultCategoryDataset()
        );
        myPlot = myChart.getCategoryPlot();
        myPlot.setBackgroundPaint(Color.WHITE);

        // Refresh the axis range
        NumberAxis numPlot = (NumberAxis) myPlot.getRangeAxis();
        numPlot.setAutoRangeIncludesZero(false);
        numPlot.setAutoRange(true);

        ChartPanel CP = new ChartPanel(myChart);
        CP.setPreferredSize(new java.awt.Dimension(900, 400));
        chart.add(CP);
        return chart;
    }// renderChart()

    // Text field accessor
    public String getTextField(){
        return this.myTextField.getText();
    }

    // Return the selected row index
    public int getSelectedRow() {
        return this.table.getSelectedRow();
    }

    // selectedDataIndex accessor
    public int getSelectedDataIndex() { return selectedDataIndex; }

    // selectedDataIndex mutator
    public void setSelectedDataIndex(int index){ selectedDataIndex = index; }

    // Update the enable property of remove button
    private void updateButtonVisibility(Button aButton) {
        if (getSelectedRow() == -1) {
            aButton.setEnabled(false);
        } else {
            aButton.setEnabled(true);
        }
    }// updateRemoveButton()

    // Attach listener event into the buttons
    public void addController(ActionListener controller) {
        System.out.println("View      : adding controller");

        //add listener for adding monitor
        addMonitorButton.addActionListener(controller);
        addTimeLapseButton.addActionListener(controller);

        //add listener for removing monitor
        removeMonitorButton.addActionListener(controller);
        //add listener for viewing monitor
        viewMonitorButton.addActionListener(controller);
    } //addController()

    // Function called every time observers receive update to the selected chart
    public void updateChart(ArrayList<ArrayList<String[]>> anArray) {
        DefaultCategoryDataset aDataset = new DefaultCategoryDataset();
        if (selectedDataIndex != -1 && anArray.size() > 0) {
            ArrayList<String[]> selectedData = anArray.get(getSelectedDataIndex());     //get the selected dataset row
            for (String[] item : selectedData) {
                aDataset.addValue(Double.parseDouble(item[0]), "Stock Value", item[1]); //update data set
            }
            myChart.setTitle(table.getModel().getValueAt(selectedDataIndex, 0).toString()); //update plot title
        }
        else{
            myChart.setTitle("");
        }

        myPlot.setDataset(aDataset);

    }// updateChart()

    // Function called every time observers receive updates from model
    @Override
    public void update(ArrayList<ArrayList<String>> anObject) {
        // Get latest stock quote data
        String[][] data = null;
        if (anObject.size() > 0) {  // Prevent the out of index error bug when data is empty
            data = new String[anObject.size()][anObject.get(0).size()];
        }

        // Clear table row data
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Insert all row data
        for (int i = 0; i < anObject.size(); i++){
            for(int j = 0; j < anObject.get(i).size(); j++){
                data[i][j] = anObject.get(i).get(j).toString();
            }
            model.addRow(data[i]);
        }

        //update the button visibility
        updateButtonVisibility(removeMonitorButton);
        updateButtonVisibility(viewMonitorButton);

    }// update()

    // Function called every time observers received message to show dialog from model
    @Override
    public void showDialog(String title, String message, int type){
        System.out.println("View      : Show message dialog");
        JOptionPane.showMessageDialog(frame, message, title, type);
    }// showDialog

    // Close button listener
    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        } //windowClosing()
    } //CloseListener

    // Mouse Listener
    public class StockMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            updateButtonVisibility(removeMonitorButton);
            updateButtonVisibility(viewMonitorButton);
        }
    }//StockMouseListener
} //View
