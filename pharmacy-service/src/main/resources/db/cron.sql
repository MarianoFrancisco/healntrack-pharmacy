CREATE EXTENSION IF NOT EXISTS pg_cron;

SELECT cron.schedule(
               'update_stock_snapshot',
               '0 0 * * *',
               $$SELECT update_stock_snapshot();$$
);