public interface Stock {

  public StockImpl stockSelect(Stock stock);

  public Double gainLoss(String date1, String date2);

  public Double movingAvg(Integer numOfDays, String date);

  public String crossovers(Integer numOfDays, String date1, String date2);

  public StockImpl createPortfolio(String name);

  public StockImpl buy(Stock stock, Integer shares, String portfolioName);

  public StockImpl sell(Stock stock, Integer shares, String portfolioName);

  public Double portfolioValue(String portfolioName, String date);
}
