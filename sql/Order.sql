CREATE TABLE orders
(
    id           bigserial PRIMARY KEY,
    order_date   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount decimal   not null

);