import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("Tidy Snaps");
//        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
//        Scene scene = new Scene(root,
//                visualBounds.getWidth(), visualBounds.getHeight());
//        primaryStage.setScene(scene);
        primaryStage.setScene(new Scene(root, 1280, 800));

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
