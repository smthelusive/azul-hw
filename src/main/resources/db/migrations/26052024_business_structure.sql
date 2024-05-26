create table author(
    author_id bigint primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    bio varchar(1000)
);

create table genre(
    genre_id bigint primary key,
    name varchar(255) not null
);

create table book(
    book_id bigint primary key,
    title varchar(255) not null,
    price numeric(8, 2) not null,
    annotation varchar(1000),
    count int
);

create table book_author(
    book_id bigint,
    author_id bigint,
    primary key(book_id, author_id),
    foreign key(book_id) references book,
    foreign key(author_id) references author
);

create table book_genre(
    book_id bigint,
    genre_id bigint,
    primary key(book_id, genre_id),
    foreign key(book_id) references book,
    foreign key(genre_id) references genre
);

create sequence if not exists genre_seq minvalue 10;
create sequence if not exists author_seq minvalue 10;
create sequence if not exists book_seq minvalue 10;
