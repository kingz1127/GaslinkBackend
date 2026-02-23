CREATE TABLE orders (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reference        VARCHAR(30) UNIQUE NOT NULL,
    customer_id      UUID NOT NULL REFERENCES users(id),
    vendor_id        UUID NOT NULL REFERENCES vendors(id),
    service_type     VARCHAR(20) NOT NULL,
    status           VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    cylinder_size_kg DOUBLE PRECISION NOT NULL,
    qty              INT NOT NULL DEFAULT 1,
    delivery_lat     DOUBLE PRECISION,
    delivery_lng     DOUBLE PRECISION,
    distance_km      DOUBLE PRECISION,
    subtotal         NUMERIC(12,2),
    delivery_fee     NUMERIC(12,2),
    platform_fee     NUMERIC(12,2),
    total            NUMERIC(12,2),
    payment_method   VARCHAR(30),
    payment_status   VARCHAR(20) NOT NULL DEFAULT 'UNPAID',
    scheduled_at     TIMESTAMPTZ,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_orders_vendor ON orders(vendor_id);
CREATE INDEX idx_orders_status ON orders(status);