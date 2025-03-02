# empik-recruitment-task

## Add new complaint

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

## Edit complaint content

```
curl -X PUT http://localhost:8080/api/v1/complaints/ABC123/jan.kowalski@example.com \
  -H "Content-Type: application/json" \
  -H "X-Forwarded-For: 108.138.7.23, 203.0.113.5, 93.184.216.34" \
  -d '{
    "content": "Mam ZUPEŁNIE NOWY problem z produktem ABC123"
  }'
```

## Get complaint

```
curl -X GET http://localhost:8080/api/v1/complaints/ABC123/jan.kowalski@example.com \
  -H "Content-Type: application/json" \
  -H "X-Forwarded-For: 108.138.7.23, 203.0.113.5, 93.184.216.34"
```

## Get all available complaints (example for page=0, size=10, sort by: productId, descending)

```
curl -X GET "http://localhost:8080/api/v1/complaints?page=0&size=10&sort=productId,desc" \
  -H "Content-Type: application/json"
```