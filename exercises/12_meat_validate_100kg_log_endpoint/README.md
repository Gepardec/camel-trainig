Create Validating Route

Prerequisites: Exercise 09_choice

Overview: <br>
- In this Section you will learn how to create a camel route, which is validating a specific field of the incomming class.<br>
- And transform this one item in a json File.

Description: <br>
- Create a Route which is waiting for Input from the seda meat Endpoint URI ("seda://meat_order_entry"). <br>
- In the incoming Exchange you should have a OrderToProducer.class Item. <br>
- Validate the Field AMOUNT, if it's greater than 100, continue the route, otherwise a Exception will be thrown. (.validate(get the amount from the exchange with lambda) <br>
- After the successfull validation, transform the OrderToProducer.class Item into a JSON File and store it anywhere in the target Folder. (Split the routes, create a transformer, create the file route, which gets a json as input)

Unit Test: <br>
- Create a OrderItem and a OrderToProducer and send it to the seda meat Endpoint. <br>
- Compare the content of the created json file with the OrderItem from the Test.
