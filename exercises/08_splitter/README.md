Creating Splitter Route
================================

Prerequisites: Exercise 07

Add a route which splits a List of OrderItems.

Make sure that the route splits only on demand and not upfront.

Log out the name of the OrderItem and the CamelSplitIndex on every submessage. 

Write an IT which checks: 
```
assertThat(result.getReceivedCounter() == 3);
```