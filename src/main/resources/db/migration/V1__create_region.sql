CREATE TABLE IF NOT EXISTS region(
    region_id serial PRIMARY KEY,
    region_name VARCHAR(100),
    created_date DATE,
    modified_date DATE
);