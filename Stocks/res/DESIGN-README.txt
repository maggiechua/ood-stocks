PROGRAM DESIGN:

We chose to change methods for buy and sell to include date entries, as opposed to creating new
methods as we found our design ensures that changing the parameters would be relatively easy for us.
We added new classes and methods for our added features. Additional specific changes are written in
for each change from the last assignment within the files. We hope our notes will suffice.

Our design is based off of the MVC format- where we attempted to separate MVC as much as possible.
All stock data access and methods are stored in the Model section (including the StocksModel
interface, the StocksModelImpl class, and alphaVantageDemo), all text output related methods are
stored in the View section (including the StocksView interface, and the StocksViewImpl class), and
the executing method of the program which interacts with user inputs is stores in the Controller
section (including the StocksController interface, and the StocksControllerImpl class).

StocksController only contains the execute method. StocksControllerImpl implements this method, as
well as other helper methods for better separation and readibility. This class only interacts
directly with the StocksModel interface and the StocksView interface, pulling from methods stored
there. The model and view of our program do not directly interact in any way, only through the
controller class calling methods from each interface.

All stock related data and methods are stored solely in the model section, as stated prior, to
allow us to enhance and fix any stock access features without interrupting processes in the view
and controller. We made a concerted effort to isolate all outputted text into view methods, to
permit us to make changes to outputted text in an organized, isolated fashion. Our controller class,
StocksControllerImpl pulls solely from the view and model interfaces, rather than classes, to allow
us to add any future classes with more ease through which we can expand our program's features.

Our Model uses AlphaVantage API as it was a resource provided to us to use as stock data, creates
csv files to save stock data from the API, and creates xml files to save portfolio data.
Our StocksViewImpl uses an Appendable to store text to be outputted for readability.
Our StocksControllerImpl uses a Scanner to read user inputs as it was a resource we were informed
of by our instructors and works to fulfill our purposes.

StockProgram connects to our three implementations (StocksModelImpl, StocksViewImpl, and
StocksControllerImpl) and runs the program.

Our design is described in greater depth below:

Model: StocksModel, Portfolio
    - [holds the data and methods of the program]
    - Includes:
        - StocksModelImpl
            Methods (pulled from Interface):
            - getStockInfo (accesses API data)
            - stockSelect (takes in user stock input and saves it to the StocksModel)
            - gainLoss (method to calculate gains and losses over a specific date range)
            - movingAvg (method to calculate x-day moving average)
            - crossovers (method to check if date is an x-day crossover)
            - createPortfolio (creates a new portfolio with given name)
            - buy (adds a stock to the portfolio)
            - sell (removes a stock from the portfolio)
            - portfolioValue (caculates the value of a portfolio on a given date)
            Methods (not in interface):
            - getAPIKey (stores the API key used to access the API)
            getStockInfo (pulls stock information from data, or calls createStockCSVFile)
        - FileCreator
            Methods (not pulled from Interface):
            - createStockCSVFile (creates a CSV file in data to store stock data)
        - FileParser
        - PortfolioImpl
        - Transaction
        - CompareTransaction
        - CompareDate
        - ReadOnlyModel
View: StocksView
    - [holds the methods to visualize outputs for user]
    - Includes:
        - StocksViewImpl
            Methods:
            - welcomeMessage (adds text for the welcome message to the appendable)
            - typeInstruct (adds message of Type Instruction: for user to type into)
            - undefined (adds invalid input message)
            - farewellMessage (adds the program end message)
            - printMenu (adds the inital menu text)
            - printStockMenu (adds the stock-data based menu text)
            - returnResult (adds the input to be outputted)
            - portfolioException (adds text to inform the user a portfolio error is present)
            - formattedReturn (adds Double inputs as formatted strings to two decimals)
            - portfolioCreationMessage (adds text to indicate a new portfolio has been created)
            - buySellMessage (adds text to indicate whether a stock has been bought or sold)
Controller: StocksController
    - [holds the method to execute the program, interacts with other two interfaces]
    - Includes:
        - StocksControllerImpl
            Methods:
            - execute (executes the program using user inputs and methods from StocksModel and
              StocksView)