# Project Base with Vaadin and Spring Boot


## Running the Application

Import the project to the IDE of your choosing as a Maven project.

After importing the maven project, you need to change the "spring.datasource.url" in your application.properties.

You find the databasefile in the path myhimoapp/src/main/resources/data/demo, but you need add you personal project path. There is some sample data inside, the databasefiles are part of .gitignore, so please just check in, if the schema changes.

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
