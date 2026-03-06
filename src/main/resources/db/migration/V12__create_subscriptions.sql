CREATE TABLE subscriptions (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vendor_id     UUID NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    plan          VARCHAR(50),
    amount        NUMERIC(12,2),
    billing_cycle VARCHAR(20),
    status        VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    started_at    TIMESTAMPTZ,
    expires_at    TIMESTAMPTZ,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW()
);