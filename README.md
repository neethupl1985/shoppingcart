# Shopping Cart API

Project for implementing a Shopping Cart Library

## Implementation Details

This project uses Spring Boot Application to run the program on the port 8080.

## Endpoints

Below are the endpoint for this application which can be tested using `POSTMAN`


#### INVENTORY CONTROLLER

1) Test create items in Inventory table

        POST http://localhost:8080/inventory/createItems

2) Test get items in Inventory table
   
        GET http://localhost:8080/inventory/getAllItems

3) Test get single item in Inventory table

        GET http://localhost:8080/inventory/getItem/{itemId}


4) Test update the count of an item in Inventory table

        PUT http://localhost:8080/inventory/updateItemCount/{updateType}/{itemId}
   updateType = "A" adds item count  and updateType = "D" reduce item count
   

5) Test update the item price of an item in Inventory table

        PUT http://localhost:8080/inventory/updateItemPrice/{itemId}

6) Test delete an item from Inventory table

        DELETE http://localhost:8080/inventory/deleteItem/{itemId}


#### USER CONTROLLER


1) Test create user in user table

        POST http://localhost:8080/user/createUser

2) Test get all users from user table

        GET http://localhost:8080/user/getAllUsers

3) Test get single user from user table

        GET http://localhost:8080/user/getAUser/{userId}

4) Test update a  user from user table

        PUT http://localhost:8080/user/updateUser/{userId}


5) Test delete a  user from user table

        DELETE http://localhost:8080/user/deleteUser/{userId}

### UsersCart Controller


1) Test add an item to user's cart

        POST http://localhost:8080/cart/addToCart

2) Test read an item from user's cart

        GET http://localhost:8080/cart/getUserCart/{userId}

3) Test delete all items from user's cart

        DELETE http://localhost:8080/cart/deleteAllItemsFromCart/{userId}

4) Test update an item in user's cart, operation is A(for add) or D(for add)

        PUT http://localhost:8080/cart/updateItemInUserCart/{operation}

5) Test get the summary and final discount applied to a users cart

        GET http://localhost:8080/cart/summary/{userId}


## Testing the application locally

For testing the application local


## Table Structure
The table structure is defined in  ``src/main/resources/createShoppingCartTables.sql``. This is loaded into the spring
application when the application is launched.

## Testing

1) All the unit tests are in the ``src/test``
2) There is an integration test which is there in the ``src/integrationtest``. 
   The integration test uses embedded H2 database for storing the data . This is an in memory storage database which is 
   cleaned up when the application is stopped
   
## Future Implementation

1) Creating an Open API specification using Swagger doc.
2) Using a persistent database like MySql, Postgress, Oracle 
3) Hosting the application in AWS utilizing cloud services
4) Creating a table for Discount which will contain all discounts , current solution has all the discount in a java code.

