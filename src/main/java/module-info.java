module hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires transitive javafx.base;

    opens hotel to javafx.fxml;
    exports hotel;

    opens hotel.UI to javafx.fxml;
    exports hotel.UI;

    opens hotel.UI.Staff to javafx.fxml;
    exports hotel.UI.Staff;

    opens hotel.UI.Staff.GuestManagement to javafx.fxml;
    exports hotel.UI.Staff.GuestManagement;

    opens hotel.UI.Staff.ModifyStay to javafx.fxml;
    exports hotel.UI.Staff.ModifyStay;

    opens hotel.UI.Staff.AdditionalServices to javafx.fxml;
    exports hotel.UI.Staff.AdditionalServices;

    opens hotel.UI.Staff.ModifyStay.ModifyStayOptions to javafx.fxml;
    exports hotel.UI.Staff.ModifyStay.ModifyStayOptions;

    opens hotel.UI.Staff.AdditionalServices.AdditionalServicesOptions to javafx.fxml;
    exports hotel.UI.Staff.AdditionalServices.AdditionalServicesOptions;

    opens hotel.UI.Staff.checkOut to javafx.fxml;
    exports hotel.UI.Staff.checkOut;
    
}


