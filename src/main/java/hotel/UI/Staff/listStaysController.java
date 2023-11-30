package hotel.UI.Staff;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import hotel.DbLoader;
import hotel.WelcomePage;


public class listStaysController implements Initializable{

   @FXML
    private TableView stayTableView;

    @FXML
    private TableColumn<Stay, Integer> stayIdColumn;

    @FXML
    private TableColumn<Stay, Double> invoiceTotalColumn;

    @FXML
    private TableColumn<Stay, Boolean> cancelledFlagColumn;

    @FXML
    private TableColumn<Stay, String> guestNameColumn;

    @FXML
    private TableColumn<Stay, LocalDate> checkinDateColumn;

    @FXML
    private TableColumn<Stay, LocalDate> checkoutDateColumn;

    @FXML
    private TableColumn<Stay, Integer> roomNumberColumn;

    private ObservableList<Stay> staysData = FXCollections.observableArrayList();
    // Other necessary methods and initialization
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            processList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void processList() throws SQLException {
         try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
            String query = "SELECT stay.stay_id, stay.guest_id, stay.room_number, stay.checkin_date, stay.checkout_date, stay.cancelled_flag, invoice.invoice_total, guest.name " +
                            "FROM stay " +
                            "INNER JOIN guest ON stay.guest_id = guest.guest_id " +
                            "INNER JOIN invoice ON stay.invoice_id = invoice.invoice_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Collect data from ResultSet and populate the list of Stay objects
                while (resultSet.next()) {
                    Stay stay = new Stay(
                            resultSet.getInt("stay_id"),
                            resultSet.getInt("guest_id"),
                            resultSet.getInt("room_number"),
                            (resultSet.getDate("checkin_date")).toLocalDate(),
                            (resultSet.getDate("checkout_date")).toLocalDate(),
                            resultSet.getString("name"),
                            resultSet.getBoolean("cancelled_flag"),
                            resultSet.getDouble("invoice_total")

                    );

                    staysData.add(stay);

                    
                }

                // Bind data to TableView
                stayIdColumn.setCellValueFactory(cellData -> cellData.getValue().stayIdProperty().asObject());
                guestNameColumn.setCellValueFactory(cellData -> cellData.getValue().guestNameProperty());
                checkinDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckinDate()));
                checkoutDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCheckoutDate()));
                cancelledFlagColumn.setCellValueFactory(cellData -> cellData.getValue().cancelledFlagProperty().asObject());
                invoiceTotalColumn.setCellValueFactory(cellData -> cellData.getValue().invoiceTotalProperty().asObject());
                roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());

                stayTableView.getItems().addAll(staysData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, for example, show an error message to the user
        }
    }
        
        
    public void processExitStaff() throws IOException {
        WelcomePage.setRoot("Staff/staffpage");
    }
    
}
