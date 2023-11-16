import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import javafx.scene.control.DatePicker;


public class WelcomePage extends Application {
    private Stage primaryStage;
    private Scene scene;
    private VBox pane;
    
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
        Text prompt = new Text ("Are you a...");

        Button guestButton = new Button ("Guest");
        Button staffButton = new Button ("Staff");

        guestButton.setOnAction(this::processGuestPress);
        staffButton.setOnAction(this::processStaffPress);
        pane.getChildren().addAll(prompt,guestButton, staffButton);

        primaryStage.setWidth(300);
        primaryStage.setHeight(250);
    }


    public void processGuestPress(ActionEvent event) {
        pane.getChildren().clear();
        Button additionalServiceButton = new Button("Additional Services");
        Button checkOutButton = new Button("Check Out");
        Button exitButton = new Button("Exit");

        additionalServiceButton.setOnAction(this::processAdditionalService);
        checkOutButton.setOnAction(this::processCheckOut);
        exitButton.setOnAction(this::processExit);

        pane.getChildren().addAll(additionalServiceButton, checkOutButton, exitButton);
    }

    public void processCheckOut(ActionEvent event){
        System.out.println("Incomplete check out");
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

        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processPool(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to the Pool?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processBar(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to the Night Bar?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processBuffet(ActionEvent event){
        pane.getChildren().clear();
        Text prompt = new Text ("Do you want access to Breakfast Buffet?");
        
        Button yesButton = new Button("YES");
        Button noButton = new Button("NO");

        noButton.setOnAction(this::processNo);
        pane.getChildren().addAll(prompt, yesButton, noButton);
    }
    public void processNo(ActionEvent event){
        processAdditionalService(event);
    }
    public void processYes(ActionEvent event){
        
    }
    


    public void processStaffPress(ActionEvent event) {
        pane.getChildren().clear();
        Button guestRegistration = new Button("Guest Registration");
        Button modifyStayButton = new Button("Modify Stay");
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
        exitButton.setOnAction(this::processExit);

        pane.getChildren().addAll(guestRegistration, modifyStayButton, exitButton);
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
        System.out.println("Incomplete Enter process");
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
        Button cancelStayButton = new Button("Cancel Stay");
        Button changeRoomButton = new Button("Change Room");
        Button ExtendStayButton = new Button("Extend Stay");
        Button exitButton = new Button("Exit");

        cancelStayButton.setOnAction(this::processCancelStay);
        changeRoomButton.setOnAction(this::processChangeRoom);
        ExtendStayButton.setOnAction(this::processExtendStay);
        exitButton.setOnAction(this::processExitStaff);
        pane.getChildren().addAll(cancelStayButton, changeRoomButton, ExtendStayButton, exitButton);
    }

    public void processCancelStay(ActionEvent event){
        System.out.println("Incomplete cancel stay");
    }
    public void processChangeRoom(ActionEvent event){
        System.out.println("Incomplete change room");
    }
    public void processExtendStay(ActionEvent event){
        System.out.println("Incomplete Extend stay");
    }
}

