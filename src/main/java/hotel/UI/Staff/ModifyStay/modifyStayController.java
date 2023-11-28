package hotel.UI.Staff.ModifyStay;
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


public class modifyStayController implements Initializable{

    @FXML
    private VBox pane;

    @FXML
    private Text modifyStayInfoText;

     public void initialize(URL location, ResourceBundle resources) {
        setInfoText();
    }

    public void setInfoText(){
        modifyStayInfoText.setText("Guest " + modifyStaySearchController.getModifySearchName() + " found!");
    }

    public void processCancelStay(ActionEvent event) throws IOException {
        WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/CancelStayConfirmation");
    }

    public void processChangeRoom(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/ChangeRoomConfirmation");
    }

    public void processExtendStay(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/ExtendStayConfirmation");
    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStaySearch");
    }
}
