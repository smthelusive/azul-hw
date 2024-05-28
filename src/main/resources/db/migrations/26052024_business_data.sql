insert into genre(genre_id, name) values (1, 'horror');
insert into genre(genre_id, name) values (2, 'romance');
insert into genre(genre_id, name) values (3, 'sci-fi');
insert into genre(genre_id, name) values (4, 'non-fiction');
insert into genre(genre_id, name) values (5, 'detective');

insert into author(author_id, first_name, last_name, bio) values (1, 'Stephen', 'King', 'Super horror author');
insert into author(author_id, first_name, last_name, bio) values (2, 'Jules', 'Verne', 'One of the best');
insert into author(author_id, first_name, last_name, bio) values (3, 'Anna', 'Noname', 'She wrote something');
insert into author(author_id, first_name, last_name, bio) values (4, 'Steven', 'Bartlett', 'Some CEO');
insert into author(author_id, first_name, last_name, bio) values (5, 'Arthur', 'Konan-Doyle', 'Classic detective author');

insert into book(book_id, title, price, annotation, count) values(1, 'Shining', 12.50, 'Scary stuff', 21);
insert into book(book_id, title, price, annotation, count) values(2, 'The Diary of a CEO', 42.00, 'Smarty thoughts', 5);
insert into book(book_id, title, price, annotation, count) values(3, 'Dummy', 15, 'Multiple genres here', 1);
insert into book(book_id, title, price, annotation, count) values(4, 'Sherlock Holmes', 15, 'Detective stories', 9);

insert into book_author(book_id, author_id) values(1, 1);
insert into book_author(book_id, author_id) values(2, 4);
insert into book_author(book_id, author_id) values(3, 2);
insert into book_author(book_id, author_id) values(3, 3);
insert into book_author(book_id, author_id) values(4, 5);

insert into book_genre(book_id, genre_id) values(1, 1);
insert into book_genre(book_id, genre_id) values(2, 4);
insert into book_genre(book_id, genre_id) values(3, 3);
insert into book_genre(book_id, genre_id) values(3, 2);
insert into book_genre(book_id, genre_id) values(3, 1);
insert into book_genre(book_id, genre_id) values(4, 5);

alter sequence genre_seq restart with 10;
alter sequence author_seq restart with 10;
alter sequence book_seq restart with 10;