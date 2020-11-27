Creating Marshalling Route

Prerequisites: Exercise 09_choice

Overview: <br>
- In this Section you will learn how to create a camel route, which should only retrieve a OrderToProducer.class (filter?) Item and marshal it to a xml File with jaxb.<br>
- This xml should be sent into a jms queue.

Description: <br>
- Create a Route which is waiting for Input from the seda egg Endpoint URI ("seda:egg_order_entry"). <br>
- In the incoming exchange you should have a OrderToProducer.class Item, to be sure filter it with the filter method (body.isInstanceOf(OrderToProducer.class)) <br>
- marshal the body with .marshal(JAXBFORMAT) <br>
- send this body into a jms queue TODO describe how to create and send to a jms queue?

Unit Test: <br>
- create a OrderToProducer.class Item <br>
- send this Item to the seda egg Endpoint (extends CamelRouteCDITest) <br>
- mock the jms queue from the eggcamelroute <br>
- get the exchange from the mock queue and compare it with the created OrderToProducer.class Item
TODO describe how to mock a jms queue?
