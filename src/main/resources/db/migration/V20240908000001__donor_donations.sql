CREATE TABLE donors
(
    donor_id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    phone      VARCHAR(15)  NULL,
    address1   VARCHAR(255) NULL,
    address2   VARCHAR(255) NULL,
    city       VARCHAR(100) NULL,
    state      VARCHAR(50)  NULL,
    zip        VARCHAR(20)  NULL
);

CREATE TABLE donations
(
    donation_id     BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    donor_id        BIGINT UNSIGNED NOT NULL,
    currency_code   CHAR(3)         NOT NULL,
    currency_amount DECIMAL(10, 2)  NOT NULL,
    designation     VARCHAR(255),
    committed_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    received_at     TIMESTAMP       NULL,
    FOREIGN KEY (donor_id) REFERENCES donors (donor_id) ON DELETE CASCADE
);