CREATE TABLE addresses (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    label        VARCHAR(100),
    address_line TEXT         NOT NULL,
    city         VARCHAR(100),
    state        VARCHAR(100),
    lat          DOUBLE PRECISION,
    lng          DOUBLE PRECISION,
    is_default   BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);