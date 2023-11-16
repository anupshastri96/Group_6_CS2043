import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomType{
    private static int uniqueTypeID = 0;
    
    private String nameRoomType;
    private List<String> features = new ArrayList<>();

    public RoomType(String nameIn, List<String> featuresIn){
        this.uniqueTypeID++;
        this.nameRoomType = nameIn;
        this.features = featuresIn;
    }

    public int getTypeID(){
        return uniqueTypeID;
    }

    public String getTypeName(){
        return nameRoomType;
    }

    public String getFeatures(){
        String result = "";
        for(int i = 0; i < features.size(); i++){
            if(i < features.size() - 1){
                result += features.get(i) + ", ";
            }
            if(i == features.size() - 1){
                result += features.get(i);
            }
        }
        return result;
    }

    @Override
    public String toString(){
        return this.getTypeName() + "\t[" + this.getFeatures() + "]";
    }
    public static void main(String[] args){
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
        List <RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(type1);
        roomTypeList.add(type2);
        roomTypeList.add(type3);
        roomTypeList.add(type4);
        roomTypeList.add(type5);
        roomTypeList.add(type6);
        roomTypeList.add(type7);
        roomTypeList.add(type8);
        roomTypeList.add(type9);
        roomTypeList.add(type10);
        roomTypeList.add(type11);

        for(int i = 0; i < roomTypeList.size(); i++){
            System.out.println(roomTypeList.get(i).toString());
        }

    }
}