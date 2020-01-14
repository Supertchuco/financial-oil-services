# Oil Service Application
	This application is a REST API to take control of oil operations.

Functional requirements
 ```bash
    1. For all oil types listed in the Table 1:
        a) Given any price as the input, please calculate the Revenue yield,
        b) Given any price as the input, please calculate the Price-Earnings Ratio,
        c) Record a transaction with timestamp, quantity, buy or sell indicator and price,
        d) Calculate Volume Weighted Oil Price based on transaction in the past 30 minutes.
    2. Calculate the Inventory Index using the geometric mean of prices for all the types of oil.
 ```   
## Technologies(Frameworks and Plugins)
This project I have developed using Intellij IDE and these technologies and frameworks:

	-Java 8
    -Springboot,
    -Gradle,
    -H2 (memory database),
    -Swagger,
    -Lombok,
    -Actuator,
    -Docker,
	-PMD plugin,
	-Checkstyle plugin,
    -Spring rest.

## About project	
	This project is formed per one SpringBoot Application.
        Notes about application:
            -This application is configured to use H2 database, I mean, a memory database, you don´t need install any database in your pc to run this application;
            -It is configured to listen 8090 port;
            -The class DatabaseLoader.java is configured to insert initial oil types and oils, you can disable it, to do that, you need just to remove the @Component annotation;
            -In this Swagger url: http://localhost:8090/oil-service/swagger-ui.html you can check all documentation about rest endpoints;
            -It is configured to use Swagger, using it you can check the endpoints and payloads using an internet browser. To access Swagger interface use this url: http://localhost:8090/swagger-ui.html, just a detail, if you are using Docker in Windowns machine maybe you need to change the localhost to a -specific ip (docker machine ip)
			-Basic the API has these endpoints:
				Post - http://localhost:8090/oil-service/oil - Insert oil registry on database;
                            Get - http://localhost:8090/oil-service/oil- Retrieve all oil registries on database;
				Get - http://localhost:8090/oil-service/oil/{oilId} - Retrieve oil registry by id on database;
				
				Post - http://localhost:8090/oil-service/oilTransaction - delete an exam in database;
				Get - http://localhost:8090/oil-service/oilTransaction - Insert oil transaction registry on database;
				Get - http://localhost:8090/oil-service/oilTransaction/{transactionId} - Retrieve oil transaction by transaction id;

                            Get - http://localhost:8090/oil-service//statistics/geometricMean - Calculate the Inventory Index using the geometric mean of prices for all oil types;

			-There are unit tests for service layer,
			-There are integration tests for API that simulate the complete flows:
				These tests are configured to use H2 database.
			-This project is using PMD (https://maven.apache.org/plugins/maven-pmd-plugin/) and Checkstyle (https://maven.apache.org/plugins/maven-checkstyle-plugin/) plugins to keep a good quality in -the code.
			-During every build process, these process are executed:
				Execute unit tests for service layer
				Execute integration tests
				Execute Checkstyle verification
				Execute PMD verification	
				build jar file

## Run 
 Inside Intellij IDE:
 ```bash
    -Import the project;
    -Execute Gradle import;
    -Check Enable annotation processing field in Intellij options
    -Start the application running this class FinancialOilServicesApplication.
 ```

## Docker
 To use docker you need follow these steps:
 ```bash
	-Build the application,
	-Build docker image with this command: docker build -t oil-service . (you need to run this command in root project folder to create the docker image);
    -Execute the docker-compose file with this command: docker-compose up (you need to run this command in root project folder). You can -check if applications are running using the actuator feature, to do do that you need to access this url: http://localhost:8090/oil-service/health;
```

If you have questions, please feel free to contact me.