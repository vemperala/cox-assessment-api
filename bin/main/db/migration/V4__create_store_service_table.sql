CREATE TABLE store_services (
  id bigint not null auto_increment,
  store_ref bigint,
  service_ref bigint,
  primary key (id)
);
