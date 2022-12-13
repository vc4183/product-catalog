# product-catalog
Microservice for getting product data

## Prerequisites
- check api > src > main > resources > config.yaml for configuration for PostGres database in Docker
- and models > src > main > resources > META-INF > persistence.xml
- koda za inicializacijo baze v docker containerju
```bash
docker run -d --name pg-product -e POSTGRES_USER=dbuser_Diagnoses7782 -e POSTGRES_PASSWORD=T3Bo32fu7yW#Gj^%r!%^ -e POSTGRES_DB=rso_product -p 5432:5432 postgres:13
```


## Buildanje aplikacije v konzoli IntelliJ
```bash
mvn clean package
```

```bash
java -jar .\api\target\product-catalog-api-1.0-SNAPSHOT.jar
```


## Buildanje z Docker in dodatni Docker ukazi
```bash
docker build -t vcesar0edu/rso_repo:product_catalog .

//potrebno še konfiguracijo poštimat
docker run vcesar0edu/rso_repo:product_catalog
```

### Dodatni Docker ukazi
Prikaz slik docker
```bash
docker images
```
Nalaganje slike docker na DockerHub
```bash
docker push vcesar0edu/rso_repo:product_catalog
```
Brisanje slike docker 
```bash
docker image rm vcesar0edu/rso_repo:product_catalog  
```


## Elephant SQL
- url katerege zgenerira ElephantSQL ni cisto pravilen -> potrebno popravit
```
jdbc:postgresql://<server>:5432/<User & Default database>
jdbc:postgresql://lucky.db.elephantsql.com:5432/plqeoytw
```

## Azure K8S
1. namesti AzureCLI
```bash
winget install -e --id Microsoft.AzureCLI
```
2. prijavi se v azure (preusmeri na brksalnik)
```bash
az login
```

3. Poženeš komande katere so v "connect" zavihku na Azure - K8S cluster


## Kubectl 
create secret
```bash
kubectl create secret generic elephant-pg-pwd --from-literal=pg-pass=tbGnBKmpYh2bhYPz8E4i4-etKgm8w68V
```
podatki o secretu
```bash
kubectl get secret elephant-pg-pwd -o jsonpath='{.data}'
```
