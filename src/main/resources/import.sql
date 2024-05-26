-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
--create table author(
--    author_id bigint primary key,
--    first_name varchar(200) not null,
--    last_name varchar(200) not null,
--    bio varchar(1000)
--);

--create table genre(
--    genre_id bigint primary key,
--    name varchar(255) not null
--);

--create table book (
--    book_id bigint primary key,
--    title varchar(255) not null,
--    price numeric(8, 2) not null,
--    count int
--);
--
--create table book_author(
--    book_id bigint,
--    author_id bigint,
--    primary key(book_id, author_id),
--    foreign key(book) references book,
--    foreign key(author) references author
--);
--
--create table book_genre(
--    book_id bigint,
--    genre_id bigint,
--    primary key(book_id, genre_id),
--    foreign key(book) references book,
--    foreign key(genre) references genre
--);
--todo index by title?

insert into genre(name) values ('horror');
insert into genre(name) values ('romance');
insert into genre(name) values ('sci-fi');
insert into genre(name) values ('non-fiction');

insert into author(first_name, last_name, bio) values ('Stephen', 'King', 'Super horror author');
insert into author(first_name, last_name, bio)  values ('Jules', 'Verne', 'One of the best');
insert into author(first_name, last_name, bio)  values ('Anna', 'Noname', 'She wrote something');
insert into author(first_name, last_name, bio)  values ('Steven', 'Bartlett', 'Some CEO');

insert into book(title, price, annotation, count) values('Shining', 12.50, 'Scary shit', 21);
insert into book(title, price, annotation, count) values('The Diary of a CEO', 42.00, 'Smarty thoughts', 5);
insert into book(title, price, annotation, count)  values('Dummy', 15, 'All the genres here', 1);

insert into book_author(book_id, author_id) values(1, 1);
insert into book_author(book_id, author_id) values(2, 4);
insert into book_author(book_id, author_id) values(3, 2);
insert into book_author(book_id, author_id) values(3, 3);

insert into book_genre(book_id, genre_id) values(1, 1);
insert into book_genre(book_id, genre_id) values(2, 4);
insert into book_genre(book_id, genre_id) values(3, 3);
insert into book_genre(book_id, genre_id) values(3, 2);
insert into book_genre(book_id, genre_id) values(3, 1);