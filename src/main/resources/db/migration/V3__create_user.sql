CREATE TABLE IF NOT EXISTS public.user_sidekick(
    user_id serial PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    profile_picture_link VARCHAR(8192),
    about TEXT,
    user_preference VARCHAR(1024),
    fk_persona_id INTEGER,
    fk_region_id INTEGER,
    dark_mode BOOLEAN,
    google_drive_consent TEXT,
    access_requested_date DATE,
    last_active_date DATE,
    onboarded_date DATE,
    created_date DATE,
    modified_date DATE
);