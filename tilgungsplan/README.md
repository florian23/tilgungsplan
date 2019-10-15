# Repayment Schedule

This project calculates a repayment schedule for a given loan amount, an interest, an initial repayment and a duration in years for a fixed rate amount.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

This project needs Java 1.8 and maven to compile and run. You might need a webbrowser to view the calculated repayment schedule.

### Compiling

Compile the sources

```
mvn clean compile
```

Execute the test cases located in the `src/test/java/de/example` directory.

```
mvn clean test
```

Build a jar

```
mvn clean package
```

This will create two jars in the `target` directory named `tilgungsplaner.jar` and `tilgungsplaner-jar-with-dependencies.jar`


## Running the application

The application needs the following cli options to run succesfully

 - amount: the total loan amount in euros
 - interest: the interest in percentage
 - repayment: the initial repayment in percentage
 - duration: the duration in years
 - filename: the filename where the repayment schedule is stored.
 
The following command creates a repayment schedule with the initial amount of 100.000 Euros, the interest of 2.12%, the repayment of 2.00% for the duration of 10 years. The result is written to the file `tilgungsplan.html` and can be viewed with a webbrowser.

```
java -jar target/tilgungsplaner-jar-with-dependencies.jar -amount 100000 -interest 2.12 -repayment 2.00 -duration 10 -filename tilgungsplan
```

