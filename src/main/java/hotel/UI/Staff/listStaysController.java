package hotel.UI.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class listStaysController implements Initializable{

   @FXML
    private TableView<Map<String, Object>> staysTableView;

    @FXML
    private TableColumn<Map<String, Object>, Integer> stayIdColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> guestIdColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> guestNameColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> roomNumberColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> checkinDateColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> checkoutDateColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> invoiceIdColumn;

    @FXML
    public void initialize() {
        // Initialize columns
        stayIdColumn.setCellValueFactory(cellData -> cellData.getValue().get("stay_id").toString(), Integer::parseInt);
        guestIdColumn.setCellValueFactory(cellData -> cellData.getValue().get("guest_id").toString(), Integer::parseInt);
        guestNameColumn.setCellValueFactory(cellData -> (String) cellData.getValue().get("guest_name"));
        roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().get("room_number").toString(), Integer::parseInt);
        checkinDateColumn.setCellValueFactory(cellData -> (String) cellData.getValue().get("checkin_date"));
        checkoutDateColumn.setCellValueFactory(cellData -> (String) cellData.getValue().get("checkout_date"));
        invoiceIdColumn.setCellValueFactory(cellData -> cellData.getValue().get("invoice_id").toString(), Integer::parseInt);

        // Fetch stays from the database and set them in the table
        staysTableView.setItems(getStaysFromDatabase());
    }

    private ObservableList<Map<String, Object>> getStaysFromDatabase() {
        ObservableList<Map<String, Object>> stays = FXCollections.observableArrayList();

        // Connect to the database and execute the query
        try (Connection connection = DriverManager.getConnection("jdbc:your-database-url", "username", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("your-SQL-query")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> stay = new HashMap<>();
                stay.put("stay_id", resultSet.getInt("stay_id"));
                stay.put("guest_id", resultSet.getInt("guest_id"));
                stay.put("guest_name", resultSet.getString("guest_name"));
                stay.put("room_number", resultSet.getInt("room_number"));
                stay.put("checkin_date", resultSet.getString("checkin_date"));
                stay.put("checkout_date", resultSet.getString("checkout_date"));
                stay.put("invoice_id", resultSet.getInt("invoice_id"));

                stays.add(stay);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stays;
    }
    public void processExitStaff() throws IOException {
        WelcomePage.setRoot("Staff/GuestManagement/guestmanagement");
    }
    
}
