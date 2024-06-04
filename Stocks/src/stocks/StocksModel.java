package stocks;

public interface StocksModel {

  public StocksModelImpl stockSelect(String stock);

  public Double gainLoss(String date1, String date2);

  public Double movingAvg(Integer numOfDays, String date);

  public String crossovers(Integer numOfDays, String date1, String date2);

  public StocksModelImpl createPortfolio(String name);

  public StocksModelImpl buy(Integer shares, String portfolioName);

  public StocksModelImpl sell(String stock, Integer shares, String portfolioName);

  public Double portfolioValue(String portfolioName, String date);
}
