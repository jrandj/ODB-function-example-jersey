# ODB-function-example-jersey

This is an example application for calling an Oracle Database function exposed through a Jersey API. 
Access from http://localhost:8080/dbretrieval/api/checkpalindrome?word=VALUE

## Prerequisites
* Tomcat v9.0
* Oracle database 12C
* Java 7

In this example all of the above are running locally.

## Usage

1\. Create a config.properties file with the database connection parameters, e.g.

```
db.user=VALUE
db.URL=jdbc:oracle:thin:@HOST:PORT/INSTANCE_NAME
db.password=VALUE
```

2\. Add Function.sql to your Oracle Database.

3\. Change path to config.properties in App.

4\. Deploy to Tomcat using Eclipse.