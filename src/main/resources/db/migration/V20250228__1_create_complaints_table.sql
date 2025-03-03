CREATE TABLE complaints (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reporter VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    complaint_count INT NOT NULL DEFAULT 1,
    CONSTRAINT unique_product_id_reporter UNIQUE (product_id, reporter)
);
