package hotel.UI.Staff.checkOut;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hotel.DbLoader;
import hotel.WelcomePage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class checkOutController implements Initializable{

    @FXML
    private Text checkOutText;

    @FXML
    private Button checkOutButton;

    @FXML
    private Button cancelButton;

    private double totalCost;

    private int numNights;

    private String name = checkOutSearchController.getCheckOutSearchName();

    public void processCheckout() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
                processCheckOutDB(connection);
        } 
        catch (SQLException e) {
                e.printStackTrace();

            }
    }


    public void processCheckOutDB(Connection connection) throws IOException, SQLException {

        //Free Room
        String oldRoomUpdate = "UPDATE room\r\n" + //
                "SET room_status_flag = 0\r\n" + //
                "WHERE room_number IN (SELECT stay.room_number FROM stay JOIN guest ON stay.guest_id = guest.guest_id WHERE guest.name = ?);";
        PreparedStatement updateOldRoom = connection.prepareStatement(oldRoomUpdate);
        updateOldRoom.setString(1, name);
        updateOldRoom.execute();
        

        //Calculate Cost
        String sqlQuery = "SELECT guest.name AS guest_name, stay.room_number, " +
                    "room_type.room_type_name, DATEDIFF(day, stay.checkin_date, ISNULL(stay.checkout_date, GETDATE())) AS num_nights, " +
                    "room_type.suggested_rate * DATEDIFF(day, stay.checkin_date, ISNULL(stay.checkout_date, GETDATE())) AS total_cost " +
                    "FROM stay " +
                    "JOIN guest ON stay.guest_id = guest.guest_id " +
                    "JOIN room ON stay.room_number = room.room_number " +
                    "JOIN room_type ON room.room_type = room_type.room_type " +
                    "WHERE guest.name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, name);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        numNights = resultSet.getInt("num_nights");
                        totalCost = resultSet.getDouble("total_cost");
                        System.out.println("Number of Nights: " + numNights);
                        System.out.println("Total Cost: " + totalCost);
                    }
                }
            }
            catch (SQLException e) {
            e.printStackTrace();
            }

            updateInvoice(connection);
         checkOutText.setText("Guest " + name + " Checked Out! Total Cost: " + totalCost + "$. Number of Nights: " + numNights);
         checkOutButton.setVisible(false);
         cancelButton.setText("Exit");
        }

        public void updateInvoice(Connection connection) throws SQLException{
            String updateInvoice = "UPDATE invoice\r\n" + //
                "SET invoice_total = invoice_total + ?\r\n" + //
                "WHERE invoice_id IN (SELECT stay.invoice_id FROM stay JOIN guest ON stay.guest_id = guest.guest_id WHERE guest.name = ?);";
            PreparedStatement updateInvoiceStatement = connection.prepareStatement(updateInvoice);
            updateInvoiceStatement.setDouble(1, totalCost);
            updateInvoiceStatement.setString(2, name);
            updateInvoiceStatement.execute();
        }


    public void setCheckOutText(){
        checkOutText.setText("Are you sure you want to check out: " + name + "?");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCheckOutText();
        
        //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    } 

    @FXML
    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/staffpage");
    }
    
}
