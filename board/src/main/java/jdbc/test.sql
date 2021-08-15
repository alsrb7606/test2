//article_no 글번호 증가
create table article(
	article_no int primary key,
	writer_id varchar(50) not null,
	writer_name varchar(50) not null,
	title varchar(255) not null,
	regdate timestamp not null,
	moddate timestamp not null,
	read_cnt int
);

select * from article;

create table article_content(
	article_no int primary key,
	content long
);

select * from article_content;

grant create sequence to user01;

create sequence num_seq increment by 1 start with 1;

//insert into article values(num_seq.nextval, ?,?,?,?,?,0)"

//select last_insert_id() from article 

select max(article_dao) from article



select * from article order by article_no desc OFFSET 1 ROWS FETCH NEXT 3 ROWS ONLY;



