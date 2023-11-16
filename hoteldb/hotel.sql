--work in progress!
create table room_type 
( room_type    int unsigned not null primary key,
  room_type_name     varchar(30) not null,
  --king_bed_quantity  int unsigned not null,
 -- queen_bed_quantity int unsigned not null,
 -- what ammenities should be added and how?
  suggested_rate     decimal(7,2) default 0.00
);

--
--create table hotel
--( hotel_id       int unsigned not null primary key,
 -- hotel_name     varchar(40) not null,
 -- hotel_address  varchar(60)
--);
--
create table room
( --hotel_id          int unsigned not null,
  room_number       int unsigned not null,
  room_type         int unsigned,
  room_status_flag  tinyint default 0,
  primary key (room_number),
  --primary key (hotel_id, room_number),
  --foreign key (hotel_id) references hotel (hotel_id),
  foreign key (room_type) 
           references room_type (room_type)
);

create table guest
( guest_id       int unsigned not null primary key,
  name     varchar(40) not null,
  address    varchar(50),
  email    varchar(40),
  phone    varchar(20)
);

create table invoice
( invoice_id       int unsigned not null primary key,
  discount_code     varchar(40),
  --how to implement ammenities?
  --maybe a table of items person charged to their room?
  invoice_total             decimal(7,2) not null
);

create table reservation
( reservation_id  int unsigned not null primary key,
  guest_id       int unsigned not null,
  --hotel_id        int unsigned not null ,
  room_type  int unsigned not null,
  check_in_date   date not null, 
  check_out_date  date not null, 
  cancelled_flag  tinyint default 0,
  payment_flag    tinyint default 0,
  foreign key (guest_id) references guest (guest_id),
  --foreign key (hotel_id) references hotel (hotel_id),
  foreign key (room_type) 
           references room_type (room_type)
);

------------------------------------------------------------
-- A stay represents consecutive nights spent in a single room  
-- in one of the  hotels. If a guest has multiple rooms at 
-- the same time, then multiple stays are created in the 
-- database. This is so individual rooms can be checked out 
-- early or extended for extra nights.
------------------------------------------------------------
create table stay
( stay_id                  int unsigned not null primary key,
  guest_id                 int unsigned not null,
--  hotel_id                 int unsigned not null ,
  room_number              int unsigned not null,
  actual_checkin_date      date default null, 
  expected_checkout_date   date not null, 
  actual_checkout_date     date default null, 
  ext_checkout_date        date default null,
  early_checkout_date      date default null,
  --charged_rate             decimal(7,2) not null,
  invoice_id      int unsigned not null,
  foreign key (invoice_id) references invoice (invoice_id),
  foreign key (guest_id)   references guest (guest_id),
   foreign key (room_number) 
            references room (room_number)
  --foreign key (hotel_id, room_number) 
  --          references room (hotel_id, room_number)
);