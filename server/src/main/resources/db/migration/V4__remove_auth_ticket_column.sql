SET @drop_auth_ticket_column = (
    SELECT IF(
        COUNT(*) > 0,
        'ALTER TABLE players DROP COLUMN auth_ticket',
        'SELECT 1'
    )
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'players'
      AND COLUMN_NAME = 'auth_ticket'
);

PREPARE drop_auth_ticket_column_statement FROM @drop_auth_ticket_column;
EXECUTE drop_auth_ticket_column_statement;
DEALLOCATE PREPARE drop_auth_ticket_column_statement;
