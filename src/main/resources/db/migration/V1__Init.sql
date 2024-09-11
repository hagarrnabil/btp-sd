-- Create table for LineType
CREATE TABLE line_type (
                           line_type_code SERIAL PRIMARY KEY,
                           code CHAR(225) UNIQUE NOT NULL,
                           description VARCHAR(255) NOT NULL
);
-- Create Currency table
CREATE TABLE currency (
                          currency_code SERIAL PRIMARY KEY,
                          code CHAR(225) UNIQUE NOT NULL,
                          description VARCHAR(255) NOT NULL
);
-- Create Formula table
CREATE TABLE formula (
                         formula_code SERIAL PRIMARY KEY,
                         formula CHAR(4) UNIQUE NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         number_of_parameters INTEGER NOT NULL,
                         formula_logic TEXT,
                         expression TEXT,
                         result DOUBLE PRECISION,
                         parameter_ids CHAR(1)[] NOT NULL,
                         parameter_descriptions TEXT[] NOT NULL,
                         test_parameters DOUBLE PRECISION[]
);

-- Create MaterialGroup table
CREATE TABLE material_group (
                                material_group_code SERIAL PRIMARY KEY,
                                code CHAR(225) UNIQUE NOT NULL,
                                description VARCHAR(255) NOT NULL
);

-- Create ServiceType table
CREATE TABLE service_type (
                              service_type_code SERIAL PRIMARY KEY,
                              service_id CHAR(225) UNIQUE NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              last_change_date DATE
);
-- ModelSpecifications Table
CREATE TABLE model_specifications (
                                      model_spec_code SERIAL PRIMARY KEY,
                                      model_spec_details_code BIGINT[],
                                      currency_code VARCHAR(255),
                                      model_serv_spec CHAR(225) UNIQUE NOT NULL,
                                      blocking_indicator BOOLEAN,
                                      service_selection BOOLEAN,
                                      description VARCHAR(255) NOT NULL,
                                      search_term VARCHAR(255),
                                      last_change_date DATE,
                                      model_specifications_details_id BIGINT,
                                      FOREIGN KEY (model_specifications_details_id) REFERENCES model_specifications_details(model_spec_details_code)
);

-- ModelSpecificationsDetails Table
CREATE TABLE model_specifications_details (
                                              model_spec_details_code SERIAL PRIMARY KEY,
                                              service_number_code BIGINT,
                                              no_service_number BIGINT UNIQUE,
                                              service_type_code VARCHAR(255),
                                              material_group_code VARCHAR(255),
                                              personnel_number_code VARCHAR(255),
                                              unit_of_measurement_code VARCHAR(255),
                                              currency_code VARCHAR(255),
                                              formula_code VARCHAR(255),
                                              line_type_code VARCHAR(255),
                                              selection_check_box BOOLEAN,
                                              line_index CHAR(225) UNIQUE,
                                              deletion_indicator BOOLEAN,
                                              short_text VARCHAR(255),
                                              quantity INTEGER NOT NULL,
                                              gross_price INTEGER NOT NULL,
                                              over_fulfilment_percentage INTEGER,
                                              price_changed_allowed BOOLEAN,
                                              unlimited_over_fulfillment BOOLEAN,
                                              price_per_unit_of_measurement INTEGER,
                                              external_service_number CHAR(225),
                                              net_value INTEGER,
                                              service_text TEXT,
                                              line_text TEXT,
                                              line_number CHAR(225),
                                              alternatives VARCHAR(255),
                                              bidders_line BOOLEAN,
                                              supplementary_line BOOLEAN,
                                              lot_size_for_costing_is_one BOOLEAN,
                                              last_change_date DATE,
                                              service_number_id BIGINT,
                                              FOREIGN KEY (service_number_id) REFERENCES service_number(service_number_code)
);

-- ServiceNumber Table
CREATE TABLE service_number (
                                service_number_code SERIAL PRIMARY KEY,
                                no_service_number BIGINT,
                                search_term VARCHAR(255) NOT NULL,
                                service_type_code VARCHAR(255) NOT NULL,
                                material_group_code VARCHAR(255),
                                description VARCHAR(255) NOT NULL,
                                short_text_change_allowed BOOLEAN,
                                deletion_indicator BOOLEAN,
                                main_item BOOLEAN,
                                number_to_be_converted INTEGER,
                                converted_number INTEGER,
                                last_change_date DATE,
                                service_text TEXT,
                                base_unit_of_measurement VARCHAR(255),
                                to_be_converted_unit_of_measurement VARCHAR(255),
                                default_unit_of_measurement VARCHAR(255)
);
CREATE TABLE invoiceMainItem (
                                 invoiceMainItemCode BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 serviceNumberCode BIGINT,
                                 unitOfMeasurementCode VARCHAR(255),
                                 currencyCode VARCHAR(255),
                                 formulaCode VARCHAR(255),
                                 description VARCHAR(255),
                                 quantity INT,
                                 amountPerUnit DOUBLE,
                                 total DOUBLE,
                                 profitMargin DOUBLE,
                                 totalWithProfit DOUBLE,
                                 doNotPrint BOOLEAN,
                                 amountPerUnitWithProfit DOUBLE,
                                 service_number_id BIGINT,
                                 CONSTRAINT fk_service_number FOREIGN KEY (service_number_id) REFERENCES serviceNumber(serviceNumberCode)
);
CREATE TABLE invoiceSubItem (
                                invoiceSubItemCode BIGINT AUTO_INCREMENT PRIMARY KEY,
                                invoiceMainItemCode BIGINT,
                                serviceNumberCode BIGINT,
                                unitOfMeasurementCode VARCHAR(255),
                                currencyCode VARCHAR(255),
                                formulaCode VARCHAR(255),
                                description VARCHAR(255),
                                quantity INT,
                                amountPerUnit DOUBLE,
                                total DOUBLE,
                                main_item_id BIGINT,
                                service_number_id BIGINT,
                                CONSTRAINT fk_main_item FOREIGN KEY (main_item_id) REFERENCES invoiceMainItem(invoiceMainItemCode) ON DELETE CASCADE,
                                CONSTRAINT fk_service_number_subitem FOREIGN KEY (service_number_id) REFERENCES serviceNumber(serviceNumberCode)
);
CREATE TABLE executionOrderMain (
                                    executionOrderMainCode BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    serviceNumberCode BIGINT,
                                    unitOfMeasurementCode VARCHAR(255),
                                    currencyCode VARCHAR(255),
                                    description VARCHAR(255),
                                    totalQuantity INT,
                                    actualQuantity INT,
                                    actualPercentage INT,
                                    amountPerUnit DOUBLE,
                                    total DOUBLE,
                                    lineNumber CHAR(225),
                                    biddersLine BOOLEAN,
                                    supplementaryLine BOOLEAN,
                                    lotCostOne BOOLEAN,
                                    doNotPrint BOOLEAN,
                                    overFulfillmentPercentage INT,
                                    unlimitedOverFulfillment BOOLEAN,
                                    service_invoice_main_id BIGINT,
                                    CONSTRAINT fk_service_invoice_main FOREIGN KEY (service_invoice_main_id) REFERENCES serviceInvoice(serviceInvoiceCode) ON DELETE CASCADE
);
CREATE TABLE serviceInvoice (
                                serviceInvoiceCode BIGINT AUTO_INCREMENT PRIMARY KEY,
                                serviceNumberCode BIGINT,
                                description VARCHAR(255),
                                unitOfMeasurementCode VARCHAR(255),
                                currencyCode VARCHAR(255),
                                materialGroupCode VARCHAR(255),
                                personnelNumberCode VARCHAR(255),
                                lineTypeCode VARCHAR(255),
                                serviceTypeCode VARCHAR(255),
                                remainingQuantity INT,
                                quantity INT,
                                totalQuantity INT,
                                amountPerUnit DOUBLE,
                                total DOUBLE,
                                actualQuantity INT,
                                actualPercentage INT,
                                overFulfillmentPercentage INT,
                                unlimitedOverFulfillment BOOLEAN,
                                externalServiceNumber VARCHAR(255),
                                serviceText VARCHAR(255),
                                lineText VARCHAR(255),
                                lineNumber CHAR(225) UNIQUE,
                                biddersLine BOOLEAN,
                                supplementaryLine BOOLEAN,
                                lotCostOne BOOLEAN,
                                doNotPrint BOOLEAN,
                                alternatives VARCHAR(255),
                                service_number_id BIGINT,
                                execution_order_main_id BIGINT,
                                CONSTRAINT fk_service_number_invoice FOREIGN KEY (service_number_id) REFERENCES serviceNumber(serviceNumberCode),
                                CONSTRAINT fk_execution_order FOREIGN KEY (execution_order_main_id) REFERENCES executionOrderMain(executionOrderMainCode) ON DELETE CASCADE
);
-- Insert predefined LineType records
INSERT INTO line_type (line_type_code, code, description) VALUES
                                                              (1, 'Standard line', 'Default line type'),
                                                              (2, 'Informatory line', 'Informational line'),
                                                              (3, 'Internal line', 'Internal usage line'),
                                                              (4, 'Contingency line', 'Backup or contingency line');

