# cox-assessment-api
store-api for the cox assessment test


Expose CRUD API for stores


----------------------------
## To run locally via docker note: docker should installed on your machine else follow other procedure steps
1.  `docker-compose up -d`
2.  Start the application:  gradle bootrun

your application should be up and running on port 8081


-----------------------
## other procedure to run locally

1) create schema with name 'cox' in your local mysql.
2) update the application.yml file with your db_schema_name, password, port.

3) start the application: 'gradle bootrun'


once the application is up and running in your local

to load the test dataset given for the test 
https://github.com/BestBuyAPIs/open-data-set/blob/master/stores.json

please make a POST request to the below endpoint and pass the json as the body. the content type should be JSON.
via POSTMAN

http://localhost:8081/store-api/load/data



