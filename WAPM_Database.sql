Drop database WAPM_Database;
Create database WAPM_Database;
use WAPM_Database;

create table order_detail(
    order_Id varchar(20) NOT NULL,
    customer_Name varchar (20),
    primary key (orderId)
)engine=innodb;
