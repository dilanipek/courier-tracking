# Courier Tracking project


## Prerequisites

- [`Java 11`](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)

## Start Environment

- Open a terminal and inside `courier-tracking` root folder run

  mvn clean install
  ```
   docker-compose  up  --build -d  
  ```

- Wait a little bit until `postgres` and `redis` container is Up (healthy). You can check their status running
  ```
  docker-compose ps
  ```

## Running file-app using Maven & Npm

- **courier-tracking**

    - Open a terminal and navigate to `/courier-tracking` folder

    - Run the following `Maven` command to start the application
      ```
      mvn clean package 
      ```

## Applications URLs

| Application | URL                                   | Credentials                                         |
|-------------| ------------------------------------- | --------------------------------------------------- |
| courier-tracking  | http://localhost:8080 |        `courieruser/courierpassword`                                             |

This table shows the credentials provided by docker-compose file. No need for configuration docker compose up do it all
> **Note:** the credentials shown in the table are the ones already pre-defined.


## Shutdown


- To stop and remove docker-compose containers, networks and volumes, run the command below in `courier-tracking` root
  folder
  ```
  docker-compose down -v
  ```

## Testing API

## Step 1: Loading Stores into the Database

Before testing the API, load the stores into the database from the stores.json file.


```bash
curl --location --request POST 'localhost:8080/api/couriers/load-store' \
--data-raw ''
  ```
Expected Response : Stores saved successfully.

## Step 2: Checking if Courier is Near Any Migros Store

```bash
curl --location --request POST 'localhost:8080/api/couriers/location' \
--header 'Content-Type: application/json' \
--data-raw '{
"id" : 1,
"timestamp" :"2024-02-24T17:11:00",
"latitude" : "40.991115",
"longitude": "29.115744"
}'
  ```
This endpoint returns true if the requested courier is near (enters a radius of 100 meters) any Migros store, and false if not. 
It holds data of the store and whether the courier is near a Migros store (enters a radius of 100 meters).
Reentries of the same courier are not held.

## Step 3: Getting Total Travel Distance of a Courier


To see the total travel distance of a courier, use the following endpoint.


```bash
curl --location --request GET 'localhost:8080/api/couriers/1/total-travel-distance' \
--data-raw ''

  ```
 ## Step 4:To Check Db Tables By Terminal(Optional)
In the terminal, you can use the following PostgreSQL commands:


```bash 
docker exec -it <container_name> psql -U courieruser -d courierdb
  ```

To list all tables in the current database:
```bash 
\dt
  ```

## List of Tables

| Schema | Name              | Type  | Owner       |
|--------|-------------------|-------|-------------|
| public | courier_entry_log | table | courieruser |
| public | courier_location  | table | courieruser |
| public | stores            | table | courieruser |


To list all records of a table

```bash 
SELECT * FROM your_table_name;
  ```





