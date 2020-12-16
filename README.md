# inventory-manager
Demo Inventory Management Application

## BUILD & DEPLOY
The application can be built and run with the following commands in the base directory (tested on Maven 3.6.3) :
```
mvn clean package
java -jar target/inventory-manager-0.0.1-SNAPSHOT.jar
```
## API
The service runs by default on port 8080
### Inventory
#### List all records
```
POST api/inventory/record
```
#### Add a record
```
POST api/inventory/record
```
#### Updates the specified quantity(int) of the specified product (long)
```
PATCH api/inveotory/record
```
