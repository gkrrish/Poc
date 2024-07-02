CREATE SEQUENCE user_delivery_status_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE USER_DELIVERY_STATUS (
    id NUMBER PRIMARY KEY,
    batch_time NUMBER,
    state VARCHAR2(255),
    language VARCHAR2(255),
    user_id NUMBER,
    user_mobile_number VARCHAR2(20),
    email VARCHAR2(255),
    newspaper_id NUMBER,
    newspaper_file_id NUMBER,
    newspaper_file_name VARCHAR2(255),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE TRIGGER user_delivery_status_trigger
BEFORE INSERT ON USER_DELIVERY_STATUS
FOR EACH ROW
BEGIN
    :new.id := user_delivery_status_seq.NEXTVAL;
END;