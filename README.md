# Task Manager

Task Manager is a Java Spring web app that allows logged in users to display, create, update, and delete tasks.

## Installation

Use [docker](https://docs.docker.com/get-docker/) for database.

```bash
docker-compose --file docker/compose.yaml up --build -d
```
Run in IDE by importing task-manager-api and task-manager-webapp 
or by using Maven
```bash
cd task-manager-api/
mvn spring-boot:run
```
```bash
cd task-manager-webapp/
mvn spring-boot:run
```

## Usage
API documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Going to [http://localhost:8081/tasks](http://localhost:8081/tasks) should redirect to login page if not authenticated and once authenticated, returns back to tasks page.

## Stopping

```bash
docker-compose --file docker/compose.yaml down
```

## License
[MIT](https://choosealicense.com/licenses/mit/)
