-- Clear records from tables using CASCADE to handle dependencies
TRUNCATE TABLE formula_parameter_ids, formula_parameter_descriptions, formula_test_parameters CASCADE;
TRUNCATE TABLE invoiceSubItem, invoiceMainItem, serviceInvoice, executionOrderMain, "serviceNumber" CASCADE;
TRUNCATE TABLE model_specifications_details, model_specifications CASCADE;
TRUNCATE TABLE currency, material_group, service_type, line_type CASCADE;
TRUNCATE TABLE formula CASCADE;

-- Drop all tables, handling dependencies with CASCADE
DROP TABLE IF EXISTS formula_parameter_ids, formula_parameter_descriptions, formula_test_parameters CASCADE;
DROP TABLE IF EXISTS invoiceSubItem, invoiceMainItem, serviceInvoice, executionOrderMain, "serviceNumber" CASCADE;
DROP TABLE IF EXISTS model_specifications_details, model_specifications CASCADE;
DROP TABLE IF EXISTS currency, material_group, service_type, line_type CASCADE;
DROP TABLE IF EXISTS formula CASCADE;


-- Create table for LineType
CREATE TABLE line_type
(
    line_type_code SERIAL PRIMARY KEY,
    code           CHAR(225) UNIQUE NOT NULL,
    description    VARCHAR(255)     NOT NULL
);
-- Create Currency table
CREATE TABLE currency
(
    currency_code SERIAL PRIMARY KEY,
    code          CHAR(225) UNIQUE NOT NULL,
    description   VARCHAR(255)     NOT NULL
);
-- Create Formula table
CREATE TABLE formula
(
    formula_code           SERIAL PRIMARY KEY,
    formula                CHAR(4) UNIQUE NOT NULL,
    description            VARCHAR(255)   NOT NULL,
    number_of_parameters   INTEGER        NOT NULL,
    formula_logic          TEXT,
    expression             TEXT,
    result                 DOUBLE PRECISION
);
CREATE TABLE formula_parameter_ids
(
    formula_code    BIGINT NOT NULL,
    parameter_id    TEXT NOT NULL,
    CONSTRAINT fk_formula_code FOREIGN KEY (formula_code) REFERENCES formula(formula_code)
);
CREATE TABLE formula_parameter_descriptions
(
    formula_code           BIGINT NOT NULL,
    parameter_description  TEXT NOT NULL,
    CONSTRAINT fk_formula_code FOREIGN KEY (formula_code) REFERENCES formula(formula_code)
);
CREATE TABLE formula_test_parameters
(
    formula_code   BIGINT NOT NULL,
    test_parameter DOUBLE PRECISION,
    CONSTRAINT fk_formula_code FOREIGN KEY (formula_code) REFERENCES formula(formula_code)
);

-- Create MaterialGroup table
CREATE TABLE material_group
(
    material_group_code SERIAL PRIMARY KEY,
    code                CHAR(225) UNIQUE NOT NULL,
    description         VARCHAR(255)     NOT NULL
);

-- Create ServiceType table
CREATE TABLE service_type
(
    service_type_code SERIAL PRIMARY KEY,
    service_id        CHAR(225) UNIQUE NOT NULL,
    description       VARCHAR(255)     NOT NULL,
    last_change_date  DATE
);
-- ServiceNumber Table
CREATE TABLE "serviceNumber"
(
    service_number_code                 SERIAL PRIMARY KEY,
    no_service_number                   BIGINT,
    search_term                         VARCHAR(255),
    service_type_code                   VARCHAR(255),
    material_group_code                 VARCHAR(255),
    description                         VARCHAR(255),
    short_text_change_allowed           BOOLEAN,
    deletion_indicator                  BOOLEAN,
    main_item                           BOOLEAN,
    number_to_be_converted              INTEGER,
    converted_number                    INTEGER,
    last_change_date                    DATE,
    service_text                        TEXT,
    base_unit_of_measurement            VARCHAR(255),
    to_be_converted_unit_of_measurement VARCHAR(255),
    default_unit_of_measurement         VARCHAR(255)
);
-- ModelSpecificationsDetails Table
CREATE TABLE model_specifications_details
(
    model_spec_details_code       SERIAL PRIMARY KEY,
    service_number_code           BIGINT,
    no_service_number             BIGINT UNIQUE,
    service_type_code             VARCHAR(255),
    material_group_code           VARCHAR(255),
    personnel_number_code         VARCHAR(255),
    unit_of_measurement_code      VARCHAR(255),
    currency_code                 VARCHAR(255),
    formula_code                  VARCHAR(255),
    line_type_code                VARCHAR(255),
    selection_check_box           BOOLEAN,
    line_index                    CHAR(225) UNIQUE,
    deletion_indicator            BOOLEAN,
    short_text                    VARCHAR(255),
    quantity                      INTEGER NOT NULL,
    gross_price                   INTEGER NOT NULL,
    over_fulfilment_percentage    INTEGER,
    price_changed_allowed         BOOLEAN,
    unlimited_over_fulfillment    BOOLEAN,
    price_per_unit_of_measurement INTEGER,
    external_service_number       CHAR(225),
    net_value                     INTEGER,
    service_text                  TEXT,
    line_text                     TEXT,
    line_number                   CHAR(225),
    alternatives                  VARCHAR(255),
    bidders_line                  BOOLEAN,
    supplementary_line            BOOLEAN,
    lot_size_for_costing_is_one   BOOLEAN,
    last_change_date              DATE,
    service_number_id             BIGINT,
    FOREIGN KEY (service_number_id) REFERENCES "serviceNumber" (service_number_code)
);
-- ModelSpecifications Table
CREATE TABLE model_specifications
(
    model_spec_code                 SERIAL PRIMARY KEY,
    model_spec_details_code         BIGINT[],
    currency_code                   VARCHAR(255),
    model_serv_spec                 CHAR(225) UNIQUE NOT NULL,
    blocking_indicator              BOOLEAN,
    service_selection               BOOLEAN,
    description                     VARCHAR(255)     NOT NULL,
    search_term                     VARCHAR(255),
    last_change_date                DATE,
    model_specifications_details_id BIGINT,
    FOREIGN KEY (model_specifications_details_id) REFERENCES model_specifications_details (model_spec_details_code)
);
CREATE TABLE invoiceMainItem
(
    invoiceMainItemCode     BIGSERIAL PRIMARY KEY,
    serviceNumberCode       BIGINT,
    unitOfMeasurementCode   VARCHAR(255),
    currencyCode            VARCHAR(255),
    formulaCode             VARCHAR(255),
    description             VARCHAR(255),
    quantity                INT,
    amountPerUnit           DOUBLE PRECISION,
    total                   DOUBLE PRECISION,
    profitMargin            DOUBLE PRECISION,
    totalWithProfit         DOUBLE PRECISION,
    doNotPrint              BOOLEAN,
    amountPerUnitWithProfit DOUBLE PRECISION,
    service_number_id       BIGINT,
    FOREIGN KEY (service_number_id) REFERENCES "serviceNumber" (service_number_code) -- Quoted reference
);
CREATE TABLE invoiceSubItem
(
    invoiceSubItemCode    BIGSERIAL PRIMARY KEY,
    invoiceMainItemCode   BIGINT,
    serviceNumberCode     BIGINT,
    unitOfMeasurementCode VARCHAR(255),
    currencyCode          VARCHAR(255),
    formulaCode           VARCHAR(255),
    description           VARCHAR(255),
    quantity              INT,
    amountPerUnit         DOUBLE PRECISION,
    total                 DOUBLE PRECISION,
    main_item_id          BIGINT,
    service_number_id     BIGINT,
    CONSTRAINT fk_main_item FOREIGN KEY (main_item_id) REFERENCES invoiceMainItem (invoiceMainItemCode) ON DELETE CASCADE,
    CONSTRAINT fk_service_number_subitem FOREIGN KEY (service_number_id) REFERENCES "serviceNumber" (service_number_code)
);

-- -- Drop constraints if they exist (only run this if you have previous constraints)
-- ALTER TABLE serviceInvoice DROP CONSTRAINT IF EXISTS fk_execution_order;
-- ALTER TABLE executionOrderMain DROP CONSTRAINT IF EXISTS fk_service_invoice_main;

-- Create executionOrderMain table with updates
CREATE TABLE executionOrderMain
(
    executionOrderMainCode    BIGSERIAL PRIMARY KEY,
    serviceNumberCode         BIGINT,
    unitOfMeasurementCode     VARCHAR(255),
    currencyCode              VARCHAR(255),
    description               VARCHAR(255),
    totalQuantity             INT,
    actualQuantity            INT,
    actualPercentage          INT,
    amountPerUnit             DOUBLE PRECISION,
    total                     DOUBLE PRECISION,
    lineNumber                CHAR(225),
    biddersLine               BOOLEAN,
    supplementaryLine         BOOLEAN,
    lotCostOne                BOOLEAN,
    doNotPrint                BOOLEAN,
    overFulfillmentPercentage INT,
    unlimitedOverFulfillment  BOOLEAN
);

-- Create serviceInvoice table with updates
CREATE TABLE serviceInvoice
(
    serviceInvoiceCode        BIGSERIAL PRIMARY KEY,
    serviceNumberCode         BIGINT,
    description               VARCHAR(255),
    unitOfMeasurementCode     VARCHAR(255),
    currencyCode              VARCHAR(255),
    materialGroupCode         VARCHAR(255),
    personnelNumberCode       VARCHAR(255),
    lineTypeCode              VARCHAR(255),
    serviceTypeCode           VARCHAR(255),
    remainingQuantity         INT,
    quantity                  INT,
    totalQuantity             INT,
    amountPerUnit             DOUBLE PRECISION,
    total                     DOUBLE PRECISION,
    actualQuantity            INT,
    actualPercentage          INT,
    overFulfillmentPercentage INT,
    unlimitedOverFulfillment  BOOLEAN,
    externalServiceNumber     VARCHAR(255),
    serviceText               VARCHAR(255),
    lineText                  VARCHAR(255),
    lineNumber                CHAR(225) UNIQUE,
    biddersLine               BOOLEAN,
    supplementaryLine         BOOLEAN,
    lotCostOne                BOOLEAN,
    doNotPrint                BOOLEAN,
    alternatives              VARCHAR(255),

    -- Foreign key to serviceNumber
    service_number_id         BIGINT,
    CONSTRAINT fk_service_number_invoice FOREIGN KEY (service_number_id)
        REFERENCES "serviceNumber" (service_number_code) ON DELETE CASCADE,

    -- Foreign key to executionOrderMain
    execution_order_main_id   BIGINT,
    CONSTRAINT fk_execution_order FOREIGN KEY (execution_order_main_id)
        REFERENCES executionOrderMain (executionOrderMainCode) ON DELETE CASCADE
);
-- Insert predefined LineType records
INSERT INTO line_type (line_type_code, code, description)
VALUES (1, 'Standard line', 'Default line type'),
       (2, 'Informatory line', 'Informational line'),
       (3, 'Internal line', 'Internal usage line'),
       (4, 'Contingency line', 'Backup or contingency line');
-- INSERT INTO formula (formula, description, number_of_parameters, parameter_ids, parameter_descriptions, formula_logic,
--                      test_parameters)
-- VALUES ('f',
--         'circumference',
--         1,
--         ARRAY ['r'],
--         ARRAY ['radius'],
--         '22/7*r**2',
--         ARRAY [2, 3, 4]);
-- INSERT INTO model_specifications_details (short_text, gross_price, quantity, deletion_indicator, service_text,
--                                           bidders_line, supplementary_line,
--                                           lot_size_for_costing_is_one, currency_code, material_group_code,
--                                           service_type_code, formula_code,
--                                           personnel_number_code, line_type_code, unit_of_measurement_code)
-- VALUES ('Safety fence',
--         5000,
--         20,
--         false,
--         'service text',
--         false,
--         false,
--         false,
--         'egp',
--         'material group 1',
--         'service type 1',
--         'f1',
--         'personnel number 1',
--         'nz',
--         'Gram');
-- INSERT INTO model_specifications (model_serv_spec, model_spec_details_code, description, search_term,
--                                   blocking_indicator, currency_code)
-- VALUES ('ZKDMS',
--         ARRAY [1],
--         'Excavation Model',
--         'Excavation',
--         false,
--         'egp');
-- INSERT INTO "serviceNumber" (description, deletion_indicator, search_term, number_to_be_converted, converted_number,
--                              service_type_code,
--                              material_group_code, base_unit_of_measurement, to_be_converted_unit_of_measurement,
--                              default_unit_of_measurement,
--                              service_text, short_text_change_allowed)
-- VALUES ('Service Number 1',
--         false,
--         'number',
--         20,
--         2000,
--         'service type 1',
--         'material group 1',
--         'Gram',
--         'Kilogram',
--         'Gram',
--         'service text',
--         false);
-- INSERT INTO service_type (service_id, description)
-- VALUES ('service type',
--         'Service Type');
-- INSERT INTO material_group (code, description)
-- VALUES ('Material group',
--         'Material group');
-- INSERT INTO currency (code, description)
-- VALUES ('eur',
--         'Euro');
-- -- Insert into the main item table
-- INSERT INTO invoicemainitem (unitofmeasurementcode, currencycode, quantity, amountperunit)
-- VALUES ('Gram',
--         'egp',
--         2,
--         10);
--
-- -- Insert into the sub-item table (assuming subItems are stored in a separate table related to main_item)
-- INSERT INTO invoicesubitem (unitofmeasurementcode, currencycode, quantity, amountperunit)
-- VALUES ('Gram',
--         'egp',
--         20,
--         100);
-- INSERT INTO executionordermain (unitofmeasurementcode, currencycode, description, totalquantity, amountperunit)
-- VALUES ('gram',
--         'egp',
--         'trial',
--         10,
--         100);
-- INSERT INTO serviceinvoice (unitofmeasurementcode, servicenumbercode, currencycode, totalquantity, quantity,
--                             amountperunit, unlimitedoverfulfillment)
-- VALUES ('Gram',
--         1,
--         'egp',
--         10,
--         12,
--         10,
--         true);
--
--
