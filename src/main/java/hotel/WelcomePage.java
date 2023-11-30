// WelcomePage.java
package hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePage extends Application {

    private static Scene scene;
    public static final DbLoader dbLoader = DbLoader.loadFromCredentialsFile(WelcomePage.class.getResource("credentials.txt").getPath());

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            scene = new Scene(loadFXML("Staff/staffpage"), 640, 480);
            primaryStage.setTitle("Hotel Management System");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            WelcomePage.setRoot("Error");
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePage.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
