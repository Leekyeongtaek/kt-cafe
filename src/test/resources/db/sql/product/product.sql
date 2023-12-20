DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_option_group;
DROP TABLE IF EXISTS product_option;

CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `basic_price` int NOT NULL,
  `description` varchar(150) NOT NULL,
  `main_image` varchar(150) NOT NULL,
  `add_image1` varchar(150) DEFAULT NULL,
  `add_image2` varchar(150) DEFAULT NULL,
  `product_type` varchar(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE (`name`)
);

CREATE TABLE `product_option_group` (
  `product_option_group_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`product_option_group_id`),
  UNIQUE (`name`,`product_id`)
);

CREATE TABLE `product_option` (
  `product_option_id` int NOT NULL AUTO_INCREMENT,
  `product_option_group_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` int NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`product_option_id`)
);

INSERT INTO product (`product_id`, `name`, `basic_price`, `description`, `main_image`, `product_type`, `created_date`)
VALUES (1, '아메리카노(R)', 2000, '로스팅한 원두의 맛', 'www.mainImage.com', 'COFFEE', CURRENT_TIMESTAMP);

INSERT INTO product_option_group (`product_option_group_id`, `product_id`, `name`, `created_date`)
VALUES (1, 1, '온도 및 원두선택', CURRENT_TIMESTAMP);

INSERT INTO product_option (`product_option_id`, `product_option_group_id`, `name`, `price`, `created_date`)
VALUES (1, 1, '온도 : HOT / 원두 : 블랙그라운드', 0, CURRENT_TIMESTAMP);

INSERT INTO product (`product_id`, `name`, `basic_price`, `description`, `main_image`, `product_type`, `created_date`)
VALUES (2, '카페라떼(R)', 3000, '로스팅한 원두의 맛', 'www.mainImage.com', 'COFFEE', CURRENT_TIMESTAMP);

INSERT INTO product_option_group (`product_option_group_id`, `product_id`, `name`, `created_date`)
VALUES (2, 2, '온도 및 원두선택', CURRENT_TIMESTAMP);

INSERT INTO product_option (`product_option_id`, `product_option_group_id`, `name`, `price`, `created_date`)
VALUES (2, 2, '온도 : HOT / 원두 : 블랙그라운드', 0, CURRENT_TIMESTAMP);

INSERT INTO product_option (`product_option_id`, `product_option_group_id`, `name`, `price`, `created_date`)
VALUES (3, 2, '온도 : ICE / 원두 : 블랙그라운드', 0, CURRENT_TIMESTAMP);

INSERT INTO product (`product_id`, `name`, `basic_price`, `description`, `main_image`, `product_type`, `created_date`)
VALUES (3, '카페모카(R)', 3500, '로스팅한 원두의 맛', 'www.mainImage.com', 'COFFEE', CURRENT_TIMESTAMP);

INSERT INTO product (`product_id`, `name`, `basic_price`, `description`, `main_image`, `product_type`, `created_date`)
VALUES (4, '콜드브루(R)', 3700, '로스팅한 원두의 맛', 'www.mainImage.com', 'COFFEE', CURRENT_TIMESTAMP);