// GuestRegistrationController.java
package hotel.UI.Staff.GuestManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hotel.DbLoader;
import hotel.GuestRegistration;
import hotel.WelcomePage;

public class GuestRegistrationController {

    @FXML
    private TextField guestNameIn;
    
    @FXML
    private TextField guestPhoneNumberIn;

    @FXML
    private TextField guestEmailIn;

    @FXML
    private TextField guestAddressIn;

    @FXML
    private DatePicker dateOfBirthIn;

    @FXML
    private DatePicker checkInField;

    @FXML
    private DatePicker checkOutField;

    @FXML
    private ComboBox<String> roomType;

    private int roomTypeVal;

    @FXML
    private Text requiredText;

    //@FXML
    //private DatePicker DateOfBirthIn;


    @FXML
    private void handleSelection() {
        String selectedOption = roomType.getSelectionModel().getSelectedItem();

        if (selectedOption != null) {
                //roomTypeVal = "";//Integer.parseInt(selectedText);
                //This is a really bad way to do this, but I had to get it working. 
                // If I had more time I'd definately have this query the room types to contruct the dropdown

                switch (selectedOption) {
                    case "One King Bed":
                        roomTypeVal = 1;
                        break;
                    case "Two Queen Beds":
                        roomTypeVal = 2;
                        break;
                    case "One King Bed with Sofabed":
                        roomTypeVal = 3;
                        break;
                    case "Junior Suite With One King Bed":
                        roomTypeVal = 4;
                        break;
                    default:
                        break;
                }
            }
        }

    @FXML
    private void processEnter(ActionEvent event) {
        if(guestNameIn.getText().isEmpty() || guestPhoneNumberIn.getText().isEmpty() || guestEmailIn.getText().isEmpty() || guestAddressIn.getText().isEmpty() || dateOfBirthIn.getValue() == null ||
        checkInField.getValue() == null || checkOutField.getValue() == null || roomType.getValue() == null){
            requiredText.setText("Please fill in all fields");
        }
        else{
            try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
                System.out.println(guestNameIn.getText());
                System.out.println(guestPhoneNumberIn.getText());
                System.out.println(guestEmailIn.getText());
                System.out.println(guestAddressIn.getText());
                System.out.println(dateOfBirthIn.getValue());
                System.out.println(checkInField.getValue());
                System.out.println(checkOutField.getValue());
                System.out.println(roomTypeVal);
                GuestRegistration guest = new GuestRegistration(guestNameIn, guestPhoneNumberIn, guestEmailIn, guestAddressIn, dateOfBirthIn, checkInField, checkOutField, roomTypeVal);
                guest.registerGuest(connection);
                requiredText.setText("Guest Registered");
                
            } 
            catch (SQLException e) {
                e.printStackTrace();
                
                // Handle the exception appropriately
            }

        }
    }

    @FXML
    private void processClear(ActionEvent event) {
        guestNameIn.clear();
        guestPhoneNumberIn.clear();
        guestEmailIn.clear();
        guestAddressIn.clear();
        dateOfBirthIn.setValue(null);
        checkInField.setValue(null);
        checkOutField.setValue(null);
        roomType.setValue(null);
        // Clear other fields as needed
    }

    @FXML
    private void processExitStaff(ActionEvent event) throws IOException {
        WelcomePage.setRoot("Staff/GuestManagement/guestmanagement");
    }
}
