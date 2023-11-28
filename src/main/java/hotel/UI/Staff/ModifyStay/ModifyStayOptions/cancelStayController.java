package hotel.UI.Staff.ModifyStay.ModifyStayOptions;

import java.io.IOException;

import hotel.WelcomePage;

public class cancelStayController {

    public void processCancelStayConfirmation() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/ModifyStayOptions/cancelStay");
    }

    public void processCancelStay() throws IOException{
        //nothing yet
    }


    public void processExit() throws IOException{
        WelcomePage.setRoot("Staff/ModifyStay/modifyStay");
    }
    
}
