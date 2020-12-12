import java.sql.*;
import java.util.ArrayList;
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
  public ObservableList<ProductionRecord> productionLog;

  // Product Line tab
  @FXML private TextField tfProductName;

  @FXML private TextField tfManufacturer;

  @FXML private ChoiceBox<ItemType> chbItem;

  @FXML private Button btnAddProduct;

  /**
   * Inserts added product into the PRODUCT database, and calls loadProductList().
   *
   * @param event Event
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
   * @param event: Event
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
    addToProductionDB(productionRun);

    loadProductionLog();
    showProduction();
  } // end btnRecordProduction()

  @FXML private ComboBox<String> cmbQuantity;

  @FXML private ListView<Product> lvChooseProduct;

  private ObservableList<Product> showProduction;

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
    loadProductionLog();

  } // end initialize()

  /** Sets items of the TableView to the ObservableList. */
  public void setupProductLineTable() {
    productLine = FXCollections.observableArrayList();

    tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
    tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
    tcManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

    tvProducts.setItems(productLine);
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
      if (resultSet.getString("type").equalsIgnoreCase("AU")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.AUDIO));

        lvChooseProduct.setItems(productLine);
      } else if (resultSet.getString("type").equalsIgnoreCase("VI")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.VISUAL));

        lvChooseProduct.setItems(productLine);
      } else if (resultSet.getString("type").equalsIgnoreCase("AM")) {
        productLine.add(
            new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("manufacturer"),
                ItemType.AUDIOMOBILE));

        lvChooseProduct.setItems(productLine);
      } else if (resultSet.getString("type").equalsIgnoreCase("VM")) {
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
        "INSERT INTO ProductionRecord (PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)" + "VALUES (?,?,?)";
    PreparedStatement ps = connection.prepareStatement(sql);
    Timestamp timestamp;
    System.out.println(productionRun.size());
    for (ProductionRecord pr : productionRun) {
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

    String query = "SELECT * FROM ProductionRecord";
    statement = connection.createStatement();
    resultSet = statement.executeQuery(query);

    // populate the productionLog ArrayList
    while (resultSet.next()) {
      productionLog.add(
          new ProductionRecord(
              resultSet.getInt("PRODUCTION_NUM"),
              resultSet.getInt("PRODUCT_ID"),
              resultSet.getString("SERIAL_NUM"),
              resultSet.getDate("DATE_PRODUCED")));
    } // end while loop

    showProduction();
  } // end loadProductionLog()

  /**
   * Populates the production log TextArea with ProductionRecord objects
   *
   * @throws SQLException Exception
   * @author Matthew Popescu
   */
  public void showProduction() throws SQLException {

    taProductionLog.setText(String.valueOf(productionLog));
  } // end showProduction()

  /**
   * Connects to the database
   *
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
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();

    }
  } // end connectToDB()

} // end Controller
