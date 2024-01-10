drop table allocation;

create table allocation (
    order_code varchar(10) primary key,
    vehicle varchar(10),
    tonnage varchar(10),
    loading_area varchar(150),
    unloading_area varchar(150),
    shipping_cost varchar(10),
    commission varchar(10),
    payment_type varchar(10),
    shipper_name varchar(100),
    shipper_phone varchar(20),
    information varchar(255),
    registration_date date
);

