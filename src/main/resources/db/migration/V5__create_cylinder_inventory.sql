CREATE TABLE cylinder_inventory (
    id                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vendor_id            UUID NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    size_kg              DOUBLE PRECISION NOT NULL,
    available_qty        INT NOT NULL DEFAULT 0,
    refill_price         NUMERIC(12,2),
    exchange_price       NUMERIC(12,2),
    rental_price_per_day NUMERIC(12,2),
    purchase_price       NUMERIC(12,2),
    created_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(vendor_id, size_kg)
);