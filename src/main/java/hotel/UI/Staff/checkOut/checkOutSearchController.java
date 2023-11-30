package hotel.UI.Staff.checkOut;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotel.DbLoader;
import hotel.GuestRegistration;
import hotel.WelcomePage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class checkOutSearchController {


    private static String checkOutSearchName;

    @FXML
    private TextField searchField;


    @FXML
    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/staffpage");
    }
    @FXML
    private void processExitError(ActionEvent event) throws IOException {
        WelcomePage.setRoot("Staff/checkOut/checkOutSearch");
    }

    @FXML
    public void processSearch() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {

                compareToDb(connection);
        } 
        catch (SQLException e) {
                e.printStackTrace();

            }
    }

    public void compareToDb(Connection connection) throws SQLException, IOException{
        String query = "SELECT name FROM guest JOIN stay ON guest.guest_id = stay.guest_id WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = ?";
            PreparedStatement queryStatement = connection.prepareStatement(query);
            queryStatement.setString(1, searchField.getText());
            ResultSet result = queryStatement.executeQuery();
            if (result.next()) {
                checkOutSearchName = result.getString(1);
            }
            if (!searchField.getText().isEmpty() && searchField.getText().equals(checkOutSearchName)) {
                WelcomePage.setRoot("Staff/checkOut/checkOut");
                
            }
            else{
                WelcomePage.setRoot("Staff/checkOut/checkOutSearchError");
            }
    }
    public static String getCheckOutSearchName() {
        return checkOutSearchName;
    }
}
