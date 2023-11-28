DROP PROCEDURE IF EXISTS addGuests;

CREATE PROCEDURE addGuests(guestId INT, guestName varchar(40),guestPhoneNum varchar(20),guestAddress varchar(50), guestEmail varchar(40))
BEGIN
    INSERT INTO guest (guest_id, name, phone, address, email) VALUES (guestId, guestName, guestPhoneNum, guestAddress, guestEmail);

END
