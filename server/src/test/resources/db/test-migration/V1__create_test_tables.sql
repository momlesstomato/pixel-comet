CREATE TABLE flyway_probe (
    id INT PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE test_seed_values (
    id INT PRIMARY KEY,
    seed_value VARCHAR(64) NOT NULL
);