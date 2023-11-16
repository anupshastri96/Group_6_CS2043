import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hotel {
    private List <Rooms> rooms;
        
    public Hotel(){
        rooms = new ArrayList<>();
    }
    public void addRoom(Rooms room){
        rooms.add(room);
    }

    @Override
    public String toString(){
        String result = "";
        for(int i = 0; i < rooms.size(); i++){
            result += rooms.get(i) + "\n";
        }
        return result;
    }
    public static void main(String[] args){
        Hotel hotel1 = new Hotel();
        
        RoomType type1 = new RoomType("One King Bed",Arrays.asList("Sleeps 2", "Chaise Lounge"));
        RoomType type2 = new RoomType("Two Queen Beds", Arrays.asList("Sleeps 4", "Chaise Lounge"));
        RoomType type3 = new RoomType("One King Bed with Sofabed", Arrays.asList("Sleeps 4", "Sofabed"));
        RoomType type4 = new RoomType("Junior Suite One King Bed", Arrays.asList("Sleeps 4", "Chaise Lounge", "Sofabed", "Seating Area"));
        RoomType type5 = new RoomType("Junior Suite One King Bed with Kitchenette", Arrays.asList("Sleeps 2", "Kitchen", "Complimentary Breakfast"));
        RoomType type6 = new RoomType("Executive Floor One King Bed", Arrays.asList("Sleeps 2", "Chaise Lounge", "Complimentary Breakfast"));       
        RoomType type7 = new RoomType("Executive Floor Two Queen Beds", Arrays.asList("Sleeps 4", "Chaise Lounge", "Complimentary Breakfast"));
        RoomType type8 = new RoomType("Rosedale Studio King Suite Top Floor", Arrays.asList("Sleeps 2", "Seating Area"));
        RoomType type9 = new RoomType("High Park Studio King Suite Top Floor", Arrays.asList("Sleeps 2", "Complimentary Breakfast"));
        RoomType type10 = new RoomType("The Annex 1 Bedroom King Suite Top Floor", Arrays.asList("Sleeps 2", "Kitchen", "Living Room", "Large Table", "Complimentary Breakfast"));
        RoomType type11 = new RoomType("Forest Hill Studio King Suite Top Floor", Arrays.asList("Sleeps 2", "Living Room", "Standing Tub with Handheld Shower", "Complimentary Breakfast"));
        
        Rooms room1 = new Rooms(type1, true);
        Rooms room2 = new Rooms(type2, false);
        Rooms room3 = new Rooms(type6, true);
        Rooms room4 = new Rooms(type8, false);
        Rooms room5 = new Rooms(type2, true);
        Rooms room6 = new Rooms(type4, false);
        Rooms room7 = new Rooms(type11, true);
        Rooms room8 = new Rooms(type9, true);
        Rooms room9 = new Rooms(type7, false);
        Rooms room10 = new Rooms(type10, false);
        
        hotel1.addRoom(room1);
        hotel1.addRoom(room2);
        hotel1.addRoom(room3);
        hotel1.addRoom(room4);
        hotel1.addRoom(room5);
        hotel1.addRoom(room6);
        hotel1.addRoom(room7);
        hotel1.addRoom(room8);
        hotel1.addRoom(room9);
        hotel1.addRoom(room10);

        System.out.print(hotel1.toString());
    }
}

// https://www.hilton.com/en/hotels/torhihh-hilton-toronto/rooms/