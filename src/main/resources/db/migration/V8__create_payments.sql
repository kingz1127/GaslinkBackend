CREATE TABLE payments (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id    UUID NOT NULL REFERENCES orders(id),
    gateway     VARCHAR(50),
    gateway_ref VARCHAR(255) UNIQUE,
    amount      NUMERIC(12,2),
    currency    VARCHAR(10) DEFAULT 'NGN',
    status      VARCHAR(20) NOT NULL DEFAULT 'UNPAID',
    paid_at     TIMESTAMPTZ,
    metadata    TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);