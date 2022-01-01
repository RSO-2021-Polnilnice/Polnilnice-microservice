# RSO: Polnilnice microservice

## Prerequisites

```bash
docker run -d --name pg-polnilnice -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=polnilnice-data -p 5433:5432 postgres:13
```

Create Docker container.
```bash
docker run -p 8081:8080 --network rsonet -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://192.168.99.100:5433/polnilnice-data -e KUMULUZEE_CONFIG_CONSUL_AGENT=http://192.168.99.100:8500 --name polnilnice-instance polnilnice
```

Start database and polnilnice-ms containers
```bash
docker start pg-polnilnice
docker start polnilnice-instance
```