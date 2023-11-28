package hotel.UI.Staff.AdditionalServices.AdditionalServicesOptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import hotel.DbLoader;
import hotel.WelcomePage;
import hotel.UI.Staff.AdditionalServices.AdditionalServicesSearchController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

public class barController {

    private LocalDate DOB;

    @FXML
    private Text barText;

    public void processBar(Connection connection) throws IOException, SQLException{
            //again all of the costs should be a variable but this is faster for me its literally 1 am rn :(
         String name = AdditionalServicesSearchController.getServicesName();
            String sql = "UPDATE invoice\r\n" + //
                    "SET invoice_total = invoice_total + 24.99\r\n" + //
                    "FROM invoice\r\n" + //
                    "JOIN stay ON invoice.invoice_id = stay.invoice_id\r\n" + //
                    "JOIN guest ON stay.guest_id = guest.guest_id\r\n" + //
                    "WHERE guest.name = ?;\r\n" + //
                    "";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.setString(1, name);
            queryStmt.execute();
            WelcomePage.setRoot("Staff/AdditionalServices/additionalServices");

    }

    @FXML
    public void processBarConfirmation() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
            if (calcAge()>=19){
                processBar(connection);
            }
            else{
                System.out.println("You are not old enough to drink");
                barText.setText("You are not old enough to drink!");

            }
        } 
        catch (SQLException e) {
                e.printStackTrace();
                
                // Handle the exception appropriately
            }
    }


    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/AdditionalServices/additionalServices");
    }

    public int calcAge(){
        String sql = "SELECT date_of_birth FROM guest WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword());
        PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setString(1, AdditionalServicesSearchController.getServicesName());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Date dateOfBirth = resultSet.getDate("date_of_birth");
                    DOB = (dateOfBirth.toLocalDate());  // Assuming datePicker accepts a LocalDate
                    } 
                    else {
                        // Handle case where guest not found
                    }
            }
        } 

        catch (SQLException e) {
            e.printStackTrace();  // Handle SQLException appropriately
        }
        LocalDate currDate = LocalDate.now();
        
        if ((DOB != null) && DOB.isBefore(currDate) || DOB.isEqual(currDate)){
            return Period.between(DOB,currDate).getYears();
        }
        else{
            return 0;
        }
    }
    
}
