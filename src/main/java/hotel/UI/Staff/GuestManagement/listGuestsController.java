package hotel.UI.Staff.GuestManagement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import hotel.DbLoader;
import hotel.GuestRegistration;
import hotel.WelcomePage;

public class listGuestsController implements Initializable {

    @FXML
    private TableView<GuestRegistration> tableView;

    @FXML
    private TableColumn<GuestRegistration, Integer> guestIdColumn;

    @FXML
    private TableColumn<GuestRegistration, String> nameColumn;

    @FXML
    private TableColumn<GuestRegistration, String> phoneColumn;

    @FXML
    private TableColumn<GuestRegistration, String> emailColumn;

    @FXML
    private TableColumn<GuestRegistration, String> addressColumn;

    @FXML
    private TableColumn<GuestRegistration, String> DOBColumn;

    @FXML
    private Button exitButton;

    // Add other necessary fields

    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
        System.out.println("View is now loaded!");
        try {
            processGuestList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void processGuestList() throws SQLException {
        guestIdColumn.setCellValueFactory(cellData -> cellData.getValue().guestIDProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().guestEmailProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().guestAddressProperty());
        DOBColumn.setCellValueFactory(cellData -> cellData.getValue().guestDOBProperty());
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword());){
            List<GuestRegistration> guests = GuestRegistration.convertToGuestList(connection);
            tableView.getItems().addAll(guests);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processExitStaff() throws IOException {
        WelcomePage.setRoot("Staff/GuestManagement/guestmanagement");
    }
}