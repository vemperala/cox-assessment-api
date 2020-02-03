
CREATE TABLE store (
   id bigint not null auto_increment,
  type varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  address2 varchar(255),
  city varchar(100) NOT NULL,
  state varchar(2) NOT NULL,
  zip varchar(10) NOT NULL,
   primary key (id)
);
