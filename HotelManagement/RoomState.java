public class RoomState{
    private int roomNum;
    private boolean isAvailable;

    public RoomState(int roomNumIn, boolean isAvailableIn){
        roomNum = roomNumIn;
        isAvailable = isAvailableIn;
    }

    public int getRoomNum(){
        return roomNum;
    }

    public boolean getIsAvailble(){
        return isAvailable;
    }

    public void setRoomNum (int num){
        roomNum = num;
    }

    public void setIsAvailable (boolean status){
        isAvailable = status;
    }
}