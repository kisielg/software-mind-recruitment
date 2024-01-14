# software-mind-recrutation

Dodawanie danych:
```
curl --location 'localhost:8080/items' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Clean",
    "description": "Clean a garage",
    "done": false
}'
```

Wyświetlanie listy:
```
curl --location 'localhost:8080/items?sortBy=done'
```

Update danych:
```
curl --location --request PUT 'localhost:8080/items/1' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Updated name",
    "description": "updated description",
    "done": true
}'
```

Usuwanie danych:
```
curl --location --request DELETE 'localhost:8080/items/3'
```

Liczba requestów dostępna jest tu:
```
http://localhost:8080/actuator/metrics/http.server.requests
```

Taka trochę namiastka GUI ale brakło czasu:
http://localhost:8080/swagger-ui/index.html#

Sortowanie po query param 'sortBy'

Walidacja na nie pusty name

JUnit dodany

Baza H2 (defaultowa)

