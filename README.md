# Project Base with Vaadin and Spring Boot

Daily association training for improving associative thinking, your memory and your vocabulary.

Write down your associations for random words, click through your associations, compare your associations with other users.

## Running the Application

Import the project to the IDE of your choosing as a Maven project.


Run the application using `mvn spring-boot:run` or by running the `Application` class directly from your IDE.

Open http://localhost:8080/ in your browser.

If you want to run the application locally in the production mode, run `mvn spring-boot:run -Pproduction`.

To run Integration Tests, execute `mvn verify -Pintegration-tests`.

## More Information

- [Vaadin Flow](https://vaadin.com/flow) documentation
- [Using Vaadin and Spring](https://vaadin.com/docs/v14/flow/spring/tutorial-spring-basic.html) article

## Database

The database is a local H2 database. Open the H2 console while the server is running on 
localhost:8080/h2-console

If your put a "data.sql" in src/main/resources, it will be executed every time you start the server