
CREATE TABLE store_hours (
   id bigint not null auto_increment,
  store_ref bigint,
  day ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
  open_time time NOT NULL,
  close_time time NOT NULL,
  primary key (id)
);
