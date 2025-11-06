CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TYPE medicine_status AS ENUM ('ACTIVE','INACTIVE');
CREATE TYPE unit_type AS ENUM ('UNIT','TAB','CAP','AMP','VIAL','BOTTLE','BOX');
CREATE TYPE sale_status AS ENUM ('OPEN','COMPLETED');
CREATE TYPE buyer_type AS ENUM ('HOSPITALIZATION','PATIENT');

CREATE TABLE medicines
(
    id            UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
    code          VARCHAR(10) UNIQUE NOT NULL,
    name          VARCHAR(120)       NOT NULL,
    description   VARCHAR(500),
    status        medicine_status    NOT NULL DEFAULT 'ACTIVE',
    unit          unit_type          NOT NULL,
    min_stock     INTEGER            NOT NULL DEFAULT 0,
    current_price NUMERIC(14, 2)     NOT NULL CHECK (current_price >= 0),
    current_cost  NUMERIC(14, 2)     NOT NULL CHECK (current_cost >= 0),
    created_at    TIMESTAMP          NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP          NOT NULL DEFAULT now()
);

CREATE TABLE batches
(
    id                 UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    medicine_id        UUID      NOT NULL REFERENCES medicines (id),
    expiration_date    DATE,
    purchased_quantity INTEGER   NOT NULL CHECK (purchased_quantity > 0),
    quantity_on_hand   INTEGER   NOT NULL CHECK (quantity_on_hand >= 0),
    purchase_price     NUMERIC(14, 2) CHECK (purchase_price IS NULL OR purchase_price >= 0),
    purchased_at       TIMESTAMP,
    purchased_by       UUID,
    created_at         TIMESTAMP NOT NULL DEFAULT now(),
    updated_at         TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE stock_snapshot
(
    medicine_id    UUID PRIMARY KEY REFERENCES medicines (id),
    total_quantity INTEGER   NOT NULL CHECK (total_quantity >= 0),
    updated_at     TIMESTAMP NOT NULL
);

CREATE TABLE monthly_closings
(
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    year  INT NOT NULL,
    month INT NOT NULL CHECK (month BETWEEN 1 AND 12
) ,
  closed_by UUID,
  closed_at TIMESTAMP NOT NULL,
  UNIQUE (year, month)
);

CREATE TABLE monthly_closing_lines
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    closing_id     UUID           NOT NULL REFERENCES monthly_closings (id) ON DELETE CASCADE,
    medicine_id    UUID           NOT NULL REFERENCES medicines (id),
    system_qty     INTEGER        NOT NULL CHECK (system_qty >= 0),
    physical_qty   INTEGER        NOT NULL CHECK (physical_qty >= 0),
    variance       INTEGER        NOT NULL,
    unit_cost      NUMERIC(14, 2) NOT NULL CHECK (unit_cost >= 0),
    variance_value NUMERIC(14, 2) NOT NULL,
    note           VARCHAR(500),
    CHECK (variance = physical_qty - system_qty),
    CHECK (variance_value = (variance::numeric * unit_cost)),
    UNIQUE (closing_id, medicine_id)
);

CREATE TABLE sales
(
    id          UUID PRIMARY KEY        DEFAULT gen_random_uuid(),
    occurred_at TIMESTAMP      NOT NULL,
    seller_id   UUID           NOT NULL,
    buyer_id    UUID           NOT NULL,
    buyer_type  buyer_type     NOT NULL,
    status      sale_status    NOT NULL DEFAULT 'OPEN',
    total       NUMERIC(14, 2) NOT NULL CHECK (total >= 0)
);

CREATE TABLE sale_items
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sale_id       UUID           NOT NULL REFERENCES sales (id) ON DELETE CASCADE,
    medicine_id   UUID           NOT NULL REFERENCES medicines (id),
    medicine_code VARCHAR(10)    NOT NULL,
    quantity      INTEGER        NOT NULL CHECK (quantity > 0),
    unit_price    NUMERIC(14, 2) NOT NULL CHECK (unit_price >= 0),
    unit_cost     NUMERIC(14, 2) CHECK (unit_cost IS NULL OR unit_cost >= 0),
    line_total    NUMERIC(14, 2) NOT NULL CHECK (line_total = (quantity::numeric * unit_price))
);
