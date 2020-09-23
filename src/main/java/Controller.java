import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class Controller {

    @FXML
    private TextField tfProductName;
    public TextField getTfProductName() {
        return tfProductName;
    }

    public void setTfProductName(TextField tfProductName) {
        this.tfProductName = tfProductName;
    }

    @FXML
    private TextField tfManufacturer;
    public TextField getTfManufacturer() {
        return tfManufacturer;
    }

    public void setTfManufacturer(TextField tfManufacturer) {
        this.tfManufacturer = tfManufacturer;
    }

    @FXML
    private TextField tfItem;
    public TextField getTfItem() {
        return tfItem;
    }

    public void setTfItem(TextField tfItem) {
        this.tfItem = tfItem;
    }

    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    void btnAddProduct() {
      //  connectToDb();
    }

    @FXML
    void showProduct(ActionEvent event) {
        connectToDb();
    }

    @FXML
    void btnRecordProduction(MouseEvent event) {
        System.out.println("Production Recorded.");
    }

    // void showDetails(ActionEvent event){connectToDb();}

    public void initialize(){
        for (int count = 1; count <= 10; count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
        cmbQuantity.setEditable(true);
        cmbQuantity.getSelectionModel().selectFirst();
    }



    public void connectToDb(){

        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/resDB";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        String make = "";
        String manufacturer = "";
        String name = "";

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter type: ");
        make = scanner.next();
        System.out.println("enter manufacturer: ");
        manufacturer=scanner.next();
        System.out.println("enter name: ");
        name = scanner.next();

        try {

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to Database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection Successful");

            //STEP 3: Execute a query
            System.out.println("attempting to insert");
            final String sql = "INSERT INTO Product (type, manufacturer, name)"
                    + "VALUES (?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, make);
            ps.setString(2, manufacturer);
            ps.setString(3, name);

            ps.executeUpdate();
            System.out.println("insert successful");

            stmt = conn.createStatement();

            String showSql = "SELECT * FROM Product";

            ResultSet rs = stmt.executeQuery(showSql);
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
            // STEP 4: Clean-up environment
             stmt.close();
             conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // insert PRODUCT table here!!!
        System.out.println("Products Successfully inserted into table");
    } // end connectToDb

} // end Controller

