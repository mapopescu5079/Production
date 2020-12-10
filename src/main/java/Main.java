import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("The election was totally rigged");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Application.launch();
        launch(args);
    }


} // end Main
