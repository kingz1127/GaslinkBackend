CREATE TABLE vendors (
    id                      UUID PRIMARY KEY REFERENCES users(id),
    business_name           VARCHAR(255) NOT NULL,
    business_address        TEXT,
    nin                     VARCHAR(11) UNIQUE NOT NULL,
    lat                     DOUBLE PRECISION,
    lng                     DOUBLE PRECISION,
    service_radius_km       DOUBLE PRECISION DEFAULT 5,
    verification_status     VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    account_status          VARCHAR(20) NOT NULL DEFAULT 'ENABLED',
    account_disabled_reason TEXT,
    subscription_status     VARCHAR(20) NOT NULL DEFAULT 'INACTIVE',
    is_open                 BOOLEAN NOT NULL DEFAULT FALSE,
    rating                  NUMERIC(3,2) NOT NULL DEFAULT 0,
    total_reviews           INT NOT NULL DEFAULT 0,
    created_at              TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_vendors_location ON vendors(lat, lng);
CREATE INDEX idx_vendors_status ON vendors(verification_status, account_status);