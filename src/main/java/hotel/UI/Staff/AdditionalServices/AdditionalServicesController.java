package hotel.UI.Staff.AdditionalServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hotel.WelcomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class AdditionalServicesController implements Initializable{

    @FXML
    private VBox pane;

    @FXML
    private Text additionalServicesInfoText;

     public void initialize(URL location, ResourceBundle resources) {
        setServicesText();
    }

    public void setServicesText(){
        additionalServicesInfoText.setText("Guest " + AdditionalServicesSearchController.getServicesName() + " found!");
    }

    public void processGym(ActionEvent event) throws IOException {
        WelcomePage.setRoot("Staff/AdditionalServices/additionalServicesOptions/gymConfirmation");
    }

    public void processPool(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/AdditionalServices/additionalServicesOptions/poolConfirmation");
    }

    public void processBar(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/AdditionalServices/additionalServicesOptions/barConfirmation");
    }

    public void processBuffet(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/AdditionalServices/additionalServicesOptions/buffetConfirmation");
    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/AdditionalServices/additionalServicesSearch");
    }
}
