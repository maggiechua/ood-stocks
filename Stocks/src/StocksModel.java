public interface StocksModel {

  public StocksModelImpl stockSelect(StocksModel stock);

  public Integer gainLoss(String date1, String date2);

  public Integer movingAvg(Integer numOfDays, String date);

  public String crossovers(Integer numOfDays, String date1, String date2);

  public StocksModelImpl createPortfolio(String name);

  public StocksModelImpl buy(StocksModel stock, Integer shares, String portfolioName);

  public StocksModelImpl sell(StocksModel stock, Integer shares, String portfolioName);
}
