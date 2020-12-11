import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Date;

public class ProductionRecord {
  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  // ArrayList<ProductionRecord> list = new ArrayList<ProductionRecord>();

  /**
   * Called when the user records production from the UI
   *
   * @param productID: int
   * @author Matthew Popescu
   */
  public ProductionRecord(int productID) {
    this.productID = productID;
    serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * Creates serial number for produce.
   *
   * @param newProduct: Product
   * @param itemCount: int
   * @author Matthew Popescu
   */
  public ProductionRecord(Product newProduct, int itemCount) {
    newProduct = newProduct;
    itemCount = itemCount;
    this.dateProduced = new Date();
    String padded = String.format("%05d", itemCount);
    serialNumber =
        newProduct.getManufacturer().substring(0, 3)
            + newProduct.getType().toString().substring(0, 2)
            + padded;

    this.dateProduced = new Date();
  } // end overloaded constructor

  /**
   * Used when creating ProductionRecord objects from the database.
   *
   * @param productionNumber: int
   * @param productID: int
   * @param serialNumber: String
   * @param dateProduced: Date
   * @author Matthew Popescu
   */
  public ProductionRecord(
      int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  } // end constructor

  public int getProductionNum() {
    return productionNumber;
  }

  public void setProductionNum(int newProductionNumber) {
    this.productionNumber = newProductionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int newProductID) {
    this.productID = newProductID;
  }

  public String getSerialNum() {
    return serialNumber;
  }

  public void setSerialNum(String newSerialNumber) {
    this.serialNumber = newSerialNumber;
  }

  public Date getProdDate() {
    return dateProduced;
  }

  public void setProdDate(Date newDateProduced) {
    this.dateProduced = newDateProduced;
  }

  @Override
  public String toString() {
    return "Prod. Num: "
        + productionNumber
        + " "
        + "Product ID: "
        + productID
        + " "
        + "Serial Num: "
        + serialNumber
        + " "
        + "Date: "
        + dateProduced;
  } // end toString()
} // end ProductionRecord
