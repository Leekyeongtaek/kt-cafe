DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_product;
DROP TABLE IF EXISTS order_option_group;
DROP TABLE IF EXISTS order_option;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS payment;

CREATE TABLE `member` (
    `member_id` int NOT NULL AUTO_INCREMENT,
    `user_name` varchar(45) NOT NULL,
    PRIMARY KEY (`member_id`)
);

CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `payment_id` int DEFAULT NULL,
  `order_time` datetime NOT NULL,
  `order_status` varchar(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
);

CREATE TABLE `order_product` (
  `order_product_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` int NOT NULL,
  `count` int NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_product_id`)
);

CREATE TABLE `order_option_group` (
  `order_option_group_id` int NOT NULL AUTO_INCREMENT,
  `order_product_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_option_group_id`)
);

CREATE TABLE `order_option` (
  `order_option_group_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` int NOT NULL
);

CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `order_id` int NOT NULL,
  `rating` int NOT NULL,
  `contents` varchar(150) NOT NULL,
  `photo_image` varchar(150) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  UNIQUE (`order_id`,`review_id`)
);

CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `total_amount` int NOT NULL,
  `discount_amount` int NOT NULL,
  `payment_amount` int NOT NULL,
  `payment_method` varchar(30) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
);

INSERT INTO `member` (`member_id`,`user_name`)
VALUES (1, 'Mr.Lee');

INSERT INTO `member` (`member_id`, `user_name`)
VALUES (2, '방울이');

INSERT INTO `orders` (`order_id`, `member_id`, `payment_id`, `order_time`, `order_status`, `created_date`)
VALUES (1, 1, 1, CURRENT_TIMESTAMP, 'ORDERED', CURRENT_TIMESTAMP);

INSERT INTO `order_product` (`order_product_id`, `order_id`, `product_id`, `name`, `price`, `count`, `created_date`)
VALUES (1, 1, 1, '아메리카노(R)', 2000, 1, CURRENT_TIMESTAMP);

INSERT INTO `order_option_group` (`order_option_group_id`, `order_product_id`, `name`, `created_date`)
VALUES (1, 1, '온도 및 원두 선택', CURRENT_TIMESTAMP);

INSERT INTO `order_option` (`order_option_group_id`, `name`, `price`)
VALUES (1, '온도 : HOT / 원두 : 블랙그라운드', 0);

INSERT INTO `order_product` (`order_product_id`, `order_id`, `product_id`, `name`, `price`, `count`, `created_date`)
VALUES (2, 1, 2, '아메리카노(L)', 2500, 1, CURRENT_TIMESTAMP);

INSERT INTO `order_option_group` (`order_option_group_id`, `order_product_id`, `name`, `created_date`)
VALUES (2, 2, '온도 및 원두 선택', CURRENT_TIMESTAMP);

INSERT INTO `order_option` (`order_option_group_id`, `name`, `price`)
VALUES (2, '온도 : ICE / 원두 : 블랙그라운드', 0);

INSERT INTO `orders` (`order_id`, `member_id`, `payment_id`, `order_time`, `order_status`, `created_date`)
VALUES (2, 1, 2, CURRENT_TIMESTAMP, 'ORDERED', CURRENT_TIMESTAMP);

INSERT INTO `order_product` (`order_product_id`, `order_id`, `product_id`, `name`, `price`, `count`, `created_date`)
VALUES (3, 2, 1, '아메리카노(R)', 2000, 1, CURRENT_TIMESTAMP);

INSERT INTO `order_option_group` (`order_option_group_id`, `order_product_id`, `name`, `created_date`)
VALUES (3, 1, '온도 및 원두 선택', CURRENT_TIMESTAMP);

INSERT INTO `order_option` (`order_option_group_id`, `name`, `price`)
VALUES (3, '온도 : HOT / 원두 : 블랙그라운드', 0);

INSERT INTO `order_product` (`order_product_id`, `order_id`, `product_id`, `name`, `price`, `count`, `created_date`)
VALUES (4, 2, 2, '아메리카노(L)', 2500, 1, CURRENT_TIMESTAMP);

INSERT INTO `order_option_group` (`order_option_group_id`, `order_product_id`, `name`, `created_date`)
VALUES (4, 2, '온도 및 원두 선택', CURRENT_TIMESTAMP);

INSERT INTO `order_option` (`order_option_group_id`, `name`, `price`)
VALUES (4, '온도 : ICE / 원두 : 블랙그라운드', 0);

INSERT INTO `order_product` (`order_product_id`, `order_id`, `product_id`, `name`, `price`, `count`, `created_date`)
VALUES (5, 2, 2, '카페라떼(R)', 3000, 1, CURRENT_TIMESTAMP);

INSERT INTO `order_option_group` (`order_option_group_id`, `order_product_id`, `name`, `created_date`)
VALUES (5, 2, '추가선택', CURRENT_TIMESTAMP);

INSERT INTO `order_option` (`order_option_group_id`, `name`, `price`)
VALUES (5, '샷추가', 500);

INSERT INTO `payment` (`payment_id`, `total_amount`, `discount_amount`, `payment_amount`, `payment_method`, `created_date`)
VALUES (1, 4500, 0, 4500, 'CARD', CURRENT_TIMESTAMP);

INSERT INTO `payment` (`payment_id`, `total_amount`, `discount_amount`, `payment_amount`, `payment_method`, `created_date`)
VALUES (2, 7500, 0, 7500, 'CARD', CURRENT_TIMESTAMP);

INSERT INTO `review` (`review_id`, `member_id`, `order_id`, `rating`, `contents`, `photo_image`, `created_date`)
VALUES (1, 1, 1, 3, '맛있어요!!', 'www.photo.com', CURRENT_TIMESTAMP);

INSERT INTO `review` (`review_id`, `member_id`, `order_id`, `rating`, `contents`, `photo_image`, `created_date`)
VALUES (2, 1, 2, 4, '재주문입니다!!', 'www.photo.com', CURRENT_TIMESTAMP);
