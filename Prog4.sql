CREATE TABLE Member (
    memberID NUMBER(10),
    name VARCHAR2(50),
    phoneNumber NUMBER(15), -- Wikipedia says max phone # length is 15 digits
    email VARCHAR2(50),
    dateOfBirth DATE,
    emergencyContactNumber NUMBER(15),
    PRIMARY KEY (memberID)
);

CREATE TABLE SkiPass (
    passID NUMBER(10),
    totalUses NUMBER(4),
    remainingUses NUMBER(4), -- 10, 25, 65, 1000
    expirationDate DATE,
    price NUMBER(3), -- 75, 140, 250, or 750
    active NUMBER(1),
    PRIMARY KEY (passID)
);

/*
Equipment Type	   Variants
Ski Boots	       Sizes 4.0 to 14.0 (half sizes)
Ski Poles	       Lengths 100cm to 140cm
Skis (Alpine)	   Lengths 115cm to 200cm
Snowboards	       Lengths 90cm to 178cm
Protective Gear	   XS, S, M, L, XL
 */

CREATE TABLE Equipment(
    itemID NUMBER(10),
    type VARCHAR2(20), -- Boots, poles, snowboards, alpine skis, & protective gear
    itemSize VARCHAR2(20), -- XS, S, M, L, XL
    rented NUMBER(1), --  0 = Unrented | 1 = Rented
    active NUMBER(1),
    PRIMARY KEY (itemID)
);

CREATE TABLE EquipmentRental (
    rentalID NUMBER(38),
    memberID NUMBER(38),
    passID NUMBER(38),
    itemID NUMBER(38),
    rentalDate DATE,
    returnStatus NUMBER(38), -- 0 = unused | 1 = in use
    PRIMARY KEY (rentalID)
);

CREATE TABLE Lesson (
    lessonID NUMBER(10),
    instructorID NUMBER(10),
    startTime TIMESTAMP, -- Includes date (YYYY-MM-DD HH24:MI:SS)
    difficulty NUMBER(1),
    lessonType VARCHAR2(50),
    lessonGroup VARCHAR2(50),
    PRIMARY KEY (lessonID)
);

CREATE TABLE LessonPurchase (
    orderID NUMBER(10),
    lessonID NUMBER(10),
    memberID NUMBER(10),
    purchaseTime TIMESTAMP,
    purchaseDate DATE,
    totalUses NUMBER(10),
    remainingUses NUMBER(10),
    PRIMARY KEY (orderID)
);

CREATE TABLE Employee (
    employeeID NUMBER(10),
    name VARCHAR2(50),
    phoneNumber NUMBER(15),
    email VARCHAR2(50),
    role VARCHAR2(50),
    startDate DATE,
    gender VARCHAR2(10),
    dateOfBirth DATE,
    certificationLevel NUMBER(38),
    workPlace NUMBER(38), -- FK to Property (propertyID)
    PRIMARY KEY (employeeID)
);

CREATE TABLE Lift (
    liftName VARCHAR2(50),
    openTime TIMESTAMP,
    closeTime TIMESTAMP,
    openDate DATE,
    closeDate DATE,
    destination VARCHAR2(50),
    PRIMARY KEY (liftName)
);

CREATE TABLE LiftUsage(
    passID NUMBER(10),
    liftName VARCHAR2(50),
    time TIMESTAMP(0),
    PRIMARY KEY (passID)
);

CREATE TABLE Trail (
    trailName VARCHAR2(50),
    startLocation VARCHAR2(50),
    endLocation VARCHAR2(50),
    difficulty NUMBER(1),
    PRIMARY KEY (trailName)
);

CREATE TABLE Property(
    propertyID NUMBER(10),
    name VARCHAR2(50),
    type VARCHAR2(50),
    income NUMBER(10),
    PRIMARY KEY (propertyID)
);

CREATE TABLE AuditLog (
  auditId        NUMBER(10) PRIMARY KEY,
  tableName     VARCHAR2(20),
  identifier     VARCHAR2(20),
  operation      VARCHAR2(10),
  time     	 DATE,
  PRIMARY KEY (AuditLog)
);

INSERT INTO Member VALUES (3010, 'Alice Johnson', 1234567890, 'alice@example.com', TO_DATE('1990-06-15', 'YYYY-MM-DD'), 9876543210);
INSERT INTO Member VALUES (3011, 'Bob Smith', 1987654321, 'bmith@example.com', TO_DATE('1985-03-22', 'YYYY-MM-DD'), 8765432190);
INSERT INTO Member VALUES (3012, 'Carlos Ruiz', 1212121212, 'carlosruiz121@example.com', TO_DATE('2000-12-01', 'YYYY-MM-DD'), 9090909090);

INSERT INTO SkiPass VALUES (3010, 101, 10, 4, TO_DATE('2025-12-31', 'YYYY-MM-DD'), 75, 1);
INSERT INTO SkiPass VALUES (3011, 102, 65, 12, TO_DATE('2025-12-31', 'YYYY-MM-DD'), 250, 1);
INSERT INTO SkiPass VALUES (3012, 103, 1000, 999, TO_DATE('2026-12-31', 'YYYY-MM-DD'), 750, 1);


-- Ski Boots
INSERT INTO Equipment VALUES (1, 'Ski Boots', '4.0', 0);
INSERT INTO Equipment VALUES (2, 'Ski Boots', '7.5', 0);
INSERT INTO Equipment VALUES (3, 'Ski Boots', '10.0', 0);

-- Ski Poles
INSERT INTO Equipment VALUES (4, 'Ski Poles', '100', 0);
INSERT INTO Equipment VALUES (5, 'Ski Poles', '120', 0);
INSERT INTO Equipment VALUES (6, 'Ski Poles', '140', 0);

-- Skis (Alpine)
INSERT INTO Equipment VALUES (7, 'Skis (Alpine)', '115', 10;
INSERT INTO Equipment VALUES (8, 'Skis (Alpine)', '160', 0);
INSERT INTO Equipment VALUES (9, 'Skis (Alpine)', '200', 0);

-- Snowboards
INSERT INTO Equipment VALUES (10, 'Snowboard', '90', 0);
INSERT INTO Equipment VALUES (11, 'Snowboard', '150', 0);
INSERT INTO Equipment VALUES (12, 'Snowboard', '178', 0);

-- Protective Gear
INSERT INTO Equipment VALUES (13, 'Protective Gear', 'XS', 0);
INSERT INTO Equipment VALUES (14, 'Protective Gear', 'M', 0);
INSERT INTO Equipment VALUES (15, 'Protective Gear', 'XL', 0);


-- Alice (Member ID 3010) rents equipment on 2025-01-10 using SkiPass ID 101
INSERT INTO EquipmentRental VALUES (1, 3010, 101, TO_DATE('2025-01-10', 'YYYY-MM-DD'), 1);

-- Bob (Member ID 3011) rents equipment on 2024-11-15 using SkiPass ID 102
INSERT INTO EquipmentRental VALUES (4, 3011, 102, TO_DATE('2024-11-15', 'YYYY-MM-DD'), 1);

-- Carlos (Member ID 3012) rents equipment on 2025-03-22 using SkiPass ID 103
INSERT INTO EquipmentRental VALUES (8, 3012, 103, TO_DATE('2025-03-22', 'YYYY-MM-DD'), 1);

-- Alice (Member ID 3010) rents equipment on 2025-02-01 using SkiPass ID 101
INSERT INTO EquipmentRental VALUES (12, 3010, 101, TO_DATE('2025-02-01', 'YYYY-MM-DD'), 1);

-- Bob (Member ID 3011) rents equipment on 2024-09-10 using SkiPass ID 102
INSERT INTO EquipmentRental VALUES (13, 3011, 102, TO_DATE('2024-09-10', 'YYYY-MM-DD'), 1);


INSERT INTO Lesson VALUES (1, 101, TO_TIMESTAMP('2025-01-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 'Group Ski', 'Beginner');
INSERT INTO Lesson VALUES (2, 102, TO_TIMESTAMP('2025-01-02 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 'Private Snowboarding', 'Advanced');
INSERT INTO Lesson VALUES (3, 103, TO_TIMESTAMP('2025-01-03 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 'Group Snowboarding', 'Intermediate');

