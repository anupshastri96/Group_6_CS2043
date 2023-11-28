package hotel.UI.Staff.ModifyStay.ModifyStayOptions;

import java.io.IOException;

import hotel.WelcomePage;

public class extendStayController {

    public void processExtendStayConfirmation() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/extendStay");
    }

    public void processExtendStay() throws IOException{
        //nothing yet
    }

    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
    }
    
}
