USE SkiDataAPI;

INSERT INTO Resorts(ResortName)
    VALUES('Alex');
    
INSERT INTO Resorts(ResortName)
    VALUES('Bob');
    
INSERT INTO Seasons(Season, ResortId)
    VALUES(2019, 1);
    
INSERT INTO LiftRides(ResortId, SeasonId, DayId, SkierId, StartTime, LiftId, Vertical)
    VALUES(1, 2019, 1, 1, 1, 1, 10);
    
INSERT INTO LiftRides(ResortId, SeasonId, DayId, SkierId, StartTime, LiftId, Vertical)
    VALUES(1, 2019, 1, 1, 2, 2, 20);
    
INSERT INTO LiftRides(ResortId, SeasonId, DayId, SkierId, StartTime, LiftId, Vertical)
    VALUES(1, 2018, 1, 1, 2, 3, 40);
 --    
-- INSERT INTO Restaurants(Name, Menu, Hours, Active, Cuisine, Street1,
--  City, State, Zip, CompanyName)
--     VALUES('Safari Restaurant', 'menu1', 'hours1', True, 'AFRICAN', '1st street', 
--     'Seattle', 'Washington', '98001', Null);
-- INSERT INTO Restaurants(Name, Menu, Hours, Active, Cuisine, Street1,
--  City, State, Zip, CompanyName)
--      VALUES('Chick fill A', 'menu2', 'hours2', True, 'AMERICAN', '2nd Street',
--     'Seattle', 'Washington', '98002', 'Universal Restaurant');
-- INSERT INTO Restaurants(Name, Menu, Hours, Active, Cuisine, Street1,
--  City, State, Zip, CompanyName)
--     VALUES('Panda express', 'menu3', 'hours3', True, 'ASIAN', '3rd Street',
--     'Seattle', 'Washington', '98003', 'Pepsico');
-- INSERT INTO Restaurants(Name, Menu, Hours, Active, Cuisine, Street1,
--  City, State, Zip, CompanyName)
-- 	VALUES('Piroshki on 3rd', 'menu4', 'hours4', False, 'EUROPEAN', '3rd Street',
--     'Seattle', 'Washington', '98004', 'Pepsico');
-- INSERT INTO Restaurants(Name, Menu, Hours, Active, Cuisine, Street1,
--  City, State, Zip, CompanyName)
-- 	VALUES('La Conasupo', 'menu5', 'hours5', True, 'EUROPEAN', '5th Street',
--     'Charlottesville', 'Virginia', '22901',  Null);
-- INSERT INTO Restaurants(Name, Menu, Hours, Active, Cuisine, Street1,
--  City, State, Zip, CompanyName)
-- 	VALUES('Chipottle', 'menu6', 'hours6', True, 'HISPANIC', '6th Street',
--     'Charlottesville', 'Virginia', '22901', 'Universal Restaurant');
--     
--     
-- INSERT INTO SitDownRestaurant(RestaurantId, Capacity)
--     VALUES('1', '100');
-- INSERT INTO SitDownRestaurant(RestaurantId, Capacity)
--     VALUES('2', '85');
--     
-- INSERT INTO TakeOutRestaurant(RestaurantId, MaxWaitTime)
--     VALUES('3', '30');
-- INSERT INTO TakeOutRestaurant(RestaurantId, MaxWaitTime)
--     VALUES('4', '40');
-- 
-- INSERT INTO FoodCartRestaurant(RestaurantId, Licensed)
--     VALUES('5',  True);
-- INSERT INTO FoodCartRestaurant(RestaurantId, Licensed)
--     VALUES('6',  False);
-- 
-- INSERT INTO Users(UserName, Password, FirstName, LastName)
--     VALUES('TaylorS', 'taylor01', 'Taylor', 'Swift');
-- INSERT INTO Users(UserName, Password, FirstName, LastName)
--     VALUES('JonnyD', 'tjonny', 'Jonny', 'Depp');
-- 
-- INSERT INTO Reviews(Content, Rating, UserName, RestaurantId)
--     VALUES('Food taste great', '4.5', 'TaylorS', '1');
--  INSERT INTO Reviews(Content, Rating, UserName, RestaurantId)
--     VALUES('Nice atomosphere', '3.5', 'JonnyD', '2');   
--     
-- INSERT INTO Recommendations(UserName, RestaurantId)
--     VALUES('TaylorS', '3');
--  INSERT INTO Recommendations(UserName, RestaurantId)
--     VALUES('JonnyD', '4');   
-- 
-- 
-- INSERT INTO Reservations(Start, End, Size, UserName, RestaurantId)
--     VALUES('2018-10-08 12:00:00', '2018-10-08 14:00:00', '3', 'TaylorS', '1');
-- INSERT INTO Reservations(Start, End, Size, UserName, RestaurantId)
--     VALUES('2018-10-10 17:00:00', '2018-10-10 17:00:00', '6', 'JonnyD', '2');
-- 
-- 
-- 
-- LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/assignments/assignment2/creditcards.csv' INTO TABLE CreditCards
--   FIELDS TERMINATED BY ',' ENCLOSED BY '"'
--   LINES TERMINATED BY '\n'
--   IGNORE 1 LINES;
-- 
-- /**
-- Content of creditcards.csv
-- CardNumber, Expiration, UserName
-- "1111222233334444","2020-10-20","TaylorS"
-- "5555666677778888","2019-12-24","JonnyD"
-- */
-- 
-- 
--     
--     
--     
--     
--     