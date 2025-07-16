CREATE TABLE IF NOT EXISTS persona(
    persona_id serial PRIMARY KEY,
    persona_name VARCHAR(100),
    created_date DATE,
    modified_date DATE
);