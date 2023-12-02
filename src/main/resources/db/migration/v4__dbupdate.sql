ALTER TABLE IF EXISTS auth_db.users DROP COLUMN IF EXISTS firstname;

ALTER TABLE IF EXISTS auth_db.users DROP COLUMN IF EXISTS lastname;

ALTER TABLE IF EXISTS auth_db.permissions DROP COLUMN IF EXISTS role_id;
ALTER TABLE IF EXISTS auth_db.permissions DROP CONSTRAINT IF EXISTS permissions_roles_id_fk;