package hotel.UI.Staff.ModifyStay.ModifyStayOptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import hotel.DbLoader;
import hotel.WelcomePage;
import hotel.UI.Staff.ModifyStay.modifyStaySearchController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

public class extendStayController {

    @FXML
    private Text extendText;

    @FXML
    private DatePicker extendCheckout;

    LocalDate currDate = LocalDate.now();

    public void processExtendStayConfirmation() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/extendStay");
    }

    public void processExtendStay() throws IOException{
        try (Connection connection = DriverManager.getConnection(DbLoader.getUrl(), DbLoader.getUsername(), DbLoader.getPassword())) {
            if ((extendCheckout.getValue() != null) && extendCheckout.getValue().isBefore(currDate) || extendCheckout.getValue().isEqual(currDate)){
                extendText.setText("Invalid Date");
            }
            else{
                addToDb(connection);
            }
            
        } 
        catch (SQLException e) {
                e.printStackTrace();
                //WelcomePage.setRoot("Error");

            }
        
       
    }

    public void addToDb(Connection connection) throws IOException, SQLException{
        String name = modifyStaySearchController.getModifySearchName();
        //update invoice with fee
        String invoice = "UPDATE invoice\r\n" + //
                    "SET invoice_total = invoice_total + 39.99\r\n" + //
                    "FROM invoice\r\n" + //
                    "JOIN stay ON invoice.invoice_id = stay.invoice_id\r\n" + //
                    "JOIN guest ON stay.guest_id = guest.guest_id\r\n" + //
                    "WHERE guest.name = ?;\r\n" + //
                    "";
            PreparedStatement invoiceUpdate = connection.prepareStatement(invoice);
            invoiceUpdate.setString(1, name);
            invoiceUpdate.execute();

        //set new check out date
        String setExtend = "UPDATE stay\r\n" + //
                "SET checkout_date = ?\r\n" + //
                "WHERE guest_id IN (SELECT guest_id FROM guest WHERE name = ?);";
        PreparedStatement setExtendQuery = connection.prepareStatement(setExtend);
        setExtendQuery.setDate(1, java.sql.Date.valueOf(extendCheckout.getValue()));
        setExtendQuery.setString(2, name);
        setExtendQuery.execute();
        extendText.setText("Stay Extended!");
    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
    }
    
}
