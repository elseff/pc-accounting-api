INSERT INTO speciality(code, title)
VALUES ('system-admin', 'Системный администратор'),
       ('accountant', 'Бухгалтер'),
       ('developer', 'Программист'),
       ('analyst', 'Аналитик'),
       ('tester', 'Тестировщик'),
       ('director', 'Директор');

INSERT INTO computer_type(code, title)
VALUES ('pc', 'Персональный компьютер'),
       ('mono', 'Моноблок'),
       ('mini', 'Мини ПК'),
       ('laptop', 'Ноутбук');

INSERT INTO employee_operation_type(code, title)
VALUES ('pinned', 'Компьютер закреплен за сотрудником'),
       ('unpinned', 'Компьютер откреплён от сотрудника');

INSERT INTO device_operation_type(code, title)
VALUES ('added', 'Устройство добавлено в компьютер'),
       ('removed', 'Устройство удалено из компьютера');

INSERT INTO device_group(code, title)
VALUES ('peripherals', 'Компьютерная периферия'),
       ('accessories', 'Компьютерные комплектующие');

INSERT INTO device_type(device_group_id, code, title)
VALUES (1, 'keyboard', 'Клавиатура'),
       (1, 'mouse', 'Мышь'),
       (1, 'printer', 'Принтер'),
       (1, 'display', 'Монитор'),
       (1, 'speaker', 'Колонки'),
       (1, 'headphones', 'Наушники'),
       (1, 'scanner', 'Сканер'),
       (1, 'camera', 'Камера'),
       (2, 'hard-drive', 'Жесткий диск'),
       (2, 'ram', 'Оперативная память'),
       (2, 'motherboard', 'Материнская плата'),
       (2, 'video-card', 'Видеокарта'),
       (2, 'cpu', 'Процессор'),
       (2, 'drive', 'Дисковод'),
       (2, 'audio-card', 'Звуковая карта'),
       (2, 'power-unit', 'Блок питания'),
       (2, 'case', 'Корпус'),
       (2, 'hdd', 'HDD'),
       (2, 'ssd', 'SSD'),
       (2, 'network-card', 'Сетевая карта'),
       (2, 'cooler', 'Кулер');

INSERT INTO computer (title, cabinet, number, ready, computer_type_id)
VALUES ('Персональный компьютер', null, '1faf33b5-0eb9-4f2c-a783-d1db9b5179e5', false, 1);
INSERT INTO computer (title, cabinet, number, ready, computer_type_id)
VALUES ('Ноутбук Asus', null, 'c0a59da1-eba0-41c1-b4b5-e514f3088506', false, 4);

INSERT INTO device (device_type_id, title, number, price, computer_id)
VALUES (4, 'Монитор PHILIPS 322E', '400d8e2e-e540-4e73-8a9c-491576223bff', 140, null);
INSERT INTO device (device_type_id, title, number, price, computer_id)
VALUES (15, 'Звуковая карта Realtek', '1121144f-d045-4d89-a058-f0d8fb0770e2', 90, null);
INSERT INTO device (device_type_id, title, number, price, computer_id)
VALUES (12, 'Видеокарта Geforce RTX 4090', 'a1e582e2-37e4-4698-91c5-c974542d0a4e', 490, 1);
INSERT INTO device (device_type_id, title, number, price, computer_id)
VALUES (15, 'Звуковая карта Realtek', '0ec035eb-e017-458f-8106-78516c6ad6c9', 90, 1);

INSERT INTO computer_device_log (computer_id, device_id, device_operation_type_id, date, message)
VALUES (1, 1, 1, CURRENT_TIMESTAMP, 'Устройство успешно добавлено в компьютер');
INSERT INTO computer_device_log (computer_id, device_id, device_operation_type_id, date, message)
VALUES (1, 2, 1, CURRENT_TIMESTAMP, 'Устройство успешно добавлено в компьютер');
INSERT INTO computer_device_log (computer_id, device_id, device_operation_type_id, date, message)
VALUES (1, 1, 2, CURRENT_TIMESTAMP, 'Устройство удалено из компьютера');
INSERT INTO computer_device_log (computer_id, device_id, device_operation_type_id, date, message)
VALUES (1, 1, 1, CURRENT_TIMESTAMP, 'Устройство добавлено в компьютер');