package hotel.UI.Staff.AdditionalServices.AdditionalServicesOptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotel.DbLoader;
import hotel.WelcomePage;
import hotel.UI.Staff.AdditionalServices.AdditionalServicesSearchController;
import javafx.fxml.FXML;

public class gymController {


    @FXML
    public void processGym(Connection connection) throws IOException, SQLException{
            
            String name = AdditionalServicesSearchController.getServicesName();
            String sql = "UPDATE invoice\r\n" + //
                    "SET invoice_total = invoice_total + 9.99\r\n" + //
                    "FROM invoice\r\n" + //
                    "JOIN stay ON invoice.invoice_id = stay.invoice_id\r\n" + //
                    "JOIN guest ON stay.guest_id = guest.guest_id\r\n" + //
                    "WHERE guest.name = ?;\r\n" + //
                    "";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.setString(1, name);
            queryStmt.execute();

        }

    @FXML
    public void processGymConfirmation() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
            processGym(connection);
        } 
        catch (SQLException e) {
                e.printStackTrace();
                
                // Handle the exception appropriately
            }
        
        WelcomePage.setRoot("Staff/AdditionalServices/additionalServices");
    }

    @FXML
    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/AdditionalServices/additionalServices");
    }
    
}
