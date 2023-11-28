// WelcomePageController.java
package hotel.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

import hotel.WelcomePage;

public class WelcomePageController {

    @FXML
    private VBox pane;
    
    @FXML
    private Text prompt;

    @FXML
    private Button guestButton;

    @FXML
    private Button staffButton;

    @FXML
    private void processGuestPress(ActionEvent event) {
        System.out.println("Guest button pressed");
        // Implement the logic for guest button press
        try {
            WelcomePage.setRoot("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void processStaffPress(ActionEvent event) {
        // Implement the logic for staff button press
        try {
            WelcomePage.setRoot("Staff/staffpage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
