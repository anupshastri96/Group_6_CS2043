//payments
public class Payment {

    private double totalPrice;
	
	public Payment (double totalPriceIn) {
		price = totalPriceIn;
	}

    // Calculate the total price
    public double calculatePrice () {
        additionalServicesFees();
        // Cancel stay or cut stay short
        if (WelcomePage.cancelOrCut()) {
            // add room nightly fee for one night's stay
            // depending on room
            price += getFee();
        }

        // Extend stay
        if (WelcomePage.extend()) {
            // add room nightly fee * how many
            // days stay is extended for
            price += (getNightlyFee() * getExtension());
        }
        // Room state after checkout
            // add depending on how damaged
            // the room is (damage rate)
        if (RoomState.stateAfterCheckout()) {
            price += getDamageRate() * 9.99;
        }

        // Change or transfer to an upgraded room
        if (WelcomePage.changeRoom()) {
            if (WelcomePage.getNewRoomLevel() > RoomState.getRoomLevel()) {
                // additional fee for the change
                // depends on how large the upgrade is
                price += 39.99 * (WelcomePage.getNewRoomLevel() - RoomState.getRoomLevel());

                // change fee to that room fee
                // made up my own name for the method
                // should be in RoomState
                changeNightlyFee(getNewRoomLevel());
            }
        }

        // Checks out late
        if (WelcomePage.checkOut()) {
            // This needs fixing to subtract the two dates
            if (WelcomePage.actualCheckOutDate() > GuestRegistration.checkOutDate()) {
                int difference = WelcomePage.actualCheckOutDate() - GuestRegistration.checkOutDate();
                price += (getNightlyFee() * difference);
            }
        }

        // Discounts or coupon codes
        if (WelcomePage.applyDiscounts()) {
            // Needs method in WelcomePage.c
            // The method will check if
            // coupon is expired
            if (WelcomePage.discounts()) {
                price -= (0.15 * getFee());
            }
        }


        return price;
    }

    // Check if guest uses additional services

    public void additionalServicesFees() {
        if (WelcomePage.nightBar() && GuestRegistration.getAge() >= 19) {
            totalPrice += 24.99;
        }
        if (WelcomePage.gym()) {
            totalPrice += 9.99;
        }
        if (WelcomePage.pool()) {
            totalPrice += 19.99;
        }
        if (WelcomePage.breakfastBuffet()) {
            totalPrice += 14.99;
        }
    }

}