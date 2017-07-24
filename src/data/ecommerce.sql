
drop table orderline cascade constraints;
drop table orders cascade constraints;
drop table shopuser cascade constraints;
drop table person cascade constraints;
drop table product cascade constraints;
drop table creditcard cascade constraints;
drop table product2category cascade constraints;


create table creditcard (
    creditcard_id   number(10) PRIMARY KEY,
    cardname        varchar2(200) not null,
    cardnumber      varchar2(200) not null,
    expiry_date     date
);

create table product (
    product_id number(10) PRIMARY KEY,
    product_name varchar2(200),
    product_description varchar2(2000),
    price number(10,2),
    in_stock number(15,5)
);

create table product2category (
    product_id number(10) not null,
    categoryname varchar2(500) not null,
CONSTRAINT fk_product2category_product
    FOREIGN KEY (product_id)
    REFERENCES product (product_id)
);

create table person (
    person_id   number(10) PRIMARY KEY,
    firstname   varchar2(200) not null,
    lastname    varchar2(200) not null,
    birthdate   date,
    phone       varchar2(200),
    country     varchar2(200),
    city        varchar2(200),
    zip         varchar2(50),
    street      varchar2(200),
    creditcard_id   number(10),
CONSTRAINT fk_person_creditcard
    FOREIGN KEY (creditcard_id)
    REFERENCES creditcard (creditcard_id)
);

create table shopuser (
    username        varchar2(50) PRIMARY KEY,
    email           varchar2(100) not null,
    pwd             varchar2(100) not null,
    created_date    date,
    locked_date     date,
    person_id       number(10),
CONSTRAINT fk_shopuser_person
    FOREIGN KEY (person_id)
    REFERENCES person (person_id)
);

create table orders (
    order_id number(10) PRIMARY KEY,
    order_date date,
    username varchar2(50),
CONSTRAINT fk_orders_user
    FOREIGN KEY (username)
    REFERENCES shopuser (username)
);

create table orderline (
    orderline_id number(10) PRIMARY KEY,
    order_id number(10),
    product_id number(10),
    amount number(15,5),
--CONSTRAINT pk_orderline PRIMARY KEY (order_id, product_id),
CONSTRAINT fk_orderline_orders
    FOREIGN KEY (order_id)
    REFERENCES orders (order_id),
CONSTRAINT fk_orderline_product
    FOREIGN KEY (product_id)
    REFERENCES product (product_id)
);
