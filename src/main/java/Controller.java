import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.sql.*;

public class Controller {

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

    public void initialize(){ }

    public void connectToDb(){

        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/resDB";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to Database");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection Successful");

            //STEP 3: Execute a query
            System.out.println("inserting products into table");
            stmt = conn.createStatement();

            // String addProduct = btnAddProduct().getText();

            String insertSql = "INSERT INTO Product(type, manufacturer, name) "
            + "VALUES('AUDIO', 'APPLE', 'iPod')";

            stmt.executeUpdate(insertSql);

            insertSql = "INSERT INTO Product(type, manufacturer, name) "
                    + "VALUES('AUDIO', 'Samsung', 'mp3')";

            stmt.executeUpdate(insertSql);
            System.out.println("Products inserted into table");


         /*   String sql = "SELECT email, first_name, last_name "
                    + "FROM employees "
                    + "where employee_id = "+ empId;
*/
         /*   ResultSet rs = stmt.executeQuery(sql);
            // while (rs.next()) {

            rs.next();
            String empEmail = rs.getString(1);
            String empFirstName = rs.getString(2);
            String empLastName = rs.getString(3);
            //System.out.println(empFirstName+" " + empLastName + " " + empEmail + "@tiktok.com");

            lblEmpInfo.setText(empFirstName+" " + empLastName + " " + empEmail + "@tiktok.com");
*/
            // }

            // STEP 4: Clean-up environment
             stmt.close();
             conn.close();
        } catch (ClassNotFoundException e) {
            // Handle errors for JDBC
            e.printStackTrace();

        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to clase resources
            try {
                if (stmt!=null)
                    conn.close();
            } catch (SQLException e){
                // do nothing
            }
            try {
                if (conn!=null)
                    conn.close();
                } catch (SQLException e){
                e.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye");
    } // end connectToDb

} // end Controller

