
CREATE TABLE location (
   id bigint not null auto_increment,
  lattitude decimal(10,8) NOT NULL,
  longitude decimal(11,8) NOT NULL,
  store_ref bigint,
  primary key (id)
);


