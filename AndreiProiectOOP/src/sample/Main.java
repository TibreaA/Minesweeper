package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MINESWEEPER");
        primaryStage.setScene(new Scene(root, 310, 370));
        primaryStage.setMinWidth(310);
        primaryStage.setMinHeight(370);
        primaryStage.setMaxWidth(310);
        primaryStage.setMaxHeight(370);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
