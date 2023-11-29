package hotel.UI.Staff.ModifyStay.ModifyStayOptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotel.DbLoader;
import hotel.WelcomePage;
import hotel.UI.Staff.AdditionalServices.AdditionalServicesSearchController;
import hotel.UI.Staff.ModifyStay.modifyStaySearchController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class changeRoomController {
    @FXML
    private ComboBox<String> roomType;

    @FXML
    private TextField roomNumber;

    public void processChangeRoomConfirmation() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/ChangeRoom");
    }

    public void processChangeRoom() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
            if (checkRooms(connection) == true){
                System.out.println("Room is occupied");
            }
            else{
                enterIntoDB(connection);
            }
        } 
        catch (SQLException e) {
                e.printStackTrace();
                
                // Handle the exception appropriately
            }
        
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
       
    }

    public boolean checkRooms(Connection connection) throws IOException, SQLException{
        
        boolean roomFlag = false;
        String checkRoomOccupancy = "SELECT room_status_flag\r\n" + //
                "FROM room\r\n" + //
                "where room_number = ?;";

        PreparedStatement queryStmtGet = connection.prepareStatement(checkRoomOccupancy);
        queryStmtGet.setInt(1, Integer.parseInt(roomNumber.getText()));
        try (ResultSet result = queryStmtGet.executeQuery()) {
            if (result.next()) {
                int roomStatus = result.getInt("room_status_flag");
                if (roomStatus == 1){
                    roomFlag = true;
                }
            } 
        }
        catch (SQLException e) {
            e.printStackTrace();
            
            // Handle the exception appropriately
        }
        return roomFlag;
    }

    public void enterIntoDB(Connection connection) throws IOException, SQLException {
        //update guest stay
        String name = modifyStaySearchController.getModifySearchName();
        String sql = "UPDATE stay\r\n" + //
                "SET room_number = ?\r\n" + //
                "FROM stay\r\n" + //
                "JOIN room ON stay.room_number = room.room_number\r\n" + //
                "JOIN guest ON stay.guest_id = guest.guest_id\r\n" + //
                "WHERE guest.name = ?;\r\n" + //
                "";

        PreparedStatement queryStmt = connection.prepareStatement(sql);
        queryStmt.setInt(1, Integer.parseInt(roomNumber.getText()));
        queryStmt.setString(2, name);
        queryStmt.execute();

        //update roomstatus
        //SET OLD ROOM AS FREE
        String roomUpdate = "UPDATE room SET room_status_flag = 0 WHERE room_number = ?;";
        PreparedStatement updateRoom = connection.prepareStatement(roomUpdate);
        updateRoom.setInt(1, Integer.parseInt(roomNumber.getText()));
        updateRoom.execute();

        //update invoice
        String invoiceUpdateString = "UPDATE invoice\r\n" + //
                    "SET invoice_total = invoice_total + 39.99\r\n" + //
                    "FROM invoice\r\n" + //
                    "JOIN stay ON invoice.invoice_id = stay.invoice_id\r\n" + //
                    "JOIN guest ON stay.guest_id = guest.guest_id\r\n" + //
                    "WHERE guest.name = ?;\r\n" + //
                    "";
            PreparedStatement invoiceUpdate = connection.prepareStatement(invoiceUpdateString);
            invoiceUpdate.setString(1, name);
            invoiceUpdate.execute();


    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
    }
    
}
