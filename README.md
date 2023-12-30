# kekiir_masterwork
# at Greenfox Java Beckend Enterprize course

## _Next day power trading app_

## Scope of Use

In the electricity trading sector, not only the quantity of electricity but also the timing of its transmission is extremely important. Since storing electrical energy is highly costly, it is crucial for the generation and consumption of electricity to occur simultaneously, and the amount of electricity produced and consumed should be identical. This requires essential coordination between producers and consumers.

The primary electricity trading involves the next-day electricity trade, where energy producers and consumers prearrange the amount of electricity they will need the following day and how much they will have to pay for it. The determination of the quantity and price of electricity depends on market supply and demand conditions, and prices are set the day before. Agreements between energy consumers and producers are made on electronic marketplaces, such as EPEX SPOT. This helps maintain balance between electricity consumption and production, ensuring that the network is neither overloaded nor underloaded.

With PowerTrade, a database can be created where producers and consumers can list their capacities or energy needs based on time and performance. Through PowerTrade, they can query the performance trend for the next day and examine the available performance in a given hour or what supply they can expect.


## Application Structure:
![DB_Schema](https://user-images.githubusercontent.com/105811419/232657598-7ca687ed-97ff-44c9-9bb1-dcd4b7186f4e.png)

## Diagram illustrating the operation:

![image](https://user-images.githubusercontent.com/105811419/232667247-eb7766a5-c66b-48d1-89c4-cabe3f06f24b.png)


## Modells
- Suppliers ( /api/supplier/ )

    - Users can register with a username and password.
    - Login is required after registration to access further functionalities.
    - Create capacity
    - Modify capacity
    - Delete capacity
    - Query capacities for electricity demand at the same balance hour

- Consumers ( /api/consumers/ )

    - Users can register with a username and password.
    - Login is required after registration to access further functionalities.
    - Create energy demand
    - Modify energy demand
    - Delete energy demand

- Capacity (Capacity) stored data:

    - Energy source
    - Quantity of capacity in MW
    - Usable quantity of capacity in MW
    - Capacity price
    - PowerQuantities associated with capacity
    - Initial time of capacity
    - Closing time of capacity

- Energy demand (Demand) stored data:
    - Quantity of demand in MW
    - Usable quantity of capacity in MW
    - Electricity price
    - DemandQuantities associated with demand
    - Initial time of demand
    - Closing time of demand
    
    ## Funkcionalitas
- REST interface
- Basic CRUD operations on Capacities and Demands
- When posting a Capacity or Demand, it breaks down the capacity into balance-hour capacity units and assigns the capacity units to the respective balance hour.
- After posting Capacities and Demands, it determines the electricity price for each balance hour and identifies which Producers or Consumers are out of the market.

## API Documentation:
http://localhost:8080/swagger-ui/index.html

## Project setup
| Function | Implementation |
| ------ | ------ |
| Project settings | SpringBoot (Gradle) project, JAVA 8 |
| Reposiroty support | Spring Data JPA, hibernate |
| Database | MySQL, H2 |
| Migration tool | Flyway |
| Documantation | OpenAPI standard (Swagger) |
| Containerization | Dockerfile |
| Testing | Junit4, MockMVC |
|Other| Lombok annotations, Javax validation

## Installation Guide: 

1. For using MySQL database:

Use the appropriate settings in the .env and .env.docker files.
```

2. Build the Jar file:
```
./gradlew build
   ```
4. Build and run the Docker container:
Run the following command in the root directory of the application:

```
docker compose up
```

Ezután az api a 8080-as porton lesz elérhető.
    
