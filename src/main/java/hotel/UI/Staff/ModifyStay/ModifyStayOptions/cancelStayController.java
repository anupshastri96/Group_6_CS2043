package hotel.UI.Staff.ModifyStay.ModifyStayOptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import hotel.DbLoader;
import hotel.WelcomePage;
import hotel.UI.Staff.ModifyStay.modifyStaySearchController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class cancelStayController {

    @FXML 
    private Text cancelText;

    @FXML
    private Button cancelYes;

    @FXML
    private Button cancelNo;

    private String name = modifyStaySearchController.getModifySearchName();

    @FXML
    public void processCancelStayConfirmation() throws IOException{
        //WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/cancelStay");
        processCancelStay();
        
        
    }


    @FXML
    public void processCancelStay() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
            addToDb(connection);
            cancelText.setText(name +"'s Stay Cancelled");
            cancelYes.setVisible(false);
            cancelNo.setText("Exit");
            
        } 
        catch (SQLException e) {
                e.printStackTrace();
                //WelcomePage.setRoot("Error");

            }
        
        //WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
       
    }

    public void addToDb(Connection connection) throws IOException, SQLException{
        

        //set room as unoccupied
        String oldRoomUpdate = "UPDATE room\r\n" + //
                "SET room_status_flag = 0\r\n" + //
                "WHERE room_number IN (SELECT stay.room_number FROM stay JOIN guest ON stay.guest_id = guest.guest_id WHERE guest.name = ?);";
        PreparedStatement updateOldRoom = connection.prepareStatement(oldRoomUpdate);
        updateOldRoom.setString(1, name);
        updateOldRoom.execute();

        //update invoice with fee
        String invoice = "UPDATE invoice\r\n" + //
                    "SET invoice_total = invoice_total + 9.99\r\n" + //
                    "FROM invoice\r\n" + //
                    "JOIN stay ON invoice.invoice_id = stay.invoice_id\r\n" + //
                    "JOIN guest ON stay.guest_id = guest.guest_id\r\n" + //
                    "WHERE guest.name = ?;\r\n" + //
                    "";
            PreparedStatement invoiceUpdate = connection.prepareStatement(invoice);
            invoiceUpdate.setString(1, name);
            invoiceUpdate.execute();

        //set stay as cancelled
        String setCancelled = "UPDATE stay\r\n" + //
                "SET cancelled_flag = 1\r\n" + //
                "WHERE guest_id IN (SELECT guest_id FROM guest WHERE name = ?);";
        PreparedStatement setCancelledQuery = connection.prepareStatement(setCancelled);
        setCancelledQuery.setString(1, name);
        setCancelledQuery.execute();
        cancelText.setText(name +"'s Stay Cancelled");
    }

    

    @FXML
    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
    }
    
}
