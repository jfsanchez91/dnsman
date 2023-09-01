CREATE TABLE IF NOT EXISTS domain
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) UNIQUE NOT NULL
);
CREATE INDEX IF NOT EXISTS domain_name_index ON domain (name);

CREATE TABLE IF NOT EXISTS record
(
    id                    BIGSERIAL PRIMARY KEY,
    domain_id             BIGINT NOT NULL REFERENCES domain ON DELETE CASCADE,
    value                 VARCHAR(255),
    type                  VARCHAR(255),
    ttl                   BIGINT,
    typer                 VARCHAR(20)
);
CREATE INDEX IF NOT EXISTS record_domain_id_index ON record (domain_id);
