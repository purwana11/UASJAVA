package uasjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UASJAVA extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Cafe shop management System");
        stage.setMinHeight(400);
        stage.setMinWidth(600);   
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    } 
}
