import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
public class GuestRegistration{
    private static int counter = 1;
    
    private String guestName;
    
    private String phoneNum;
    
    private String guestEmail;
    
    private String address;

    private LocalDate dateOfBirth; //format: "yyyy-MM-dd"
    
    private int age;

    private int guestID;

    String addGuestsCall = "{call addGuests(?,?,?,?,?)}";
    Connection connection;

    public GuestRegistration(Connection connectionIn, TextField guestNameIn, TextField guestPhoneNumberIn,
    TextField guestEmailIn, TextField guestAddressIn, DatePicker dateOfBirthIn){
        connection = connectionIn;
        guestID += counter++;
        guestName = guestNameIn.getText();
        phoneNum = guestPhoneNumberIn.getText();
        guestEmail = guestEmailIn.getText();
        address = guestAddressIn.getText();
        dateOfBirth = dateOfBirthIn.getValue();
    }

    
    public void addToDb() throws SQLException{
        CallableStatement procedureCall = connection.prepareCall(addGuestsCall);
        procedureCall.setInt(1, guestID);
        procedureCall.setString(2, guestName);
        procedureCall.setString(3, phoneNum);
        procedureCall.setString(4, address);
        procedureCall.setString(5, guestEmail);
        
        //DOB not implemented yet!
        procedureCall.execute();
        connection.close();
        System.out.println(toString());
        
    }

    //WIP Will be implemented soon but currently out of scope
    /*public GuestRegistration getFromDb(int guestId) throws SQLException{


        private static PreparedStatement prepareGetGuest(Connection connection){
            String query = "select name,address,email,phone from guest where guest_id = ?";
            PreparedStatement result = connection.prepareStatement(query);    
            return result;
        }

        private static void formatGuest(PreparedStatement guestStatement) {

        }

        ResultSet 
        
        result.setInt(1, guestId);
        ResultSet result = result.executeQuery();

       
        
    }*/

    public int getGuestID(){
        return guestID;
    }

    public String getGuestName(){
        return guestName;
    }

    public String getPhoneNUm(){
        return phoneNum;
    }

    public String getGuestEmail(){
        return guestEmail;
    }

    public String getAddress(){
        return address;
    }

    //Date types in javafx don't really work with strings so we will
    //have to take a look at that
    /*public LocalDate getDOB(){
        return LocalDate.parse(dateOfBirth);
    }

    /*public int getAge(){
        LocalDate DOB = LocalDate.parse(dateOfBirth);
        LocalDate currDate = LocalDate.now();
        if((DOB!=null) && (currDate!=null)){
            return Period.between(DOB,currDate).getYears();
        }
        else{
            return 0;
        }
    }*/

    public void setGuestName(String nameIn){
        guestName = nameIn;
    }

    public void setPhoneNum(String numIn){
        guestName = numIn;
    }

    public void setGuestEmail(String emailIn){
        guestEmail = emailIn;
    }

    public void setAddress(String addressIn){
        address = addressIn;
    }

    @Override
    public String toString(){
        return this.getGuestID() + "\t" + this.getGuestName() + "\t" + this.getPhoneNUm() + "\t" +
            this.getGuestEmail() + "\t" + this.getAddress() + "\t" + dateOfBirth + "\t" /*+ this.getAge()*/; 
    }
 /*public static void main(String[] args){
    //GuestRegistration guest1 = new GuestRegistration("Ilgin Tokali", "5069981396", "ilgin.tokali@hotmail.com", "612 Hanson St.", "2004-12-26");
    //System.out.print(guest.toString());
 } */  
}
