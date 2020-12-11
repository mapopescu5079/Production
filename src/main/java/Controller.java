import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.FileInputStream;
import java.sql.*;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
import java.util.*;
import java.util.Date;

public class Controller {

  Connection connection = null;
  Statement statement = null;
  ResultSet resultSet = null;

  String productName;
  String item; // getValue() ?????
  String productManufacturer;

  int productionNum;
  int productID;
  char serialNum;
  Date dateProduced;

  public ObservableList<Product> productLine;
  //    public ArrayList<ProductionRecord> productionRun;
  //    public ArrayList<ProductionRecord> productionRunRecord;
  public ArrayList<ProductionRecord> productionRecordsTest = new ArrayList<>();

  // Product Line tab
  @FXML private TextField tfProductName;

  @FXML private TextField tfManufacturer;

  @FXML private ChoiceBox<String> chbItem;

  @FXML private Button btnAddProduct;

  /**
   * Inserts added product into the PRODUCT database, and calls loadProductList().
   *
   * @param event
   * @throws SQLException
   */
  @FXML
  void btnAddProduct(ActionEvent event) throws SQLException {

    productName = tfProductName.getText();
    productManufacturer = tfManufacturer.getText();
    item = chbItem.getValue();

    connectToDB();
    //        resultSet = statement.executeQuery(query);

    // insert added product into database

    // STEP 3: Execute a query
    final String sql = "INSERT INTO Product (type, manufacturer, name)" + "VALUES (?, ?, ?)";

    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setString(1, item);
    ps.setString(2, productManufacturer);
    ps.setString(3, productName);
    ps.executeUpdate();
    System.out.println("insert successful");

    //        String showSql = "SELECT * FROM Product";

    //        resultSet = statement.executeQuery(showSql);
    //        while (resultSet.next()) {
    //            tvProducts.setCellFactory(resultSet.getObject(2));
    //        }

    while (resultSet.next()) {
      switch (item) {
        case "Audio":
          productLine.add(new AudioPlayer(productName, productManufacturer));
          break;
        case "Visual":
          productLine.add(new MoviePlayer(productName, productManufacturer));
          break;
          //                case "AudioMobile":
          //                    productLine.add(new AudioPlayer(productName, productManufacturer));
          //                    break;
          //                case "VisualMobile":
          //                    productLine.add(new MoviePlayer(productName, productManufacturer));
          //                    break;
      } // end switch statement
      lvChooseProduct.setItems(productLine);
    } // end while{} statement

    // call loadProducts
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

  @FXML
  public void btnRecordProduction(ActionEvent event) throws SQLException {
    // create sql date object for insertion into DB
    Calendar calendar = Calendar.getInstance();
    // Timestamp for ProductionRecord object
    //        java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
    java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

    ;

    // get product from product line listview
    Product beingRecorded = new Product();
    beingRecorded = lvChooseProduct.getSelectionModel().getSelectedItem();
    //   and quantity from comboBox
    int quantity = Integer.parseInt(cmbQuantity.getValue());

    // create arrayList of ProductionRecord objects
    ArrayList<ProductionRecord> productionRun = new ArrayList<>();
    ProductionRecord newProduct = new ProductionRecord(productID);
    ItemType type = beingRecorded.getType();
    String manufacturer = beingRecorded.getManufacturer();
    Date date = new Date();
    Timestamp timestamp = new Timestamp(date.getTime());
    //        java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
    //        String name = beingRecorded.getName();
    //        int productID = b.getProductID();
    //        String serialNum = newProduct.getSerialNum();
    productionRun.add(newProduct);

    // send the productionRun to addToProductionDB method.  (Tip: use TimeStamp object for date!!!)
    //        addToProductionDB(productionRun);
    //        addToProductionDB(productionRunRecords);

    loadProductionLog();
    showProduction();
  } // end btnRecordProduction()

  @FXML private ComboBox<String> cmbQuantity;

  @FXML private ListView<Product> lvChooseProduct;

  private ObservableList<Product> showProduction;
  //  lvChooseProduct;

  // Production Log tab
  @FXML private TextArea taProductionLog;

  public void initialize() throws SQLException {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();

    chbItem.getItems().add("Audio");
    chbItem.getItems().add("Visual");
    chbItem.getItems().add("AudioMobile");
    chbItem.getItems().add("VisualMobile");

    connectToDB();

    setupProductLineTable();
    // associate the ObservableList with the Product Line ListView
    tvProducts.setItems(productLine);
    loadProductList();
    // loadProductionLog();

  } // end initialize()

  /** Sets items of the TableView to the ObservableList */
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
   * ProductionRecord database table
   *
   * @param productionRun
   * @throws SQLException
   */
  public void addToProductionDB(ArrayList<ProductionRecord> productionRun) throws SQLException {
    // STEP 3: Execute a query
    connectToDB();

    String sql =
        "INSERT INTO ProductionRecord (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
            + "VALUES (?,?,?,?)";

    PreparedStatement ps = connection.prepareStatement(sql);
    for (int i = 0; i < productionRun.size(); i++) {
      ps.setInt(1, Integer.parseInt(productionRun.get(i).toString()));
      ps.setInt(2, Integer.parseInt(productionRun.get(productID).toString()));
      ps.setString(3, productionRun.get(serialNum).toString());
      ps.setDate(4, null);
      statement.executeUpdate(sql);
    }

    //        for (int i = 0; i < productionRun.size(); i++){
    //            productionRun.add(lvChooseProduct.getSelectionModel().getSelectedItem());
    ////            productionRunRecords.add(cmbQuantity.getSelectionModel().getSelectedItem());
    //        }
    //        statement = connection.createStatement();
    //        resultSet = statement.executeQuery(query);
    //
    //        while (resultSet.next()) {
    //            production
    //        } // end while
  } // end addToProductionDB()

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
