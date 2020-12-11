import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

  Connection connection = null;
  Statement statement = null;
  ResultSet resultSet = null;

  public ObservableList<Product> productLine;

  // Product Line tab
  @FXML private TextField tfProductName;

  @FXML private TextField tfManufacturer;

  @FXML private ChoiceBox<ItemType> chbItem;

  @FXML private Button btnAddProduct;

  /**
   * Inserts added product into the PRODUCT database, and calls loadProductList().
   *
   * @param event ActionEvent
   * @throws SQLException: Exception
   * @author Matthew Popescu
   */
  @FXML
  void btnAddProduct(ActionEvent event) throws SQLException {

    String productName = tfProductName.getText();
    String productManufacturer = tfManufacturer.getText();
    ItemType item = chbItem.getSelectionModel().getSelectedItem();

    connectToDB();

    // insert added product into database
    final String sql = "INSERT INTO Product (type, manufacturer, name)" + "VALUES (?, ?, ?)";

    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setString(1, item.code);
    ps.setString(2, productManufacturer);
    ps.setString(3, productName);
    ps.executeUpdate();
    System.out.println("insert successful");

    loadProductList();
  } // end button

  @FXML private TableView<Product> tvProducts;

  @FXML private TableColumn<Product, Integer> tcID;

  @FXML private TableColumn<Product, ItemType> tcType;

  @FXML private TableColumn<Product, String> tcManufacturer;

  @FXML private TableColumn<Product, String> tcName;

  //    private ArrayList<Product> productionRun;
  //    private ArrayList<Product> productList;

  // Produce tab
  @FXML private Button btnRecordProduction;

  /**
   * Creates product objects from the UI and adds them to an ArrayList which is sent to
   * addToProduction(). Lastly calls loadProductionLog and showProduction.
   *
   * @param event
   * @throws SQLException: Exception
   * @author Matthew Popescu
   */
  @FXML
  public void btnRecordProduction(ActionEvent event) throws SQLException {


    // get product from product line listview
    Product produce = new Product();
    produce = lvChooseProduct.getSelectionModel().getSelectedItem();
    //   and quantity from comboBox
    int quantity = Integer.parseInt(cmbQuantity.getValue());

    // create arrayList of ProductionRecord objects
    ArrayList<ProductionRecord> productionRun = new ArrayList<>();
    ProductionRecord recordedProduce = new ProductionRecord(produce.getId());

    productionRun.add(recordedProduce);
    // send the productionRun to addToProductionDB method.  (Tip: use TimeStamp object for date!!!)
    addToProductionDB(productionRun);

//    loadProductionLog();
//    showProduction();
  } // end btnRecordProduction()

  @FXML private ComboBox<String> cmbQuantity;

  @FXML private ListView<Product> lvChooseProduct;

  private ObservableList<Product> showProduction;
  //  lvChooseProduct;

  // Production Log tab
  @FXML private TextArea taProductionLog;

  /**
   * Initializes program during start-up.
   *
   * @throws SQLException: Exception
   * @author Matthew Popescu
   */
  public void initialize() throws SQLException {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();

    chbItem.getItems().addAll(ItemType.values());

    connectToDB();

    setupProductLineTable();
    // associate the ObservableList with the Product Line ListView
    tvProducts.setItems(productLine);
    loadProductList();
    // loadProductionLog();

  } // end initialize()

  /** Sets items of the TableView to the ObservableList. */
  public void setupProductLineTable() {
    productLine = FXCollections.observableArrayList();

    tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
    tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
    tcManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

    tvProducts.setItems(productLine);
    //        lvChooseProduct.setItems(productLine);
  } // end setupProductLineTable()

  /**
   * Creates Product objects from the Product database table and adds them to the productLine
   * ObservableList (which automatically updates the Product Line ListView).
   *
   * @author Matthew Popescu
   */
  private void loadProductList() throws SQLException {
    connectToDB();

    lvChooseProduct.setItems(productLine);

    String query = "SELECT * FROM Product";
    statement = connection.createStatement();
    resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
      if (resultSet.getString("type").equalsIgnoreCase("AUDIO")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.AUDIO));

        lvChooseProduct.setItems(productLine);
      } else if (resultSet.getString("type").equalsIgnoreCase("VISUAL")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.VISUAL));

        lvChooseProduct.setItems(productLine);
      } else if (resultSet.getString("type").equalsIgnoreCase("AUDIOMOBILE")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.AUDIOMOBILE));

        lvChooseProduct.setItems(productLine);
      } else if (resultSet.getString("type").equalsIgnoreCase("VISUALMOBILE")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.VISUALMOBILE));

        lvChooseProduct.setItems(productLine);
      }
    } // end while
  } // end loadProductList();

  /**
   * Loop through the productionRun, inserting productionRecord object information into the
   * ProductionRecord database table.
   *
   * @param productionRun: ArrayList
   * @throws SQLException; Exception
   * @author Matthew Popescu
   */
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun) throws SQLException {
    // STEP 3: Execute a query
    connectToDB();
    String sql =
        "INSERT INTO ProductionRecord (PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
            + "VALUES (?,?,?)";
    PreparedStatement ps = connection.prepareStatement(sql);
    Timestamp timestamp;
    System.out.println(productionRun.size());
    for (ProductionRecord pr:productionRun) {
          timestamp = new Timestamp(pr.getProdDate().getTime());
          ps.setInt(1, pr.getProductID());
      System.out.println(pr.getProductID());
          ps.setString(2, pr.getSerialNum());
          ps.setTimestamp(3, timestamp);
          statement.executeUpdate(sql);
        }
        ps.close();
  } // end addToProductionDB()

  /**
   * Creates Production Record objects from the ProductionRecord database
   *
   * @throws SQLException: Exception
   * @author Matthew Popescu
   */
  public void loadProductionLog() throws SQLException {
    connectToDB();

    ArrayList<ProductionRecord> productionRecordArrayList = new ArrayList<>();
    // create ProductionRecord objects from the
    //        productionRun = ;
    //   records in the ProductionRecord database table
    String query = "SELECT * FROM ProductionRecord";
    statement = connection.createStatement();
    resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
      productionRecordArrayList.add(
          new ProductionRecord(
              resultSet.getInt("PRODUCTION_NUM"),
              resultSet.getInt("PRODUCT_ID"),
              resultSet.getString("SERIAL_NUM"),
              resultSet.getDate("DATE_PRODUCED")));
    } // end while loop

    // populate the productionLob ArrayList

    showProduction();
  } // end loadProductionLog()

  /**
   * Populates the production log TextArea with ProductionRecord objects
   *
   * @throws SQLException Exception
   * @author Matthew Popescu
   */
  public void showProduction() throws SQLException {
    connectToDB();
    // populate the TextArea on the Production Log tab with the information from the productionLog,
    //    replacing the productId with the product name, with one line for each product produced
    //        taProductionLog.setText();
    String productionLog;
    String query = "SELECT * FROM ProductionRecord";
    statement = connection.createStatement();
    resultSet = statement.executeQuery(query);

    //        while (resultSet.next()) {
    //            productionRecordsTest.get(
    //                            resultSet.getInt("PRODUCTION_NUM"),
    //                            resultSet.getInt("PRODUCT_ID"),
    //                            resultSet.getString("SERIAL_NUM"),
    //                            resultSet.getDate("DATE_PRODUCED"));
    //            taProductionLog.setText(String.valueOf(productionRecordsTest));
    //            taProductionLog.set;
    //        } // end while loop
    //        taProductionLog.setAccessibleText(lo);

  } // end showProduction()

  /**
   * Connects to the database
   * @author Matthew Popescu
   */
  public void connectToDB() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/resDB";

    //  Database credentials
    final String USERNAME = "";
    final String PASSWORD = "";

    try {
      Class.forName(JDBC_DRIVER);
      System.out.println("Connecting to Database");
      connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
      statement = connection.createStatement();
      System.out.println("Connection Successful");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  } // end connectToDB()

  //    public void connectToDbOld(){
  //        final String JDBC_DRIVER = "org.h2.Driver";
  //        final String DB_URL = "jdbc:h2:./res/resDB";
  //        System.out.println("connected to database");
  //
  //        //  Database credentials
  //        final String USER = "";
  //        final String PASS = "";
  //        Connection conn = null;
  //        Statement stmt = null;
  //
  //        String productName = tfProductName.getText();
  //        String productManufacturer = tfManufacturer.getText();
  //        String item = chbItem.getValue();
  //
  //        try {
  //
  //            // STEP 1: Register JDBC driver
  //            Class.forName(JDBC_DRIVER);
  //
  //            //STEP 2: Open a connection
  //            System.out.println("Connecting to Database");
  //            conn = DriverManager.getConnection(DB_URL, USER, PASS);
  //            System.out.println("Connection Successful");
  //
  //            //STEP 3: Execute a query
  //            final String sql = "INSERT INTO Product (type, manufacturer, name)"
  //                    + "VALUES (?, ?, ?)";
  //
  //            PreparedStatement ps = conn.prepareStatement(sql);
  //            ps.setString(1, item);
  //            ps.setString(2, productManufacturer);
  //            ps.setString(3, productName);
  //            ps.executeUpdate();
  //            System.out.println("insert successful");
  //
  //            stmt = conn.createStatement();
  //
  //            String showSql = "SELECT * FROM Product";
  //
  //            ResultSet rs = stmt.executeQuery(showSql);
  //            while (rs.next()) {
  //                System.out.println(rs.getString(2));
  ////                lvChooseProduct.setCellFactory(rs.getObject(2));
  //            }
  //
  //            // STEP 4: Clean-up environment
  //             stmt.close();
  //             conn.close();
  //        } catch (ClassNotFoundException e) {
  //            e.printStackTrace();
  //
  //        } catch (SQLException e) {
  //            e.printStackTrace();
  //        }
  //
  //        // insert PRODUCT table here!!!
  //        switch (chbItem.getValue()){
  //            case "Audio":
  //                productLine.add(new AudioPlayer(productName, productManufacturer));
  //                break;
  //            case "Visual":
  //                productLine.add(new MoviePlayer(productName, productManufacturer));
  //                break;
  //            case "AudioMobile":
  //                productLine.add(new AudioPlayer(productName, productManufacturer));
  //                break;
  //            case "VisualMobile":
  //                productLine.add(new MoviePlayer(productName, productManufacturer));
  //                break;
  //        } // end switch statement
  //        tvProducts.getItems().addAll(productLine);
  ////        lvChooseProduct.getItems().addAll(productLine);
  //
  //        System.out.println("done");
  //        System.out.println();
  //
  //    } // end connectToDb

} // end Controller
