CREATE
OR REPLACE FUNCTION update_stock_snapshot()
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
UPDATE stock_snapshot ss
SET total_quantity = COALESCE((SELECT SUM(b.quantity_on_hand)
                               FROM batches b
                               WHERE b.medicine_id = ss.medicine_id
                                 AND (b.expiration_date IS NULL OR b.expiration_date >= CURRENT_DATE)), 0),
    updated_at     = now();
END;
$$;
