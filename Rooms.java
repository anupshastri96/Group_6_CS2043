import java.util.Arrays;


public class Rooms{
    private static int counter = 1;
    private RoomType roomType;
    private boolean isAvailable;
    private String uniqueRoomID = "";

    public Rooms (RoomType typeIn, boolean isAvailableIn){
        uniqueRoomID += counter++;
        roomType = typeIn;
        isAvailable = isAvailableIn;
    }

    public String getRoomID(){
        return this.uniqueRoomID;
    }

    public RoomType getRoomType(){
        return roomType;
    }
    
    public boolean getIsAvailble(){
        return isAvailable;
    }

    public void setIsAvailable (boolean status){
        this.isAvailable = status;
    }

    public void setRoomType(RoomType roomTypeIn){
        this.roomType = roomTypeIn;
    }

    @Override
    public String toString(){
        String availability = "";
        if(isAvailable){
            availability += "Available";
        }
        else{
            availability += "Not available";
        }
        return this.getRoomID() + "\t" + this.roomType.toString() + "\t" + availability;
    }
    public static void main(String[] args){
        RoomType type1 = new RoomType("One King Bed",Arrays.asList("Sleeps 2", "Chaise Lounge"));
        Rooms room1 = new Rooms(type1, true);
        System.out.println(room1.toString());
    }
}