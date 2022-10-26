DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
('2022-04-01 09:00:00', 'Завтрак user', 500, 100000),
('2022-04-04 13:00:00', 'Обед user', 800, 100000),
('2022-04-04 19:00:00', 'Ужин user', 600, 100000),
('2022-04-05 01:00:00', 'Ночной перекус user', 1500, 100000),
('2022-04-06 08:00:00', 'Завтрак user', 400, 100000),
('2022-04-08 12:00:00', 'Обед admin', 500, 100001),
('2022-04-08 20:00:00', 'Ужин admin', 1500, 100001),
('2022-04-09 09:00:00', 'Завтрак admin', 400, 100001),
('2022-04-10 00:00:00', 'Пограничное время admin', 400, 100001);