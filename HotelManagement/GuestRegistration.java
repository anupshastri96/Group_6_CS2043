public class GuestRegistration{
    private static int guestID = 0;
    
    private String guestName;
    
    private String phoneNum;
    
    private String guestEmail;
    
    private String address;

    public GuestRegistration(String nameIn, String phoneNumIn,
    String emailIn, String addressIn){
        guestID++;
        guestName = nameIn;
        phoneNum = phoneNumIn;
        guestEmail = emailIn;
        address = addressIn;
    }

    public int getGuestID(){
        return guestID;
    }

    public String getGuestName(){
        return guestName
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
}