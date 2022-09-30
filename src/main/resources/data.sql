INSERT INTO restaurant (id, zip_code, address, name) VALUES
(1L, '00000001', 'Address Restaurant 1', 'Restaurant 1'),
(2L, '00000002', 'Address Restaurant 2', 'Restaurant 2');

INSERT INTO client (id, zip_code, address, name) VALUES
(1L, '00000001', 'Address Client 1', 'Client 1'),
(2L, '00000002', 'Address Client 2', 'Client 2');

INSERT INTO product (id, available, name, unit_value, restaurant_id) VALUES
(1L, true, 'Product 1', 5.0, 1L),
(2L, true, 'Product 2', 6.0, 1L),
(3L, true, 'Product 3', 7.0, 2L),
(4L, true, 'Product 4', 8.0, 1L),
(5L, true, 'Product 5', 9.0, 2L);

INSERT INTO cart (id, payment_method, closed, grand_total, client_id) VALUES
(1L, 0, false, 0.0, 1L);
