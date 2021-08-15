create table member(
memberid varchar(50) primary key,
name varchar(50) not null,
password varchar(10) not null,
regdate datetime not null
)

select * from tab;

drop table member;

select * from member;

create table article(
	article_no int auto_increment primary key,
	writer_id varchar(50) not null,
	writer_name varchar(50) not null,
	title varchar(255) not null,
	regdate datetime not null,
	moddate datetime not null,
	read_cnt int
);

select * from article;

create table article_content(
	article_no int primary key,
	content text
);

select * from article_content;

select * from article order by article_no desc limit 0,1;

select now();
delete from Article where article_no=1;


select last_insert_id() from article





