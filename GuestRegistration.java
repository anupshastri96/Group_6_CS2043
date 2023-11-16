import java.time.LocalDate;
import java.time.Period;
public class GuestRegistration{
    private static int counter = 1;
    
    private String guestName;
    
    private String phoneNum;
    
    private String guestEmail;
    
    private String address;

    private String dateOfBirth; //format: "yyyy-MM-dd"
    
    private int age;

    private String guestID = "";

    public GuestRegistration(String nameIn, String phoneNumIn,
    String emailIn, String addressIn, String dateIn){
        guestID += counter++;
        guestName = nameIn;
        phoneNum = phoneNumIn;
        guestEmail = emailIn;
        address = addressIn;
        dateOfBirth = dateIn;
    }

    public String getGuestID(){
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

    public LocalDate getDOB(){
        return LocalDate.parse(dateOfBirth);
    }

    public int getAge(){
        LocalDate DOB = LocalDate.parse(dateOfBirth);
        LocalDate currDate = LocalDate.now();
        if((DOB!=null) && (currDate!=null)){
            return Period.between(DOB,currDate).getYears();
        }
        else{
            return 0;
        }
    }

    public void setGuestName(String nameIn){
        guestName = nameIn;
    }

    public void setPhoneNum(String numIn){
        guestName = nameIn;
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
            this.getGuestEmail() + "\t" + this.getAddress() + "\t" + dateOfBirth + "\t" + this.getAge(); 
    }
 public static void main(String[] args){
    GuestRegistration guest1 = new GuestRegistration("Ilgin Tokali", "5069981396", "ilgin.tokali@hotmail.com", "612 Hanson St.", "2004-12-26");
    System.out.print(guest1.toString());
 }   
}