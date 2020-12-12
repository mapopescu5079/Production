import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setTitle("Production Line");
    primaryStage.setScene(scene);
    scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
    primaryStage.show();
  }

  public static void main(String[] args) {
    // Application.launch();
    launch(args);
  }
} // end Main
