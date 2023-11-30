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


public class listRoomsController implements Initializable{

   @FXML
    private TableView roomsTableView;

    @FXML
    private TableColumn<Room, Boolean> roomFlagColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;

    private ObservableList<Room> roomsData = FXCollections.observableArrayList();
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
            String query = "SELECT room.room_number, room_type.room_type_name, room.room_status_flag " +
            "FROM room " +
            "JOIN room_type ON room.room_type = room_type.room_type";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Room room = new Room(
                            resultSet.getInt("room_number"),
                            resultSet.getString("room_type_name"),
                            resultSet.getBoolean("room_status_flag")

                    );

                    roomsData.add(room);

                    
                }

                // Bind data to TableView
                roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());
                roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeNameProperty());
                roomFlagColumn.setCellValueFactory(cellData -> cellData.getValue().roomFlagProperty().asObject());

                roomsTableView.getItems().addAll(roomsData);
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
