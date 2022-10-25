# create a PostgreSQL container with a movie database
# tables movies, stars, play are created
# some init data is added
docker run --detach \
	--name pg-movie \
	--env POSTGRES_DB=dbmoviequery \
	--env POSTGRES_USER=movie \
	--env POSTGRES_PASSWORD=password \
	-v ${PWD}/sql:/docker-entrypoint-initdb.d \
	-p 5432:5432 \
	postgres:14.5

