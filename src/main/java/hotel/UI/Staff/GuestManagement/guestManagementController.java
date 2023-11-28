package hotel.UI.Staff.GuestManagement;

import java.io.IOException;
import java.sql.SQLException;

import hotel.WelcomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class guestManagementController {
    @FXML
    private VBox pane;

    public void processRegisterGuest(ActionEvent event) throws IOException {
        WelcomePage.setRoot("Staff/GuestManagement/GuestRegistration");
    }

    public void processListGuests(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/GuestManagement/listguests");
    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/staffpage");
    }
}
