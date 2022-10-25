select setval('seq_persons_id', max(id)) from persons;
select setval('seq_movies_id', max(id)) from movies;
