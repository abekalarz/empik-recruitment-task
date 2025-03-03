# empik-recruitment-task

## How to run project?
* All what is needed to run project is to open project directory and run in terminal below command:
```
./gradlew bootRun
```

## How to run tests?
* All what is needed to run project tests is to open project directory and run in terminal below command:
```
./gradlew test
```

## Key points
1. Project is written and composed with usage of tech stack: Kotlin, Spring Boot, H2 database, Gradlew KTS (Kotlin).
2. Project gives possibility to report complaints related to products.
3. Exemplary API actions are given below with cURL examples.
4. No physical database was used (H2 database), which is in-memory database, but with
    with full standard SQL actions available. It is lightweight and very fast solution. 
    Especially it is good option for POC or small projects.
5. All database migrations are prepared with help of Flyway framework.
6. For purposes of client's IP address decoding used 'X-Forwarded-For' header and as fallback used
   REMOTE_ADDR.
7. For translation of the client's IP address to country name used API from 'REMOTE_ADDR' https://ipapi.co/

## API usage with cURL examples

### Add new complaint

```
curl -X POST http://localhost:8080/api/v1/complaints \
  -H "Content-Type: application/json" \
  -H "X-Forwarded-For: 108.138.7.23, 203.0.113.5, 93.184.216.34" \
  -d '{
    "productId": "ABC123",
    "content": "Mam problem z produktem ABC123",
    "reporter": "jan.kowalski@example.com"
  }'
```

### Edit complaint content

```
curl -X PUT http://localhost:8080/api/v1/complaints/ABC123/jan.kowalski@example.com \
  -H "Content-Type: application/json" \
  -H "X-Forwarded-For: 108.138.7.23, 203.0.113.5, 93.184.216.34" \
  -d '{
    "content": "Mam ZUPEŁNIE NOWY problem z produktem ABC123"
  }'
```

### Get complaint

```
curl -X GET http://localhost:8080/api/v1/complaints/ABC123/jan.kowalski@example.com \
  -H "Content-Type: application/json" \
  -H "X-Forwarded-For: 108.138.7.23, 203.0.113.5, 93.184.216.34"
```

### Get all available complaints (example for page=0, size=10, sort by: productId, descending)

```
curl -X GET "http://localhost:8080/api/v1/complaints?page=0&size=10&sort=productId,desc" \
  -H "Content-Type: application/json"
```