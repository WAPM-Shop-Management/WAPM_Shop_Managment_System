Drop database WAPM_Database;
Create database WAPM_Database;
use WAPM_Database;

create table OrderDetail(
    orderId varchar(20) NOT NULL,
    primary key (orderId)
)engine=innodb;
