-- Este script crea la tabla payment_order y sus índices.
-- No es necesario ejecutarlo manualmente:
-- 1) Si se levanta la aplicación sin Docker, la base de datos ya contiene la tabla.
-- 2) Si se levanta con Docker, el script inicial de creación de tablas ya se ejecuta automáticamente.

CREATE TABLE IF NOT EXISTS payment_order (
    payment_order_id       BIGSERIAL PRIMARY KEY,
    external_reference     VARCHAR(35) NOT NULL,
    iban_debtor_account    VARCHAR(34) NOT NULL,
    iban_creditor_account  VARCHAR(34) NOT NULL,
    amount                 NUMERIC(18, 2) NOT NULL,
    currency               VARCHAR(3) NOT NULL,
    remittance_information VARCHAR(140),
    execution_date         DATE NOT NULL,
    status                 VARCHAR(30) NOT NULL DEFAULT 'INITIATED',
    updated_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_payment_order_external_reference
    ON payment_order (external_reference);

CREATE INDEX IF NOT EXISTS idx_payment_order_status
    ON payment_order (status);
