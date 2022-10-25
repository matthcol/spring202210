create sequence seq_persons_id start with 1;
create sequence seq_movies_id start with 1;

create table persons (
	id integer constraint pk_stars primary key,
	name varchar(150) not null,
	birthdate date null
);

create table movies (
	id integer constraint pk_movies primary key,
	title varchar(300) not null,
	year smallint not null,
	duration smallint null,
	synopsis text null,
	poster_uri varchar(300) null,
	color varchar(20) null,
	pg varchar(15) null,
	director_id int null,
	constraint uniq_movies UNIQUE(title, year),
	constraint chk_movies_year CHECK(year >= 1850)
);

create table play(
	movie_id int not null,
	actor_id int not null,
	role varchar(100),
	constraint pk_play primary key(movie_id, actor_id)
);

alter table movies add constraint fk_movies_director 
	FOREIGN KEY (director_id)
	references persons(id);
alter table play add constraint FK_PLAY_MOVIE 
	FOREIGN KEY (movie_id)
	references movies(id);
alter table play add constraint FK_PLAY_ACTOR 
	FOREIGN KEY (actor_id)
	references persons(id);


