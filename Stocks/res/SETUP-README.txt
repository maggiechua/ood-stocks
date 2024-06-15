SETUP Instructions:

1. Within the terminal, locate the location of the JAR file- within the provided res folder.
2. Ensure your data folder is accessible and is within the res folder.
3. Run the JAR program ($ java -jar Stocks.jar)

Additional Note: During the testing phase, we noticed that accessing the stock data according to the
 file path had discrepancies between Mac vs. Windows OS. Please check Line 35 in FileParser to
 ensure your OS is set properly. Lines 28-34 have further instructions.
    Difference paths:
    - Mac: "/res/data/"
    - Windows: "/Stocks/res/data/"
    Please enter either "mac" or "windows" in the return for getOSType method.

Example run instructions:
1. Enter: 'create-portfolio a' to create an initial portfolio.
2. Enter: 'create-portfolio b' to create a second portfolio.
3. Enter 'select-stock MSFT' to query the Microsoft stock information.
4. Enter 'buy-stock 5' to buy 5 shares of the Microsoft stock.
    4a. Enter '2019', '9', '9' upon being prompted for a date.
    4b. Enter 'a' upon being prompted for a portfolio, to store the stock in portfolio a.
5. Enter 'buy-stock 10 b' to buy 10 shares of the Microsoft stock.
    5a. Enter '2020', '9', '10' upon being prompted for a date.
    5b. Enter 'b' upon being prompted for a portfolio, to store the stock in portfolio b.
6. Enter 'menu' to return to initial menu.
7. Enter 'select-stock GOOG' to query the Google stock information.
8. Enter 'buy-stock 10' to buy 10 shares of the Google stock.
    8a. Enter '2017', '9', '2' upon being prompted for a date.
    8b. Enter 'a' upon being prompted for a portfolio, to store the stock in portfolio a.
9. Enter 'buy-stock 2' to buy 2 shares of the Google stock.
    9a. Enter '2016', '8', '2' upon being prompted for a date.
    9b. Enter 'b' upon being prompted for a portfolio, to store the stock in portfolio b.
10. Enter 'select-stock NVDA' to query the Invidia stock information.
11. Enter 'buy-stock 1 a' to buy 1 shares of the Invidia stock.
    11a. Enter '2022', '8', '4' upon being prompted for a date.
    11b. Enter 'a' upon being prompted for a portfolio, to store the stock in portfolio a.
12. Enter 'check-portfolio a' to check the value of portfolio a.
    12a. Enter '2019', '9', '9' when prompted to get the value on September 9, 2019.
13. Enter 'check-portfolio b' to check the value of portfolio b on September 9, 2019.
    13a. Enter '2019', '9', '9' when prompted to get the value on September 9, 2019.

This process should permit a user to create two portfolios, a and b, and add 5 shares of MSFT,
10 shares of GOOG, and 1 shares of NDVA to portfolio a, and 10 shares of MSFT and 2 shares of GOOG
to portfolio b.

This program currently supports the following stocks:
    - MSFT (Microsoft) through dates (2000-04-25 - 2024-06-03)
    - GOOG (Google) through dates (2014-04-15 - 2024-06-03)
    - NVDA (Nvidia) through dates (2000-02-18 - 2024-06-03)
    - AAPL (Apple) through dates (2000-05-19 - 2024-06-03)
    - TSLA (Tesla) through dates (2011-01-11 - 2024-06-03)
    - NOTE: more stocks are permitted to be queried, and have varying dates of initial data)
