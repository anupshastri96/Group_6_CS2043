package hotel.UI.Staff.ModifyStay.ModifyStayOptions;

import java.io.IOException;

import hotel.WelcomePage;

public class changeRoomController {

    public void processChangeRoomConfirmation() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/changeRoom");
    }

    public void processChangeRoom() throws IOException{
        //nothing yet
    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
    }
    
}
