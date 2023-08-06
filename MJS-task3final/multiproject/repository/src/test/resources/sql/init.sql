CREATE TABLE gift_certificate (
  certificate_id BIGINT  NOT NULL  IDENTITY,
  certificate_name VARCHAR(45)  NOT NULL,
  description VARCHAR(250)  NOT NULL,
  price DECIMAL(7,2)  NOT NULL,
  create_date TIMESTAMP   NULL,
  last_update_date TIMESTAMP  NULL,
  duration BIGINT  NOT NULL,
  PRIMARY KEY (certificate_id)
);

CREATE TABLE  tag (
    tag_id BIGINT NOT NULL  IDENTITY,
    tag_name VARCHAR(45) NOT NULL,
    operation VARCHAR(45) NULL,
    timestamp BIGINT NULL,
    PRIMARY KEY (tag_id)
  );

CREATE TABLE  certificate_tag (
tag_id BIGINT NOT NULL,
certificate_id BIGINT NOT NULL,
PRIMARY KEY (tag_id,certificate_id),
UNIQUE KEY  (tag_id,certificate_id)
);

CREATE TABLE user_account (
  user_account_id bigint NOT NULL IDENTITY,
  user_name varchar(45) NULL,
  user_password varchar(250) NULL,
  user_email varchar(45)  NULL,
  is_active varchar(45)  DEFAULT 'ACTIVE',
  PRIMARY KEY (user_account_id)
) ;

CREATE TABLE orders (
  order_id bigint NOT NULL IDENTITY,
  order_date timestamp  NULL,
  user_account_id bigint NOT NULL,
  cost DECIMAL(7,2)  NOT NULL,
  PRIMARY KEY (order_id)
);

 CREATE TABLE order_details (
  order_details_id  bigint NOT NULL IDENTITY,
  quantity int NOT NULL,
  order_id bigint NOT NULL,
  certificate_id bigint NOT NULL,
  PRIMARY KEY (order_details_id)
);
