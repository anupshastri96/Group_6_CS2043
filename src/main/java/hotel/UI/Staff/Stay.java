package hotel.UI.Staff;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Stay {
    private final SimpleIntegerProperty stayId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty guestId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty roomNumber = new SimpleIntegerProperty();
    private final SimpleObjectProperty<LocalDate> checkinDate = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<LocalDate> checkoutDate = new SimpleObjectProperty<>();
    private final SimpleStringProperty guestName = new SimpleStringProperty();
    private final SimpleBooleanProperty cancelledFlag = new SimpleBooleanProperty();
    private final SimpleDoubleProperty invoiceTotal = new SimpleDoubleProperty();

    // Constructors, getters, and setters

    public Stay() {
        // Default constructor
    }

    public Stay(int stayId, int guestId, int roomNumber, LocalDate checkinDate, LocalDate checkoutDate, String guestName, boolean cancelledFlag, double invoiceTotal) {
        setStayId(stayId);
        setGuestId(guestId);
        setRoomNumber(roomNumber);
        setCheckinDate(checkinDate);
        setCheckoutDate(checkoutDate);
        setGuestName(guestName);
        setCancelledFlag(cancelledFlag);
        setInvoiceTotal(invoiceTotal);
    }

    // Other methods...
    
    public boolean isCancelledFlag() {
        return cancelledFlag.get();
    }

    public SimpleBooleanProperty cancelledFlagProperty() {
        return cancelledFlag;
    }

    public void setCancelledFlag(boolean cancelledFlag) {
        this.cancelledFlag.set(cancelledFlag);
    }

    public double getInvoiceTotal() {
        return invoiceTotal.get();
    }

    public SimpleDoubleProperty invoiceTotalProperty() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal.set(invoiceTotal);
    }
    
    public int getStayId() {
        return stayId.get();
    }

    public SimpleIntegerProperty stayIdProperty() {
        return stayId;
    }

    public void setStayId(int stayId) {
        this.stayId.set(stayId);
    }

    public int getGuestId() {
        return guestId.get();
    }

    public SimpleIntegerProperty guestIdProperty() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId.set(guestId);
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

    public LocalDate getCheckinDate() {
        return checkinDate.get();
    }

    public SimpleObjectProperty<LocalDate> checkinDateProperty() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate.set(checkinDate);
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate.get();
    }

    public SimpleObjectProperty<LocalDate> checkoutDateProperty() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate.set(checkoutDate);
    }

    public String getGuestName() {
        return guestName.get();
    }

    public SimpleStringProperty guestNameProperty() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName.set(guestName);
    }
}