package hotel.UI.Staff;

import java.io.IOException;
import java.sql.SQLException;

import hotel.WelcomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class staffPageController {
    @FXML
    private VBox pane;

    public void processGuestManagement(ActionEvent event) throws IOException {
        WelcomePage.setRoot("Staff/GuestManagement/guestmanagement");
    }

    public void processModifyStay(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/ModifyStay/modifyStaySearch");
    }

    public void processExtras(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/AdditionalServices/additionalServicesSearch");
        
    }

    public void processCheckOut(ActionEvent event) throws IOException {
       
         WelcomePage.setRoot("Staff/CheckOut/checkOutSearch");
    }

    public void processListRooms(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/ListRooms");
    }

    public void processListStays(ActionEvent event) throws IOException {
       WelcomePage.setRoot("Staff/ListStays");
    }

    public void processExit() {
        System.exit(0);
    }
}
