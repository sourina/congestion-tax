CREATE TABLE city (
    id INT PRIMARY KEY,
    code VARCHAR(255),
    name VARCHAR(255)
);

INSERT INTO city (id, code, name)
VALUES (1, 'GOB', 'Gothenburg'),
       (2, 'STH', 'Stockholm');

CREATE TABLE taxrate (
    id INT PRIMARY KEY,
    city_id INT,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL ,
    amount INT
);

INSERT INTO taxrate (id, city_id, start_time, end_time, amount)
VALUES (1,1, TIME '06:00', TIME '06:29', 8),
       (2,1, TIME '06:30', TIME '06:59', 13),
       (3,1, TIME '07:00', TIME '07:59', 18),
       (4,1, TIME '08:00', TIME '08:29', 13),
       (5,1, TIME '08:30', TIME '14:59', 8),
       (6,1, TIME '15:00', TIME '15:29', 13),
       (7,1, TIME '15:30', TIME '16:59', 18),
       (8,1, TIME '17:00', TIME '17:59', 13),
       (9,1, TIME '18:00', TIME '18:29', 8),
       (10,1, TIME '18:30', TIME '05:59', 0);