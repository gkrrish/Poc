INSERT INTO MASTER_NEWS_LANGUAGES (language_id, language_name) VALUES (1, 'English');
INSERT INTO MASTER_NEWS_LANGUAGES (language_id, language_name) VALUES (2, 'Hindi');
INSERT INTO MASTER_NEWS_LANGUAGES (language_id, language_name) VALUES (3, 'Telugu');

INSERT INTO MASTER_COUNTRIES (country_id, country_name, telephone_code)VALUES(1, 'United States', '+1');
INSERT INTO MASTER_COUNTRIES (country_id, country_name, telephone_code)VALUES(2, 'India', '+91');

INSERT INTO MASTER_STATES (state_id, state_name, country_id)VALUES(1, 'Telangana', 2);
    
INSERT INTO MASTER_DISTRICTS (district_id, district_name, state_id)VALUES(1, 'Hyderabad', 1);    
INSERT INTO MASTER_DISTRICTS (district_id, district_name, state_id)VALUES(2, 'Wanaparthy', 1);

INSERT INTO MASTER_MANDALS (mandal_id, mandal_name, district_id)VALUES(1, 'Hyderabad', 1);
INSERT INTO MASTER_MANDALS (mandal_id, mandal_name, district_id)VALUES(2, 'Ghatkesar', 1);
INSERT INTO MASTER_MANDALS (mandal_id, mandal_name, district_id)VALUES(3, 'Gopalpet', 2);
INSERT INTO MASTER_MANDALS (mandal_id, mandal_name, district_id)VALUES(4, 'Wanaparthy', 2);

INSERT INTO MASTER_STATEWISE_LOCATIONS (
    location_id,
    location_name,
    country_id,
    state_id,
    district_id,
    mandal_id
) VALUES (
    1,
    generate_location_name(1, 1, 1), 
    2, --country code for india
    1, -- State ID for Telangana
    1,  -- District ID for the district
    1   -- Mandal ID for the mandal
);


INSERT INTO USER_DETAILS(UserID, Username, Age, Gender, Location, RegistrationDate, Active,mobilenumber)
VALUES (1, 'Krishna', 30, 'Male', 'Hyderabad', TIMESTAMP '2024-04-27 10:00:00', 'Y','+919876543210');


-----------------------------------------------
TRUNCATE TABLE MASTER_BATCH_JOBS;

INSERT INTO MASTER_BATCH_JOBS (BATCH_ID, DELIVERY_TIME)
SELECT ROWNUM AS BATCH_ID,
       TO_CHAR(TIMESTAMP '2024-04-28 04:00:00' + INTERVAL '30' MINUTE * (ROWNUM - 1), 'HH:MI AM') AS DELIVERY_TIME
FROM dual
CONNECT BY LEVEL <= 48;

COMMIT;
----------------------------------------------
INSERT INTO MASTER_Category_Type (category_id, category_name) VALUES (1, 'Local News');
INSERT INTO MASTER_Category_Type (category_id, category_name) VALUES (2, 'Politics');


INSERT INTO SUBSCRIPTION_TYPE (subscriptiontypeid, subscriptiontype, subscriptionduration, subscriptionfee)VALUES (1, 'FREE', '1 month', 0);
INSERT INTO SUBSCRIPTION_TYPE (subscriptiontypeid, subscriptiontype, subscriptionduration, subscriptionfee)VALUES (2, 'PAID', '3 months', 30.00);

INSERT INTO VENDOR_DETAILS (vendorid, vendorname, vendorcontactdetails, vendorstatus)VALUES (1, 'EENADU-PUBLICATIONS', 'SOMAJIGUDA-HYDERABAD-TELANGANA-CONTACT EMAIL FOR MORE DETAILS', 'active');
INSERT INTO VENDOR_DETAILS (vendorid, vendorname, vendorcontactdetails, vendorstatus)VALUES (2, 'ANDHRAJYOTHI-PUBLICATIONS', 'BANJARAHILS-HYDERABAD-TELANGANA-CONTACT DIRECTLY', 'active');
INSERT INTO VENDOR_DETAILS (vendorid, vendorname, vendorcontactdetails, vendorstatus)VALUES (3, 'VAARTHA-PUBLICATIONS', 'LOWER-TANKBUND-HYDERABAD-TELANGANA', 'inactive');


INSERT INTO VENDORS (newspaper_id, location_id, newspaper_name, newspaper_language, subscription_type_id,category_id, publication_type, vendor_id)VALUES (1, 1, 'Eenadu', 3, 1,1, 'Newspaper', 1);
INSERT INTO VENDORS (newspaper_id, location_id, newspaper_name, newspaper_language, subscription_type_id,category_id, publication_type, vendor_id)VALUES (2, 2, 'Eenadu', 3, 2,1, 'Newspaper', 1);
INSERT INTO VENDORS (newspaper_id, location_id, newspaper_name, newspaper_language, subscription_type_id,category_id, publication_type, vendor_id)VALUES (3, 2, 'Andhrajyothi', 3,1, 1, 'Newspaper', 2);


INSERT INTO USER_SUBSCRIPTION (user_id, newspaper_id, batch_id, user_eligible, location_id)VALUES (1, 1, 1, 1, 1);
INSERT INTO USER_SUBSCRIPTION (user_id, newspaper_id, batch_id, user_eligible, location_id)VALUES (1, 2, 2, 1, 2);
INSERT INTO USER_SUBSCRIPTION (user_id, newspaper_id, batch_id, user_eligible, location_id)VALUES (1, 2, 8, 1, 1);
INSERT INTO USER_SUBSCRIPTION (user_id, newspaper_id, batch_id, user_eligible, location_id)VALUES (1, 3, 9, 1, 2);


INSERT INTO USER_STATUS (
    UserID,
    ValidateWhatsAppUser,
    Blocked,
    Active,
    NotReachable
) VALUES (
    1,          -- This should be an existing UserID in the USER_DETAILS table
    'Y',        -- ValidateWhatsAppUser: 'Y' (Yes) or 'N' (No)
    'N',        -- Blocked: 'Y' (Yes) or 'N' (No)
    'Y',        -- Active: 'Y' (Yes) or 'N' (No)
    'N'         -- NotReachable: 'Y' (Yes) or 'N' (No)
);


INSERT INTO USER_NEWSPAPER_SUBSCRIPTION (user_id, newspaper_master_id, subscriptiontypeid, subscription_start_date, subscription_end_date)
VALUES (1, 1, 2, TO_DATE('2024-05-15', 'YYYY-MM-DD'), TO_DATE('2024-06-14', 'YYYY-MM-DD'));


