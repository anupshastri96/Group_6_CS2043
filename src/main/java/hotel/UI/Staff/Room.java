package hotel.UI.Staff;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room {
    private final SimpleIntegerProperty roomNumber = new SimpleIntegerProperty();
    private final SimpleStringProperty roomTypeName = new SimpleStringProperty();
    private final SimpleBooleanProperty roomFlag = new SimpleBooleanProperty();

    // Constructors, getters, and setters

    public Room() {
        // Default constructor
    }

    public Room(int roomNumber, String roomTypeName, boolean roomFlag) {
        setRoomNumber(roomNumber);
        setRoomTypeName(roomTypeName);
        setRoomFlag(roomFlag);
    }

    // Other methods...
    
    public boolean getRoomFlag() {
        return roomFlag.get();
    }

    public SimpleBooleanProperty roomFlagProperty() {
        return roomFlag;
    }

    public void setRoomFlag(boolean roomFlag) {
        this.roomFlag.set(roomFlag);
    }

    public int getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public String getRoomTypeName() {
        return roomTypeName.get();
    }

    public SimpleStringProperty roomTypeNameProperty() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName.set(roomTypeName);
    }
}
