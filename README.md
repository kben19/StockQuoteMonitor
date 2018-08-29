# Stock Quote Monitor Application
Stock Quote Monitor application to monitor stock quote data by inserting a stock 
symbol into the program. The program obtains data from StockQuoteWS web service 
and will show this information in an appropriate format to the user.

## Team Members
#### Kelvin Benzali 
- Student ID: 26445468
- Student email: kben19@student.monash.edu

#### Andre Christian
- Student ID: 26445379
- Student email: achr18@student.monash.edu

## Functionality
- Add a stock monitor for stock quote service
- Add a stock monitor for stock quote time lapse
- Maintain multiple stocks simultaneously
- Remove the selected monitored stock
- View the history graph of the selected stock
- Update the stock data every 5 minutes
- Notification of stock value changes in form of row color

More functionalities may be implemented in the future

## Libraries
- Jfreechart-1.0.19
- jcommon-1.0.23
- java 1.8

## Program Guide
The program provides the executables version Assignment2.jar.
It can run without any program dependency.

- The program interface consist of a text field for stock symbol input
- The button add and add time lapse beside the text field is for adding new monitor
- The table shows the current monitored stocks
- The remove button is enabled once a row is selected and its purposes is to remove the selected monitor
- The view button is enabled once a row is selected and its purposes is to view the history graph of the selected monitor
- The line graph shows the history of the stock value, x-axis represent the time, y-axis represent the stock value in Australian dollars
