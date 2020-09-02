import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();


        TabPane tabPane = new TabPane();

        Tab productLine = new Tab("Product Line");
        Tab produce = new Tab("Produce");
        Tab productionLog = new Tab("Production Log");

        tabPane.getTabs().add(productLine);
        tabPane.getTabs().add(produce);
        tabPane.getTabs().add(productionLog);

    //    VBox vBox = new VBox(tabPane);
    //    Scene scene = new Scene(vBox);

    }

}
