package hotel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.property.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
public class GuestRegistration {
    private static int guestIDCounter = 1;
    private static int stayIDCounter = 1;
    private IntegerProperty guestID;
    private IntegerProperty stayID;
    private StringProperty guestName;
    private StringProperty phoneNum;
    private StringProperty guestEmail;
    private StringProperty address;
    private LocalDate dateOfBirth;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private StringProperty guestDOB;
    private IntegerProperty roomType;
    private int invoiceID;
    private IntegerProperty roomNumber;
    private int successfulRoomNumber;
    
    //private int age;

    public GuestRegistration() {
        this.guestID = new SimpleIntegerProperty(guestIDCounter++);
        this.guestName = new SimpleStringProperty("");
        this.phoneNum = new SimpleStringProperty("");
        this.guestEmail = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.guestDOB = new SimpleStringProperty("");
        this.roomType = new SimpleIntegerProperty(0);
        this.roomNumber = new SimpleIntegerProperty(0);
    }

     public GuestRegistration(TextField guestNameIn, TextField guestPhoneNumberIn,
                             TextField guestEmailIn, TextField guestAddressIn, DatePicker dateOfBirthIn, DatePicker checkInField, DatePicker checkOutField, int roomTypeIn) {
        this.guestID = new SimpleIntegerProperty(guestIDCounter++);
        this.stayID = new SimpleIntegerProperty(stayIDCounter++);
        this.guestName = new SimpleStringProperty(guestNameIn.getText());
        this.phoneNum = new SimpleStringProperty(guestPhoneNumberIn.getText());
        this.guestEmail = new SimpleStringProperty(guestEmailIn.getText());
        this.address = new SimpleStringProperty(guestAddressIn.getText());
        this.dateOfBirth = dateOfBirthIn.getValue();
        this.checkInDate = checkInField.getValue();
        this.checkOutDate = checkOutField.getValue();
        this.roomType = new SimpleIntegerProperty(roomTypeIn);
        this.guestDOB = new SimpleStringProperty(dateOfBirthIn.getValue().toString());
        this.roomNumber = new SimpleIntegerProperty(0);
    }

    public void registerGuest(Connection connection) throws SQLException {
        int maxAttempts = 10;
        int attempts = 0;

        while (attempts < maxAttempts) {
            //this actually hurts my soul to look at but it works and I don't want to look at code anymore
            String roomString = roomType.get() + "0" + getRandomRoomNumber();
            roomNumber.set(Integer.parseInt(roomString));
            int potentialRoomNumber = Integer.parseInt(roomString);
        
            if (!checkRoomAvailability(connection, potentialRoomNumber)) {
                successfulRoomNumber = potentialRoomNumber;
                addToDb(connection);
                createInvoice(connection);
                addToStay(connection);
                break;
                 // Return the successful room number
            }
            else{
                System.out.println("Room " + potentialRoomNumber + " is occupied.");
            }
            attempts++;
        }

        
            //System.out.println("No available rooms after " + maxAttempts + " attempts.");
            // Handle the situation where no available rooms are found.
            //return;
    
    }

    private static int getRandomRoomNumber() {
        Random random = new Random();
        // Generate a random number between 0 and 9 (inclusive)
        return random.nextInt(10);
    }


    public boolean checkRoomAvailability(Connection connection, int roomNumberIn) throws SQLException {
        boolean roomStatus = false;
        String query = "SELECT room_status_flag FROM room WHERE room_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomNumberIn);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roomStatus = resultSet.getBoolean("room_status_flag");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return roomStatus;
    }

    public void addToStay(Connection connection) throws SQLException {
        String maxStayIDQuery = "SELECT MAX(stay_id) FROM stay";
        try (PreparedStatement maxStayIDStatement = connection.prepareStatement(maxStayIDQuery);
             ResultSet resultSet = maxStayIDStatement.executeQuery()) {

            if (resultSet.next()) {
                int maxStayID = resultSet.getInt(1);

                stayID.set(maxStayID + 1);
            }
        }

        //make function to choose free room (at random?) then set room as occupied (smth like this for room assignment?)
        String insertQuery = "INSERT INTO stay (stay_id, guest_id, room_number, checkin_date, checkout_date, invoice_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, stayID.get());
            preparedStatement.setInt(2, guestID.get());
            preparedStatement.setInt(3, successfulRoomNumber); //make function to choose free room (at random?) then set room as occupied
            preparedStatement.setDate(4, java.sql.Date.valueOf(checkInDate));
            preparedStatement.setDate(5, java.sql.Date.valueOf(checkOutDate));
            preparedStatement.setInt(6, invoiceID);
            preparedStatement.executeUpdate();
            //connection.close(); // Close the connection after executing the SQL statement
        }
        catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

    }

    
    public void createInvoice(Connection connection) throws SQLException {
            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the guestID for the new guest
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String insertQuery = "INSERT INTO invoice (invoice_id, invoice_total) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, invoiceID);  // Provide the incremented guest_id
                    preparedStatement.setInt(2, 0);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                }

    }
    

    public void addToDb(Connection connection) throws SQLException {
        String maxGuestIDQuery = "SELECT MAX(guest_id) FROM guest";
        try (PreparedStatement maxGuestIDStatement = connection.prepareStatement(maxGuestIDQuery);
             ResultSet resultSet = maxGuestIDStatement.executeQuery()) {

            if (resultSet.next()) {
                int maxGuestID = resultSet.getInt(1);

                // Increment the guestID for the new guest
                guestID.set(maxGuestID + 1);
            }
        }
        // Insert the guest information into the database
        String insertQuery = "INSERT INTO guest (guest_id, name, address, email, phone, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, guestID.get());  // Provide the incremented guest_id
            preparedStatement.setString(2, guestName.get());
            preparedStatement.setString(3, address.get());
            preparedStatement.setString(4, guestEmail.get());
            preparedStatement.setString(5, phoneNum.get());
            preparedStatement.setDate(6, java.sql.Date.valueOf(dateOfBirth));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

    }

    public static List<GuestRegistration> getGuestsFromDb(Connection connection) throws SQLException {
        List<GuestRegistration> guests = new ArrayList<>();
        String query = "SELECT * FROM guest";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int guestId = resultSet.getInt("guest_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String addr = resultSet.getString("address");
                LocalDate dob = resultSet.getDate("date_of_birth").toLocalDate();

                GuestRegistration guest = new GuestRegistration();
                guest.guestID.set(guestId);
                guest.guestName.set(name);
                guest.phoneNum.set(phone);
                guest.guestEmail.set(email);
                guest.address.set(addr);
                guest.guestDOB.set(dob.toString());

                guests.add(guest);
            }
        }
        return guests;
    }

    public static List<GuestRegistration> convertToGuestList(Connection connection) throws SQLException {
        return getGuestsFromDb(connection);
    }

    public int getGuestID() {
        return guestID.get();
    }

    public IntegerProperty guestIDProperty() {
        return guestID;
    }

    public void setGuestID(int id) {
        guestID.set(id);
    }

    public String getGuestName() {
        return guestName.get();
    }

    public StringProperty guestNameProperty() {
        return guestName;
    }

    public void setGuestName(String name) {
        guestName.set(name);
    }

    public String getPhoneNum() {
        return phoneNum.get();
    }

    public StringProperty phoneNumProperty() {
        return phoneNum;
    }

    public void setPhoneNum(String phone) {
        phoneNum.set(phone);
    }

    public String getGuestEmail() {
        return guestEmail.get();
    }

    public StringProperty guestEmailProperty() {
        return guestEmail;
    }

    public void setGuestEmail(String email) {
        guestEmail.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty guestAddressProperty() {
        return address;
    }

    public void setAddress(String addr) {
        address.set(addr);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        guestDOB.set(dateOfBirth.toString());
    }


    public StringProperty guestDOBProperty() {
        return guestDOB;
    }

    public int getRoomNumber() {
        return roomNumber.get();
    }

    public IntegerProperty roomNumberProperty() {
        return roomNumber;
    }



}
