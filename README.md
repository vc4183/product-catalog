# product-catalog
Microservice for getting product data

## Prerequisites
- check api > src > main > resources > config.yaml for configuration for PostGres database in Docker
- and models > src > main > resources > META-INF > persistence.xml
```bash
docker run -d --name pg-product -e POSTGRES_USER=dbuser_Diagnoses7782 -e POSTGRES_PASSWORD=T3Bo32fu7yW#Gj^%r!%^ -e POSTGRES_DB=rso_product -p 5432:5432 postgres:13
```
