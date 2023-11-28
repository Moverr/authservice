ALTER TABLE auth_db.users ALTER COLUMN id TYPE bigint USING id::bigint;
ALTER TABLE auth_db.users ADD enabled boolean NOT NULL DEFAULT true;
ALTER TABLE auth_db.users ALTER COLUMN id TYPE bigint USING id::bigint;
ALTER TABLE auth_db.roles DROP COLUMN project_id;
ALTER TABLE auth_db.users RENAME COLUMN date_created TO created_at;
ALTER TABLE auth_db.users RENAME COLUMN date_update TO updated_at;



ALTER TABLE auth_db.permissions ADD "delete" varchar NULL;


ALTER TABLE IF EXISTS auth_db.roles
    ALTER COLUMN id DROP DEFAULT;

ALTER TABLE IF EXISTS auth_db.roles
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;

ALTER TABLE IF EXISTS auth_db.roles
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE IF EXISTS auth_db.roles
    ALTER COLUMN created_at SET NOT NULL;




    ALTER TABLE IF EXISTS auth_db.project_resources
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE IF EXISTS auth_db.project_resources
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 CACHE 1 );
ALTER TABLE IF EXISTS auth_db.project_resources
    ADD CONSTRAINT pr_project_rs PRIMARY KEY (id);
ALTER TABLE IF EXISTS auth_db.project_resources
    ADD CONSTRAINT fr_project FOREIGN KEY (project_id)
    REFERENCES auth_db.projects (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;




    CREATE TABLE IF NOT EXISTS auth_db.user_groups
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    user_id bigint,
    role_id bigint,
    status character varying,
    created_at time without time zone,
    created_by bigint,
    updated_at timestamp without time zone,
    updated_by bigint
);

ALTER TABLE IF EXISTS auth_db.user_groups
    OWNER to postgres;
