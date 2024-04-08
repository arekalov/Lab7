BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS LOCATION
(
    id   SERIAL PRIMARY KEY,
    x    REAL NOT NULL,
    y    REAL NOT NULL,
    name TEXT NULL
);

CREATE TABLE IF NOT EXISTS ADRESS
(
    id       SERIAL PRIMARY KEY,
    street   TEXT                    NULL,
    location INT REFERENCES LOCATION NOT NULL
);

CREATE TYPE OrganizationType AS ENUM ('COMMERCIAL', 'TRUST',
    'PRIVATE_LIMITED_COMPANY', 'OPEN_JOINT_STOCK_COMPANY');

CREATE TABLE IF NOT EXISTS ORGANIZATION
(
    id             SERIAL PRIMARY KEY,
    name           TEXT                  NOT NULL,
    annualTurnover REAL,
    type           OrganizationType      NOT NULL,
    postalAdress   INT REFERENCES ADRESS NOT NULL
);

CREATE TABLE IF NOT EXISTS COORDINATE
(
    id SERIAL PRIMARY KEY,
    x  REAL NOT NULL,
    y  REAL NOT NULL
);

CREATE TYPE UnitOfMeasure AS ENUM ('KILOGRAMS', 'SQUARE_METERS', 'PCS', 'MILLIGRAMS');

CREATE TABLE IF NOT EXISTS USERS
(
    login    TEXT PRIMARY KEY,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS PRODUCT
(
    id              SERIAL PRIMARY KEY,
    name            TEXT                        NOT NULL,
    coordinates     INT REFERENCES COORDINATE   NOT NULL,
    localDate       TIMESTAMP                   NOT NULL,
    price           BIGINT                      NULL,
    partNumber      TEXT                        NOT NULL,
    manufactureCost INT                         NOT NULL,
    unitOfMeasure   UnitOfMeasure               NOT NULL,
    manufacturer    INT REFERENCES ORGANIZATION NOT NULL,
    creatorLogin    TEXT REFERENCES USERS        NOT NULL
);
END TRANSACTION;