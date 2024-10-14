CREATE TABLE donors
(
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` CHAR(16)     NOT NULL DEFAULT 'Generous',
    `last_name`  CHAR(21)     NOT NULL DEFAULT 'Donor',
    `email`      CHAR(254)    NULL,
    `phone`      VARCHAR(15)  NULL,
    `address1`   VARCHAR(47)  NOT NULL DEFAULT '',
    `address2`   VARCHAR(47)  NOT NULL DEFAULT '',
    `city`       VARCHAR(28)  NOT NULL DEFAULT '',
    `state`      CHAR(2)      NOT NULL DEFAULT '',
    `country`    CHAR(3)      NOT NULL DEFAULT '',
    `zip_code`   CHAR(10)     NOT NULL DEFAULT '',
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE donations
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `donor_id`   INT UNSIGNED    NOT NULL,
    `amount`     INT UNSIGNED    NOT NULL,
    `created_at` TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`donor_id`) REFERENCES donors (`id`)
);