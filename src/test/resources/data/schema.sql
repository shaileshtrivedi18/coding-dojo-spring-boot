CREATE TABLE weather (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    updated_timestamp timestamp NULL,
    temperature NUMERIC(5, 2)
);
