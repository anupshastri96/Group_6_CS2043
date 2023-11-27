import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import javafx.scene.control.DatePicker;


public class WelcomePage extends Application {
    private Stage primaryStage;
    private Scene scene;
    private VBox pane;  
    private GuestRegistration guestRegistration;  

    TextField roomTypeField;
    TextField roomNumEntered;
    TextField searchField;
    TextField guestNameIn;
    DatePicker DateOfBirthIn;
    TextField guestPhoneNumberIn;
    TextField guestEmailIn;
    TextField guestAddressIn;
    DatePicker checkInField;
    public DbLoader dbLoader = DbLoader.loadFromCredentialsFile("credentials.txt");
    //I will share credentials.txt seperately please email me at jonathanrobichaud@icloud.com 
    private DatePicker checkOutField;

    public void start (Stage primaryStage) {
    
        this.primaryStage = primaryStage;
        this.pane = new VBox(10.0);
        this.pane.setAlignment(Pos.CENTER);
        this.scene = new Scene(pane, 300, 250);

        welcomeScene();
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void welcomeScene(){
        pane.getChildren().clear();
        Text welcome = new Text("Welcome!");
        Text prompt = new Text ("Are you a...");

        Button guestButton = new Button ("Guest");
        Button staffButton = new Button ("Staff");

        guestButton.setOnAction(this::processGuestPress);
        staffButton.setOnAction(this::processStaffPress);

        pane.getChildren().addAll(welcome, prompt,guestButton, staffButton);

        primaryStage.setWidth(300);
        primaryStage.setHeight(250);
    }


    public void processGuestPress(ActionEvent event) {
        pane.getChildren().clear();
        searchField = new TextField();
        searchField.setPromptText("Enter Guest Name or ID");

        Button searchButton = new Button("Search");
        Button exitButton = new Button("Exit");

        searchButton.setOnAction(arg0 -> {
            try {
                processSearchGuest(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(this::processExit);

        pane.getChildren().addAll(searchField, searchButton, exitButton);
    }

    public void processSearchGuest(ActionEvent event) throws SQLException{
        pane.getChildren().clear();

        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = "+ searchField.getText();

        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        result.next();
        String dbName = result.getString("name");

        if(searchField.getText() == dbName){
            Button additionalServiceButton = new Button("Additional Services");
            Button checkOutButton = new Button("Check Out");
            Button exitButton = new Button("Exit");

            additionalServiceButton.setOnAction(this::processAdditionalService);
            checkOutButton.setOnAction(arg0 -> {
                try {
                    processCheckOut(arg0);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            exitButton.setOnAction(this::processExit);

            pane.getChildren().addAll(additionalServiceButton, checkOutButton, exitButton);
        }
        else{
            Text  error = new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }
    }

    public void processCheckOut(ActionEvent event) throws SQLException{
        //double total = paymentProcess.calculatePrice();
        double total = 0.0;

        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = "+ searchField.getText();

        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        result.next();
        String dbName = result.getString("name");
        int dbStayID = result.getInt("saty_id");
        int invoiceID = result.getInt("invoice_id");

        if(dbName == searchField.getText() ){
            //calculate the invoice total 
            String queryCheckOut = "SELECT invoice_id FROM invoice join stay ON invoice.invoice_id WHERE stay_id = " + dbStayID;
            PreparedStatement queryCheckOuStatement = connection.prepareStatement(queryCheckOut);
            ResultSet invoiceAmt = queryCheckOuStatement.executeQuery();

            while(invoiceAmt.next()){
                total += invoiceAmt.getDouble("invoice_total");
            }

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the invoiceID for the new invoice
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, total)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

            Text text = new Text("Total Amount: " + total);
            pane.getChildren().addAll(text);
        }
        else{
            Text  error = new Text ("Error");
            pane.getChildren().addAll(error);
        }
    }

    public void processAdditionalService(ActionEvent event){
        pane.getChildren().clear();
        Button gymButton = new Button("Gym");
        Button poolButton = new Button("Pool");
        Button barButton = new Button("Night Bar");
        Button breakfastBuffeButton = new Button("Breakfast Buffet");
        Button exitButton = new Button("Exit");

        gymButton.setOnAction(this::processGym);
        poolButton.setOnAction(this::processPool);
        barButton.setOnAction(this::processBar);
        breakfastBuffeButton.setOnAction(this::processBuffet);
        exitButton.setOnAction(this::processExit);

        pane.getChildren().addAll(gymButton, poolButton, barButton, breakfastBuffeButton, exitButton);
    }
    public void processExit(ActionEvent event){
        welcomeScene();
    }
    public void processGym(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to the Gym?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(arg0 -> {
            try {
                processYesGym(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processYesGym(ActionEvent event)throws SQLException{
        double fee = 9.99;
        //double fee = paymentProcess.calculatePrice();

        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE()";
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        
        result.next();
        String dbName = result.getString("name");
        int invoiceID = result.getInt("invoice_id");

        if(searchField.getText() == dbName){

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the guestID for the new guest
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, fee)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

        }
        else{
            Text  error = new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }
    }

    public void processPool(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to the Pool?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(arg0 -> {
            try {
                processYesPool(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processYesPool(ActionEvent event)throws SQLException{
        double fee = 19.99;
        //double total = paymentProcess.calculatePrice();

        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE()";
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        
        result.next();
        String dbName = result.getString("name");
        int invoiceID = result.getInt("invoice_id");

        if(searchField.getText() == dbName){

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the guestID for the new guest
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, fee)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

        }
        else{
            Text  error = new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }

    }

    public void processBar(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to the Night Bar?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(arg0 -> {
            try {
                processYesNightBar(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }

    public int calcAge(DatePicker date){
        LocalDate DOB = date.getValue();
        LocalDate currDate = LocalDate.now();
        
        if ((DOB != null) && DOB.isBefore(currDate) || DOB.isEqual(currDate)){
            return Period.between(DOB,currDate).getYears();
        }
        else{
            return 0;
        }
    }
    public void processYesNightBar(ActionEvent event)throws SQLException{
        // Also check the guest's age
        // Get age from GuestRegistration or database
        double fee = 24.99;
        //double total = paymentProcess.calculatePrice();

        //incomplete
        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE()";
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        
        result.next();
        String dbName = result.getString("name");
        int invoiceID = result.getInt("invoice_id");
       
        if(searchField.getText() == dbName && calcAge(DateOfBirthIn) >= 19){

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the invoiceID for the new invoice
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, fee)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

        }
        else{
            Text  error = new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }
    }

    public void processBuffet(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to Breakfast Buffet?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(arg0 -> {
            try {
                processYesBreakfastBuffet(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processYesBreakfastBuffet(ActionEvent event)throws SQLException{
        double fee= 14.99;
        //double total = paymentProcess.calculatePrice();

        //incomplete
        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE()";
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        
        result.next();
        String dbName = result.getString("name");
        int invoiceID = result.getInt("invoice_id");

        if(searchField.getText() == dbName){

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the guestID for the new guest
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, fee)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

        }
        else{
            Text  error = new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }

    }

    public void processNo(ActionEvent event){
        processAdditionalService(event);
    }
    

    public void processStaffPress(ActionEvent event) {
        pane.getChildren().clear();
        Button guestRegistration = new Button("Guest Registration");
        Button modifyStayButton = new Button("Modify Stay");
        Button checkOutButton = new Button("Check Out");
        Button exitButton = new Button("Exit");
        
        guestRegistration.setOnAction(arg0 -> {
            try {
                processGuestRegistration(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        modifyStayButton.setOnAction(this::processModifyStay);
        checkOutButton.setOnAction(arg0 -> {
            try {
                processCheckOut(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(this::processExit);

        pane.getChildren().addAll(guestRegistration, modifyStayButton, checkOutButton, exitButton);
    }
    public void processGuestRegistration(ActionEvent event) throws SQLException { 
        pane.getChildren().clear();
        
        Text guestName = new Text ("Name: ");
        Text DateOfBirth = new Text ("DOB: ");
        Text guestPhoneNumber = new Text ("Phone Number: ");
        Text guestEmail = new Text ("Email: ");
        Text guestAddress = new Text ("Address: ");
        Text checkIn = new Text ("Check In Date: ");
        Text checkOut = new Text ("Check Out Date: ");
        guestNameIn = new TextField();
        DateOfBirthIn = new DatePicker();
        guestPhoneNumberIn = new TextField();
        guestEmailIn = new TextField();
        guestAddressIn = new TextField();
        checkInField = new DatePicker();
        checkOutField = new DatePicker();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(50);
        gridPane.setVgap(20);

        HBox buttons = new HBox();
        buttons.setSpacing(10.0);

        Button enterButton = new Button("Enter");
        Button clearButton = new Button("Clear");
        Button exitButton = new Button("Exit");
        
        enterButton.setOnAction(arg0 -> {
            try {
                processEnter(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        clearButton.setOnAction(this::processClear);
        exitButton.setOnAction(this::processExitStaff);

        buttons.getChildren().addAll(enterButton, clearButton, exitButton);
        gridPane.add(guestName, 0, 0);
        gridPane.add(guestNameIn, 1, 0);
        gridPane.add(DateOfBirth, 0, 1);
        gridPane.add(DateOfBirthIn, 1, 1);
        gridPane.add(guestPhoneNumber, 0, 2);
        gridPane.add(guestPhoneNumberIn, 1, 2);
        gridPane.add(guestEmail, 0, 3);
        gridPane.add(guestEmailIn, 1, 3);
        gridPane.add(guestAddress, 0, 4);
        gridPane.add(guestAddressIn, 1, 4);
        gridPane.add(checkIn, 0, 5);
        gridPane.add(checkInField, 1, 5);
        gridPane.add(checkOut, 0, 6);
        gridPane.add(checkOutField, 1, 6);
        gridPane.add(buttons, 0, 7, 2, 1);

        pane.getChildren().add(gridPane);
        primaryStage.setWidth(500); 
        primaryStage.setHeight(500);
    }
    public void processEnter(ActionEvent event) throws SQLException{
        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        GuestRegistration guest = new GuestRegistration(connection, guestNameIn, guestPhoneNumberIn, guestEmailIn, guestAddressIn, DateOfBirthIn);
        guest.addToDb();

        pane.getChildren().clear();

        Text roomType = new Text("Enter the room Type number: ");
        roomTypeField = new TextField();

        Label label1 = new Label("RoomType 1 : Single Room");
        Label label2 = new Label("RoomType 2 : Double Room");
        Label label3 = new Label("RoomType 3 : Queen Room");
        Label label4 = new Label("RoomType 4 : King Room");

        Button enterButton = new Button("Enter");
        Button exitButton = new Button("Exit");

        enterButton.setOnAction(arg0 -> {
            try {
                processEnterRoomAssign(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(this::processExitStaff);

        pane.getChildren().addAll(roomType, roomTypeField, enterButton, exitButton, label1, label2, label3, label4);
        
    }
    public void processEnterRoomAssign(ActionEvent event) throws SQLException{
        pane.getChildren().clear();

        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND checkout_date > GETDATE()";
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        
        result.next();
        String dbName = result.getString("name");
        //int invoiceID = result.getInt("invoice_id");
        int roomTypeNum = Integer.parseInt(roomTypeField.getText());
        int stayID = result.getInt("stay_id");

        String roomQuery = "SELECT room_number FROM room WHERE room_type = roomTypeNum AND room_status_flag = 0";
        PreparedStatement roomStatement = connection.prepareStatement(roomQuery);
        ResultSet roomResult = roomStatement.executeQuery();

        if(roomResult.next()){
            int assignRoomNum = roomResult.getInt("room_number");
            String updateRoom = "UPDATE stay SET room_number = assignRoomNum AND room_type = roomTypeNum WHERE stay_id = stayID";
            PreparedStatement updateRoomStmt = connection.prepareStatement(updateRoom);
            ResultSet updateRoomResult = updateRoomStmt.executeQuery();

            Text text = new Text("The room assigned for the guest is: " + assignRoomNum);
            pane.getChildren().addAll(text);
        }
        else{
            Text error = new Text("No rooms available");
            pane.getChildren().addAll(error);
        }
    }
  
    public void processClear(ActionEvent event){
        guestNameIn.setText("");
        DateOfBirthIn.setValue(null);
        guestPhoneNumberIn.setText("");
        guestEmailIn.setText("");
        guestAddressIn.setText("");
        checkInField.setValue(null);
        checkOutField.setValue(null);
    }
    public void processExitStaff(ActionEvent event){
        processStaffPress(event);
    }

    public void processModifyStay(ActionEvent event) {
        pane.getChildren().clear();

        searchField = new TextField();
        searchField.setPrefWidth(10);
        searchField.setPromptText("Enter Guest Name or ID");

        Button searchButton = new Button("Search");
        Button exitButton = new Button("Exit");

        searchButton.setOnAction(arg0 -> {
            try {
                processSearch(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        exitButton.setOnAction(this::processExitStaff);
        pane.getChildren().addAll(searchField, searchButton, exitButton);
    }

    public void processSearch(ActionEvent event) throws SQLException{
        pane.getChildren().clear();
        
        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = "+ searchField.getText();

        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        result.next();
        String dbName = result.getString("name");

        if(searchField.getText() == dbName){
            Button cancelStayButton = new Button("Cancel Stay");
            Button changeRoomButton = new Button("Change Room");
            Button ExtendStayButton = new Button("Extend Stay");
            Button exitButton = new Button("Exit");

            cancelStayButton.setOnAction(this::processCancelStay);
            changeRoomButton.setOnAction(this::processChangeRoom);
            ExtendStayButton.setOnAction(this::processExtendStay);
            exitButton.setOnAction(this::processNoExtend);

            pane.getChildren().addAll(cancelStayButton, changeRoomButton, ExtendStayButton, exitButton);
        }
        else{
            Text  error= new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }
    }

    public void processCancelStay(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Are you sure you want to cancel your stay?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(arg0 -> {
            try {
                processYesCancel(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        noButton.setOnAction(this::processNoExtend);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processYesCancel(ActionEvent event)throws SQLException{
        //double total = paymentProcess.calculatePrice();
        double cancelFee = 9.99;
        
        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = "+ searchField.getText();

        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        result.next();
        String dbName = result.getString("name");
        int dbReservationID = result.getInt("reservation_id");
        int invoiceID = result.getInt("invoice_id");

        if(dbName == searchField.getText()){
            //insert in the invoice

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);
                    
                    // Increment the invoiceID for the new invoice
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, cancelFee)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

            String updateFlag = "UPDATE reservation set cancelled_flag = 1 WHERE reservation_id = ?";
            PreparedStatement updateQuery = connection.prepareStatement(updateFlag);
            
            updateQuery.setInt(1, dbReservationID);
            updateQuery.executeQuery();
        }
        else{
            Text  error= new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }
    }

    public void processChangeRoom(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Are you sure you want to change your room?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(this::processYesChange);
        noButton.setOnAction(this::processNoExtend);
        pane.getChildren().addAll(prompt, yesButton, noButton); 
    }
    public void processYesChange(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Enter the new room number: ");
        roomNumEntered = new TextField();

        Button changeEnterButton = new Button("ENTER");
        Button exitButton = new Button("Exit");

        changeEnterButton.setOnAction((arg0 -> {
            try {
                processEnterchange(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }));
        exitButton.setOnAction(this::processNoExtend);
        pane.getChildren().addAll(prompt, changeEnterButton, exitButton); 
    }

    public void processEnterchange(ActionEvent event)throws SQLException{
        //double total = paymentProcess.calculatePrice();
        double fee;

        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = "+ searchField.getText();
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        result.next();

        String dbName = result.getString("name");
        int dbReservationID = result.getInt("reservation_id");
        int dbRoomNumber = result.getInt("room_number");
        int newRoomNum = Integer.parseInt(roomNumEntered.getText());
        int invoiceID = result.getInt("invoice_id");

        String roomQuery = "Select room_status_flag FROM room WHERE room_number = dbRoomNumber AND room_status_flag = 0";
        PreparedStatement roomStatement = connection.prepareStatement(roomQuery);
        ResultSet roomResult = roomStatement.executeQuery();
        int dbRoomStatus = roomResult.getInt("room_status_flag");
        int dbRoomType = roomResult.getInt("room_type");
        //insert in invoice

        if(dbName == searchField.getText() && newRoomNum != dbRoomNumber ){//to check if the guest exists and if the new room selected is not the same as the old room number
            
            if(roomResult.next() && dbRoomStatus == 0){ // to check if the room is available 
                long numNight = 0;
                LocalDate checkInDate = checkInField.getValue();
                LocalDate checkOuDate = checkOutField.getValue();
                if(checkInDate != null && checkOuDate != null){
                    numNight = ChronoUnit.DAYS.between(checkInDate, checkOuDate);
                }
                
                fee = 39.99 * dbRoomType * numNight;

                String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
                try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
                ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
                
                    if (resultSet.next()) {
                        int maxInvoiceID = resultSet.getInt(1);
                        
                        // Increment the invoiceID for the new invoice
                        invoiceID = maxInvoiceID + 1;
                    }
                }

                String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, fee)";
                PreparedStatement queryStmt = connection.prepareStatement(sql);
                queryStmt.executeQuery();

                String updateRoom = "UPDATE reservation SET room_number = dbRoomNumber WHERE reservation_id =" + dbReservationID;
                PreparedStatement updateRoomStmt = connection.prepareStatement(updateRoom);
                updateRoomStmt.executeQuery();

            }
        }
        else{
            Text  error= new Text ("No Guest available.");
            pane.getChildren().addAll(error);
        }

    }

    public void processExtendStay(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Are you sure you want to extend your stay?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        yesButton.setOnAction(this::processYesExtend);
        noButton.setOnAction(this::processNoExtend);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }

    DatePicker extendDate;
    public void processYesExtend(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Select the date you want to extend to: ");
        extendDate = new DatePicker();

        Button enterButtonExtend = new Button("Enter");
        Button exitButton = new Button("Exit");

        enterButtonExtend.setOnAction((arg0 -> {
            try {
                processEnterExtend(arg0);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }));
        exitButton.setOnAction(this::processNoExtend);
        pane.getChildren().addAll(prompt, extendDate, enterButtonExtend, exitButton);
        
       // double total = paymentProcess.calculatePrice();
    }
    public void processEnterExtend(ActionEvent event)throws SQLException{
        double extendFee;
        long numNightExtended = 0;
        LocalDate newExtendDate = extendDate.getValue();
        LocalDate checkInDate = checkInField.getValue();
        LocalDate checkOuDate = checkOutField.getValue();
        if(checkInDate != null && newExtendDate != null){
            numNightExtended = ChronoUnit.DAYS.between(newExtendDate, checkOuDate);
        }
    
        Connection connection = DriverManager.getConnection(dbLoader.getUrl(), dbLoader.getUsername(), dbLoader.getPassword());
        String query = "SELECT name FROM guest join stay ON guest.guestId WHERE checkin_date <= GETDATE() AND (checkout_date > GETDATE() OR checkout_date IS NULL) AND name = "+ searchField.getText();
        PreparedStatement queryStatement = connection.prepareStatement(query);
        ResultSet result = queryStatement.executeQuery();
        result.next();

        String dbName = result.getString("name");
        int dbReservationID = result.getInt("reservation_id");
        int dbStayID = result.getInt("stay_id");
        int dbGuestID = result.getInt("guest_id");
        int dbRoomNumber = result.getInt("room_number");

        String roomQuery = "Select room_status_flag FROM room WHERE room_number = dbRoomNumber AND room_status_flag = 0";
        PreparedStatement roomStatement = connection.prepareStatement(roomQuery);
        ResultSet roomResult = roomStatement.executeQuery();
        roomResult.next();
        int dbRoomStatus = roomResult.getInt("room_status_flag");
        int dbRoomType = roomResult.getInt("room_type");
        int invoiceID = result.getInt("invoice_id");

        if(dbName == searchField.getText() && dbRoomStatus == 0 ){
            //insert in the invoice
            extendFee = 39.99 * dbRoomType * numNightExtended;

            String maxInvoiceIDQuery = "SELECT MAX(invoice_id) FROM invoice";
            try (PreparedStatement maxInvoiceIDStatement = connection.prepareStatement(maxInvoiceIDQuery);
            ResultSet resultSet = maxInvoiceIDStatement.executeQuery()) {
            
                if (resultSet.next()) {
                    int maxInvoiceID = resultSet.getInt(1);

                    // Increment the invoiceID for the new invoice
                    invoiceID = maxInvoiceID + 1;
                }
            }

            String sql = "Insert INTO invoice (invoice_id, invoice_total) VALUES (invoiceID, extnedFee)";
            PreparedStatement queryStmt = connection.prepareStatement(sql);
            queryStmt.executeQuery();

            String extendQuery = "UPDATE stay SET ext_checkout_date = newExtendDate WHERE stay_id = "+ dbStayID;
            PreparedStatement extendQueryStmt = connection.prepareStatement(extendQuery);
            extendQueryStmt.executeQuery();
        }
    }
    public void processNoExtend(ActionEvent event){
        processModifyStay(event);
    }
}

