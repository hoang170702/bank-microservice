-- Tạo SEQUENCE cho bảng CUSTOMER
CREATE SEQUENCE CUSTOMER_SEQ START WITH 1 INCREMENT BY 1;

-- Tạo bảng CUSTOMER
CREATE TABLE CUSTOMER (
                          ID NUMBER PRIMARY KEY,
                          NAME VARCHAR2(255) NOT NULL,
                          EMAIL VARCHAR2(255) UNIQUE NOT NULL,
                          PHONE_NUMBER VARCHAR2(15),
                          CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CREATED_BY VARCHAR2(255),
                          UPDATED_AT TIMESTAMP,
                          UPDATED_BY VARCHAR2(255)
);
--------------------------------------------------------

-- Tạo SEQUENCE cho bảng ACCOUNTS
CREATE SEQUENCE ACCOUNTS_SEQ START WITH 1 INCREMENT BY 1;

-- Tạo bảng ACCOUNTS
CREATE TABLE ACCOUNTS (
                          ACCOUNT_NUMBER NUMBER PRIMARY KEY,
                          CUSTOMER_ID NUMBER NOT NULL,
                          ACCOUNT_TYPE VARCHAR2(255) NOT NULL,
                          BRANCH_ADDRESS VARCHAR2(255),
                          CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CREATED_BY VARCHAR2(255),
                          UPDATED_AT TIMESTAMP,
                          UPDATED_BY VARCHAR2(255),
                          CONSTRAINT FK_CUSTOMER
                              FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID)
                                  ON DELETE CASCADE
);
--------------------------------------------------------

-- Tạo SEQUENCE cho bảng CARDS
CREATE SEQUENCE CARDS_SEQ START WITH 1 INCREMENT BY 1;

-- Tạo bảng CARDS
CREATE TABLE CARDS (
                       ID NUMBER PRIMARY KEY,
                       MOBILE_NUMBER VARCHAR2(20),
                       CARD_NUMBER VARCHAR2(50),
                       CARD_TYPE VARCHAR2(50),
                       TOTAL_LIMIT NUMBER,
                       AMOUNT_USED NUMBER,
                       AVAILABLE_AMOUNT NUMBER,
                       CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CREATED_BY VARCHAR2(255),
                       UPDATED_AT TIMESTAMP,
                       UPDATED_BY VARCHAR2(255)
);
--------------------------------------------------------

-- Tạo SEQUENCE cho bảng LOANS
CREATE SEQUENCE LOANS_SEQ START WITH 1 INCREMENT BY 1;

-- Tạo bảng LOANS
CREATE TABLE LOANS (
                       ID NUMBER PRIMARY KEY,
                       MOBILE_NUMBER VARCHAR2(20),
                       LOAN_NUMBER VARCHAR2(50),
                       LOAN_TYPE VARCHAR2(50),
                       TOTAL_LOAN NUMBER,
                       AMOUNT_PAID NUMBER,
                       OUTSTANDING_AMOUNT NUMBER,
                       CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CREATED_BY VARCHAR2(255),
                       UPDATED_AT TIMESTAMP,
                       UPDATED_BY VARCHAR2(255)
);

-- Commit tất cả thay đổi
COMMIT;
