SETUP Instructions:

1. Within the terminal, locate the location of the JAR file- within the provided res folder.
2. Ensure your data folder is accessible and is within the res folder.
3. Run the JAR program ($ java -jar Stocks.jar)

Additional Note: During the testing phase, we noticed that accessing the stock data according to the file path had discrepancies between Mac vs. Windows OS. Please doublecheck that the file path located in the StocksModelImpl.java file on Line 30 matches the following according to your device.
    - Mac: "/res/data/"
    - Windows: "/Stocks/res/data/"

Example run instructions:
1. Enter: 'create-portfolio a' to create an initial portfolio.
2. Enter: 'create-portfolio b' to create a second portfolio.
3. Enter 'select-stock MSFT' to query the Microsoft stock information.
4. Enter 'buy-stock 5 a' to buy 5 shares of the Microsoft stock, and store it in portfolio a.
5. Enter 'buy-stock 10 b' to buy 10 shares of the Microsoft stock, and store it in portfolio b.
6. Enter 'menu' to return to initial menu.
7. Enter 'select-stock GOOG' to query the Google stock information.
8. Enter 'buy-stock 10 a' to buy 5 shares of the Google stock, and store it in portfolio a.
9. Enter 'buy-stock 2 b' to buy 2 shares of the Google stock, and store it in portfolio b.
10. Enter 'select-stock NVDA' to query the Invidia stock information.
11. Enter 'buy-stock 1 a' to buy 1 shares of the Invidia stock, and store it in portfolio a.
12. Enter 'check-portfolio a 2019-09-09' to check the value of portfolio a on September 9, 2019.
12. Enter 'check-portfolio b 2019-09-09' to check the value of portfolio b on September 9, 2019.

This process should permit a user to create two portfolios, a and b, and add 5 shares of MSFT,
10 shares of GOOG, and 1 shares of NDVA to portfolio a, and 10 shares of MSFT and 2 shares of GOOG
to portfolio b.

This program currently supports the following stocks:
    - MSFT (Microsoft) through dates (2000-04-25 - 2024-06-03)
    - GOOG (Google) through dates (2014-04-15 - 2024-06-03)
    - NVDA (Nvidia) through dates (2000-02-18 - 2024-06-03)
