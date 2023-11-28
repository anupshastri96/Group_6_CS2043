CREATE PROCEDURE addRooms 
    @startingRoomNumber INT,
    @quantity INT,
    @roomTypeCode INT
AS
BEGIN
    DECLARE @roomIncrement INT;
    
    IF (
        EXISTS (SELECT 1 FROM room_type WHERE room_type = @roomTypeCode) 
        AND NOT EXISTS (
            SELECT 1 
            FROM room 
            WHERE room_number >= @startingRoomNumber 
            AND room_number <= @startingRoomNumber + @quantity - 1
        ) 
        AND @quantity >= 1
    )
    BEGIN
        SET @roomIncrement = 0;
        
        WHILE @roomIncrement < @quantity 
        BEGIN
            INSERT INTO room (room_type, room_number) 
            VALUES (@roomTypeCode, @startingRoomNumber + @roomIncrement);
            
            SET @roomIncrement = @roomIncrement + 1;
        END;
    END;

    SELECT count(*) as NewRoomQuantity FROM room;
END;
GO
