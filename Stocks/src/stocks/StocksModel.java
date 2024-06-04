package stocks;

public interface StocksModel {

  public StocksModelImpl stockSelect(StocksModel stock);

  public Double gainLoss(Integer numOfDays, String date);

  public Double movingAvg(Integer numOfDays, String date);

  public String crossovers(Integer numOfDays, String date);

  public StocksModelImpl createPortfolio(String name);

  public StocksModelImpl buy(Integer shares, String portfolioName);

  public StocksModelImpl sell(StocksModel stock, Integer shares, String portfolioName);

  public Double portfolioValue(String portfolioName, String date);
}
