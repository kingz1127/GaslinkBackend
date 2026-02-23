CREATE TABLE users (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    phone       VARCHAR(20)  UNIQUE NOT NULL,
    email       VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name   VARCHAR(255) NOT NULL,
    avatar_url  TEXT,
    role        VARCHAR(20)  NOT NULL DEFAULT 'CUSTOMER',
    is_active   BOOLEAN      NOT NULL DEFAULT FALSE,
    push_token  TEXT,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);