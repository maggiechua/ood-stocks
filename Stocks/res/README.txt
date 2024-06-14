Stocks Program (Part 1)

by: Aarushi Shankar and Maggie Chua
***********************************

This program allows a user to input a stock and check the following information about the stock:
    - the gain-loss average of the entered stock based on a specific date range
    - the x-day moving-average of the stock based on a specific date and x-input
    - if a specific date is an x-day crossover for the given stock based on a specific date range
The program also allows the user to buy and sell stocks- storing bought stocks in portfolios the
user is permitted to create and name. The user may also check the following information about their
portfolios:
    - the composition of the portfolio on a specific date
    - the distribution of a portfolio on a specific date
    - the value of the portfolio on a specific date
    - a bar chart displaying the performance of a portfolio in a specific date range
The user is also permitted to re-balance their portfolio with weights of their own choosing.
Dates are inputted manually, and are asked for specifically by the program to be inputted after
selecting an action by inputting first the year when requested by the program, then the month, then
the day. Invalid inputs will push the program to ask for either the year, the month, or the day
again until a valid input is inputted.
The user is shown an initial menu listing the stock selection, stock selling, and portfolio-related
features, which can be reopened. Upon selecting a stock, the user is shown another menu for the
stock-specific features described above, including buying a stock. The user is able to re-open this
menu, as well as return to the previous menu.
The user is permitted to quit the program from either menu.

NOTE: This program employs stock data from API AlphaVantage. This program creates and saves csv and
xml files to your computer within the /res folder within /data and /portfolios.

Thank you for using this program.