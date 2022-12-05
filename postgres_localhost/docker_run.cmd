docker run --name java-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=advertisement_db -d -p 5432:5432 -v c:/PostgreSqlData/largedb:/mnt/largedb postgres

docker ps

PAUSE