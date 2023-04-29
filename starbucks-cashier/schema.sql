create database midterm;
create user 'shohin'@'%' identified by 'student';
grant all on midterm.* to 'shohin'@'%';

USE midterm;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    drink VARCHAR(255) NOT NULL,
    milk VARCHAR(255) NOT NULL,
    size VARCHAR(255) NOT NULL,
    total VARCHAR(255) NOT NULL,
    register VARCHAR(255),
    status VARCHAR(255) NOT NULL
);

INSERT INTO orders (drink, milk, size, total, register, status)
VALUES ('Caffe Americano', 'Soy Milk', 'Venti', '$3.16', '1', 'Ready for Payment');

truncate orders;
