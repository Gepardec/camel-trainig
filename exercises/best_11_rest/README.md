Provide REST Endpoint and implement integration tests
===================

#### Prerequisites
Maven, Java, completed exercise 10

#### Technical hints

**Artemis Queue**
 
 ```
 docker run -it --rm -p 8161:8161 -p 61616:61616 -p 5672:5672 -e ARTEMIS_USERNAME=quarkus -e ARTEMIS_PASSWORD=quarkus vromero/activemq-artemis:2.9.0-alpine
```

**Postgres**

```
docker run -it --rm -p 5432:5432 -e POSTGRES_DB=quarkus -e POSTGRES_USER=quarkus -e POSTGRES_PASSWORD=quarkus postgres
```

```
cd ./solutions/best
```

```
mvn flyway:migrate
```

**Run Camel**
```
mvn camel:run
```

**Run Integration Tests**
```
mvn -Dmaven.failsafe.skip=false failsafe:integration-test
```
#### Exercise
Add Support for Camel-Rest:
```
<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-rest</artifactId>
</dependency>
```

Use https://camel.apache.org/manual/latest/rest-dsl.html as documentation to accomplish following tasks

1. Use EggOrderRouteBuilderIT template to complete the test method `wrongInputJson_NothingInQueue()`. Implement EX1 in EntryRouteBuilder to fulfill the test.
2. Use EggOrderRouteBuilderIT template to complete the test method `correctInputJson_CorrectJavaObjectIsCreated()`. Implement EX2 in EntryRouteBuilder to fulfill the test.
3. Use PastOrderRouteBuilderIT template to complete the test method `wrongInputJson_NothingInDB()`.
4. Use PastOrderRouteBuilderIT template to complete the test method `wrongInputJson_NothingInDB()`.
