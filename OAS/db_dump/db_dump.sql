create database oas;
use oas;

CREATE TABLE accountbalance
(
    Id INT PRIMARY KEY NOT NULL,
    DefaultAccountBalance DOUBLE NOT NULL
);
CREATE TABLE auction
(
    AuctionId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    StartDate VARCHAR(50),
    Duration VARCHAR(50),
    ItemName VARCHAR(50),
    ItemType VARCHAR(50),
    ItemStartingPrice DOUBLE,
    ItemDescription VARCHAR(500),
    ItemUrl VARCHAR(50),
    ItemCondition VARCHAR(50),
    ItemCountry VARCHAR(50),
    SellerId INT
);
CREATE TABLE notification
(
    UserId INT NOT NULL,
    Date VARCHAR(50),
    Context VARCHAR(300),
    Seen TINYINT
);
CREATE TABLE searchtype
(
    Type VARCHAR(50)
);
CREATE TABLE user
(
    UserId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(50) DEFAULT '' NOT NULL,
    LastName VARCHAR(50),
    Email VARCHAR(50) DEFAULT '' NOT NULL,
    Phone VARCHAR(50),
    Password VARCHAR(50),
    AccountBalance DOUBLE
);


INSERT INTO oas.accountbalance (Id, DefaultAccountBalance) VALUES (1, 300);


INSERT INTO oas.auction (AuctionId, StartDate, Duration, ItemName, ItemType, ItemStartingPrice, ItemDescription, ItemUrl, ItemCondition, ItemCountry, SellerId) VALUES (1, 'Sat Dec 01 05:24:00 GMT 2015', '3h 45min', 'Galaxy Case Cover', 'Case Cover', 23.5, 'Genuine Samsung Wireless Keyboard Stand Case Cover for Galaxy Tab S 8.4', 'caseCover1', 'New', 'Indonesia', 1);
INSERT INTO oas.auction (AuctionId, StartDate, Duration, ItemName, ItemType, ItemStartingPrice, ItemDescription, ItemUrl, ItemCondition, ItemCountry, SellerId) VALUES (2, 'Sat Dec 04 17:33:00 GMT 2015', '21h 13min', 'The Moon And Sixpence', 'Book', 3.5, '1941 Book The Moon And Sixpence By W. Somerset Maugham', 'book1', 'New', 'USA', 2);
INSERT INTO oas.auction (AuctionId, StartDate, Duration, ItemName, ItemType, ItemStartingPrice, ItemDescription, ItemUrl, ItemCondition, ItemCountry, SellerId) VALUES (3, 'Sat Dec 06 11:55:00 GMT 2015', '3h 56min', 'Modern Abstract Painting', 'Picture', 32, 'Modern Abstract Huge Wall Ornaments Canvas Oil Painting', 'picture1', 'New', 'China', 2);
INSERT INTO oas.auction (AuctionId, StartDate, Duration, ItemName, ItemType, ItemStartingPrice, ItemDescription, ItemUrl, ItemCondition, ItemCountry, SellerId) VALUES (4, 'Sat Dec 12 13:24:00 GMT 2015', '14h 20min', 'Hot Batmobile Chariot Blocks', 'Toy', 12, 'Hot Batmobile Chariot The Super Heroes Minifigure Building Blocks', 'toy1', 'New', 'Hong Kong', 1);



INSERT INTO oas.searchtype (Type) VALUES ('All Categories');
INSERT INTO oas.searchtype (Type) VALUES ('Book');
INSERT INTO oas.searchtype (Type) VALUES ('Case Cover');
INSERT INTO oas.searchtype (Type) VALUES ('Music');
INSERT INTO oas.searchtype (Type) VALUES ('Picture');
INSERT INTO oas.searchtype (Type) VALUES ('Sporting Goods');
INSERT INTO oas.searchtype (Type) VALUES ('Toy');

