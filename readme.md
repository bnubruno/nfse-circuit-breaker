
# Projeto NFS-e com Resilience4j

Neste projeto é usado como exemplo a rotina de emissões de NFS-e para entendermos o uso aplicado do Resilience4j como  circuit breaker.

## Dicas

Para visualizar as métricas no grafana utilize o comando abaixo:

    make start  

App `localhost:8080`

Grafana `localhost:3000`

Prometheus `localhost:9090`

## Endpoints

Teste de requisição `GET localhost:8080/issue/:cityName`

Deixar cidade indisponível `GET localhost:8080/:cityName/down`

Deixar cidade disponível `GET localhost:8080/:cityName/up`

