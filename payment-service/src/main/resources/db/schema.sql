CREATE DATABASE IF NOT EXISTS gkpay_payment;
USE gkpay_payment;

CREATE TABLE IF NOT EXISTS payments (
    payment_id VARCHAR(32) PRIMARY KEY,
    merchant_order_id VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    currency VARCHAR(10) NOT NULL,
    payment_method VARCHAR(10) NOT NULL,
    payment_token VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    UNIQUE KEY uk_payment_id (payment_id)
);
