# software-mind-recrutation
Żeby zainstalować i uruchomić potrzebny maven i docker compose.
Tworzenie image:

```mvn spring-boot:build-image```

Postawienie aplikacji + komponentów (z katalogu gdzie jest docker-compose.yaml):
```
docker-compose up
```


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

Wyświetalnie poszczególnego item:
```
curl --location 'localhost:8080/items/2'
```

Wyszukiwanie:
```
curl --location 'localhost:8080/items/search?searchTerm=le'
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

Statystyki requestów na grafana (admin/admin) 
data source : prometheus
metric: http_server_requests_seconds_count
http://localhost:3000
lub bezpośrednio na prometheus:
http://localhost:9090


Taka trochę namiastka GUI na:
http://localhost:8080/swagger-ui/index.html#

Sortowanie po query param 'sortBy'

Walidacja na nie pusty name i na wulgaryzm w description.

Baza H2 (defaultowa) i postgres w profilu prod

