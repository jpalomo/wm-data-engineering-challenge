#WM Data Engineering Challenge

###Prerequisites
1. SQL Server
2. Eclipse 

###SQL Setup
1.  Create a database to house the data that will be transformed and loaded.
2.  Using the database that was created, create the _testresult_ table by running the query found in the _queries\testresulttbl.sql_ file.

###Update Configuration
1.  Update the _application.yml_ file with values to reflect your local system.

###Run
1.  Update the _run configurations program arguments_ in Eclipse to include the following:

```
server application.yml
```
2. Run the **com.palomo.wm.data.engineering.challenge.EtlService** class. 

###Notes
* Total records processed/transformed: 233,014,0496
* Transformation process took ~1.25-1.5 hrs
* Loading of the transformed records took ~0.5 hr

>In order to get the following results I first processed the 70GB file by parsing and chunking the file 
into smaller CSV files of 250,000 records.  Once all lines had been processed and each record written to its
respective transformed file, a load was initiated in to SQL Server.  The loader read each transformed file
and initiated a bulk load command for each CSV file


>The application is built with the dropwizard Java framework.  Dropwizard provides a lot of 
functionality out of the box to create REST microservices.  My intent with including Dropwizard 
was to eventually 'dockerize' the application and deploy it AWS ECS and provide some REST endpoints
to allow users to upload files, initiate etl process, etc (time did not permit).
