import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Controller {

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnRecordProduction;

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    void display(ActionEvent event) {
        System.out.println(btnAddProduct);
    }

    @FXML
    void rpDisplay(ActionEvent event) {
        System.out.println(btnRecordProduction);
    }


}
