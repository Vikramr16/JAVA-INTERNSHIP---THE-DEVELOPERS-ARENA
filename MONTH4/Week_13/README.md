Spring Boot Multi-Environment Full Example
Run dev profile:
mvn spring-boot:run -Dspring-boot.run.profiles=dev
Run prod profile:
mvn spring-boot:run -Dspring-boot.run.profiles=prod
Endpoints:
GET / returns profile info
CRUD /api/employees
