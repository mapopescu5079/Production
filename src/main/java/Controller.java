import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class Controller {

    //Product Line tab
    @FXML
    private TextField tfProductName;

    @FXML
    private TextField tfManufacturer;

    @FXML
    private ChoiceBox<String> chbItem;

    @FXML
    private Button btnAddProduct;

    @FXML
    void btnAddProduct(ActionEvent event) {
        connectToDb();
    }

    //Produce tab
    @FXML
    private Button btnRecordProduction;

    @FXML
    void btnRecordProduction(ActionEvent event) { }

    @FXML
    private ComboBox<String> cmbQuantity;


    public void initialize(){
        for (int count = 1; count <= 10; count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
        cmbQuantity.setEditable(true);
        cmbQuantity.getSelectionModel().selectFirst();

        chbItem.getItems().add("ipod2");
        chbItem.getItems().add("iwatch");
        chbItem.getItems().add("irobot");
    }


    public void connectToDb(){

        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/resDB";
        System.out.println("connected to database");


        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        String productName = tfProductName.getText();
        String manufacturer = tfManufacturer.getText();
        String item = chbItem.getValue();

        System.out.println(productName + " " + manufacturer + " " + item);


        try {

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to Database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection Successful");

            //STEP 3: Execute a query

            final String sql = "INSERT INTO Product (type, manufacturer, name)"
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, productName);
            ps.setString(2, manufacturer);
            ps.setString(3, item);
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
        System.out.println("done");

    } // end connectToDb
} // end Controller

