-- Removes obsolete RCON feature currency assignments after the RCON subsystem was deleted.

DELETE FROM `currency_use_cases`
WHERE `use_case` IN ('rcon.primary', 'rcon.secondary', 'rcon.special');
