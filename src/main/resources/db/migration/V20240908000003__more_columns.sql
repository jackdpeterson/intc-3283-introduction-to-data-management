ALTER TABLE entries
    ADD COLUMN `checkbox` TINYINT(1) NOT NULL DEFAULT 0,
    ADD COLUMN `selected_items` JSON NULL,
    ADD COLUMN `single_select` VARCHAR(255) NULL,
    ADD COLUMN `date` DATE NULL,
    ADD COLUMN `datetime` DATETIME NULL,
    ADD COLUMN `multi_select` JSON NULL;