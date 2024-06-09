CREATE TABLE MASTER_NEWS_LANGUAGES (
    language_id INT PRIMARY KEY,
    language_name VARCHAR2(100)
);

CREATE TABLE MASTER_COUNTRIES (
    country_id INT PRIMARY KEY,
    country_name VARCHAR2(100),
    telephone_code VARCHAR2(20)
);


CREATE TABLE MASTER_STATES (
    state_id INT PRIMARY KEY,
    state_name VARCHAR2(100),
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES MASTER_COUNTRIES(country_id)
);

CREATE TABLE MASTER_DISTRICTS (
    district_id INT PRIMARY KEY,
    district_name VARCHAR(100),
    state_id INT,
    FOREIGN KEY (state_id) REFERENCES MASTER_STATES(state_id)
);


CREATE TABLE MASTER_MANDALS (
    mandal_id INT PRIMARY KEY,
    mandal_name VARCHAR(100),
    district_id INT,
    FOREIGN KEY (district_id) REFERENCES MASTER_DISTRICTS(district_id)
);

CREATE TABLE MASTER_STATEWISE_LOCATIONS (
    location_id INT PRIMARY KEY,
    location_name VARCHAR(100),
    country_id INT,
    state_id INT,
    district_id INT,
    mandal_id INT,
    FOREIGN KEY (country_id) REFERENCES MASTER_COUNTRIES(country_id),
    FOREIGN KEY (state_id) REFERENCES MASTER_STATES(state_id),
    FOREIGN KEY (district_id) REFERENCES MASTER_DISTRICTS(district_id),
    FOREIGN KEY (mandal_id) REFERENCES MASTER_MANDALS(mandal_id)
);
=================================Master details completed========================================


CREATE TABLE USER_DETAILS(
    UserID NUMBER(10) PRIMARY KEY,
    mobileNumber VARCHAR2(13) UNIQUE,
    Username VARCHAR2(50),
    Age NUMBER(3),
    Gender VARCHAR2(10) CHECK (Gender IN ('Male', 'Female', 'Other')),
    Location VARCHAR2(100),
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Active CHAR(1) DEFAULT 'Y' CHECK (Active IN ('Y', 'N'))
);

================================User Details Completed==============================================

CREATE TABLE MASTER_BATCH_JOBS (
    BATCH_ID NUMBER PRIMARY KEY,
    DELIVERY_TIME VARCHAR2(20)		,
    INTERVAL_MINUTES NUMBER(2) DEFAULT 30
);

CREATE TABLE SUBSCRIPTION_TYPE (
  subscriptiontypeid INT PRIMARY KEY,
  subscriptiontype VARCHAR2(10) CHECK (subscriptiontype IN ('FREE', 'PAID')),
  subscriptionduration VARCHAR2(10) CHECK (subscriptionduration IN ('1 MONTH', '3 MONTHS', '6 MONTHS', '12 MONTHS')),
  subscriptionfee DECIMAL(10, 2) CHECK (subscriptionfee >= 0),
  CONSTRAINT chk_subscription_type_fee CHECK (
    (UPPER(subscriptiontype) = 'FREE' AND subscriptionfee = 0) OR
    (UPPER(subscriptiontype) = 'PAID' AND subscriptionfee > 0)
  )
);

==================================batch timing and Subscription charges completed==========================
CREATE TABLE MASTER_CATEGORY_TYPE(
    category_id INT PRIMARY KEY,
    category_name VARCHAR2(100)
);


CREATE TABLE VENDOR_DETAILS (
    vendorid INT PRIMARY KEY,
    vendorname VARCHAR2(255),
    vendorcontactdetails VARCHAR2(512),
    vendorstatus VARCHAR2(10) CHECK (vendorstatus IN ('active', 'inactive'))
);

==================================BASIC VENDOR DETAILS Completed==========================================================
CREATE TABLE MASTER_NEWSPAPER (
    newspaper_master_id INT PRIMARY KEY,
    newspaper_name VARCHAR2(100) UNIQUE,
    vendor_id INT,
    FOREIGN KEY (vendor_id) REFERENCES VENDOR_DETAILS(vendorid)
);

CREATE TABLE VENDORS (
    newspaper_id INT,
    location_id INT,
    newspaper_master_id INT,
    newspaper_language INT,
    subscription_type_id INT,
    category_id INT,
    publication_type VARCHAR2(10) CHECK (publication_type IN ('Newspaper', 'Magazine')),

    FOREIGN KEY (location_id) REFERENCES MASTER_STATEWISE_LOCATIONS(location_id),
    FOREIGN KEY (newspaper_master_id) REFERENCES MASTER_NEWSPAPER(newspaper_master_id),
    FOREIGN KEY (newspaper_language) REFERENCES MASTER_NEWS_LANGUAGES(language_id),
    FOREIGN KEY (subscription_type_id) REFERENCES SUBSCRIPTION_TYPE(subscriptiontypeid),
    FOREIGN KEY (category_id) REFERENCES MASTER_CATEGORY_TYPE(category_id),
    PRIMARY KEY (newspaper_id, location_id, newspaper_master_id)
);


==================================VENDOR RELATED DETAILS COMPLETED==========================================================
CREATE TABLE NEWSPAPER_FILES (
    file_id INT PRIMARY KEY,
    newspaper_id INT,
    file_location VARCHAR2(512),
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (newspaper_id) REFERENCES VENDORS(newspaper_id)
);

=================================NEWS PAPER FILE LOCATION COMPLETED===========================================================
CREATE TABLE USER_NEWSPAPER_SUBSCRIPTION (
    user_id INT,
    newspaper_master_id INT,
    subscriptiontypeid INT,
    subscription_start_date DATE,
    subscription_end_date DATE,
    PRIMARY KEY (user_id, newspaper_master_id),
    FOREIGN KEY (user_id) REFERENCES USER_DETAILS(UserID),
    FOREIGN KEY (newspaper_master_id) REFERENCES MASTER_NEWSPAPER(newspaper_master_id),
    FOREIGN KEY (subscriptiontypeid) REFERENCES SUBSCRIPTION_TYPE(subscriptiontypeid)
);


CREATE TABLE USER_SUBSCRIPTION (
    user_id INT,
    newspaper_id INT,
    batch_id NUMBER,
    
    CONSTRAINT PK_UX_USER_SUBSCRIPTION PRIMARY KEY (user_id, newspaper_id),
    FOREIGN KEY (user_id) REFERENCES USER_DETAILS(UserID),
    FOREIGN KEY (newspaper_id) REFERENCES VENDORS(newspaper_id),
    FOREIGN KEY (batch_id) REFERENCES MASTER_BATCH_JOBS(BATCH_ID)
);

CREATE TABLE USER_STATUS (
    UserID NUMBER(10),
    ValidateWhatsAppUser CHAR(1) CHECK (ValidateWhatsAppUser IN ('Y', 'N')),
    Blocked CHAR(1) CHECK (Blocked IN ('Y', 'N')),
    Active CHAR(1) CHECK (Active IN ('Y', 'N')),--rename this field after actully Active looking 2 times, another one is userdeatils also looking
    NotReachable CHAR(1) CHECK (NotReachable IN ('Y', 'N')),
    PRIMARY KEY (UserID),
    FOREIGN KEY (UserID) REFERENCES USER_DETAILS(UserID)
);


CREATE TABLE INVOICE (
    InvoiceID NUMBER(10) PRIMARY KEY,
    UserID NUMBER(10) NOT NULL,
    InvoiceDate DATE NOT NULL,
    FOREIGN KEY (UserID) REFERENCES USER_DETAILS(UserID)
);

=============================================================================================================================

-- Create the function generate_location_name in PL/SQL
CREATE OR REPLACE FUNCTION generate_location_name(state_id IN INT, district_id IN INT,mandal_id IN INT
) RETURN VARCHAR2 AS
    state_name VARCHAR2(100);
    district_name VARCHAR2(100);
    mandal_name VARCHAR2(100);
    result VARCHAR2(300);
BEGIN
    SELECT state_name INTO state_name FROM MASTER_STATES WHERE state_id = generate_location_name.state_id;
    SELECT district_name INTO district_name FROM MASTER_DISTRICTS WHERE district_id = generate_location_name.district_id;
    SELECT mandal_name INTO mandal_name FROM MASTER_MANDALS WHERE mandal_id = generate_location_name.mandal_id;
    
    result := state_name || '_' || district_name || '_' || mandal_name;

    RETURN result;
END;
/

DECLARE
    loc_name VARCHAR2(100);
BEGIN
    loc_name := generate_location_name(9, 24, 3, 1); 
    DBMS_OUTPUT.PUT_LINE('Location Name: ' || loc_name);
END;
===================================================================================================================================

ALTER TABLE USER_SUBSCRIPTION
ADD location_id INT,
ADD newspaper_master_id INT;

-- Step 2: Add the composite foreign key constraint
ALTER TABLE USER_SUBSCRIPTION
ADD CONSTRAINT fk_user_subscription_vendors FOREIGN KEY (newspaper_id, location_id, newspaper_master_id)
REFERENCES VENDORS(newspaper_id, location_id, newspaper_master_id);




ALTER TABLE NEWSPAPER_FILES
ADD location_id INT
ADD newspaper_master_id INT;

ALTER TABLE NEWSPAPER_FILES
ADD CONSTRAINT fk_newspaper_files_vendors FOREIGN KEY (newspaper_id, location_id, newspaper_master_id)
REFERENCES VENDORS(newspaper_id, location_id, newspaper_master_id);