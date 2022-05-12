use db2_project;
CREATE TABLE IF NOT EXISTS `User` (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      nickname VARCHAR(25) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    insolvent BIT NOT NULL DEFAULT 0,
    failedPayments INT NOT NULL DEFAULT 0,
    UNIQUE KEY(nickname),
    UNIQUE KEY(email)
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS Employee (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        nickname VARCHAR(25) NOT NULL,
    password VARCHAR(50) NOT NULL,
    UNIQUE KEY(nickname)
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS Bundle (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      title VARCHAR(50)
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS Service (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       offer CHAR(2) NOT NULL,
    mpMins INT,
    mpSms INT,
    mpExtraMinsCost INT,
    mpExtraSmsCost INT,
    fiGBs INT,
    fiExtraGBsCost INT,
    miGBs INT,
    miExtraGBsCost INT,
    CHECK(offer = 'FP' OR offer = 'MP' OR offer = 'FI' OR offer = 'MI')
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS ValidityPeriod (
                                              id INT PRIMARY KEY AUTO_INCREMENT,
                                              months INT NOT NULL,
                                              costPerMonth INT NOT NULL
) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `Order` (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       issueTime TIMESTAMP NOT NULL,
                                       clientId INT,
                                       bundleId INT,
                                       validityPeriodId INT,
                                       totCost INT NOT NULL,
                                       valid BIT,
                                       FOREIGN KEY (clientId) REFERENCES User(id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (bundleId) REFERENCES Bundle(id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (validityPeriodId) REFERENCES ValidityPeriod(id) ON UPDATE CASCADE ON DELETE SET NULL
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS ValidityPeriodsPerBundle (
                                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                                        bundleId INT,
                                                        validityPeriodId INT,
                                                        FOREIGN KEY (bundleId) REFERENCES Bundle(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (validityPeriodId) REFERENCES ValidityPeriod(id) ON UPDATE CASCADE ON DELETE CASCADE
    ) AUTO_INCREMENT = 1;

-- This table specifies the available Service(s) for a Bundle --
CREATE TABLE IF NOT EXISTS ServicesInBundle (
                                                id INT PRIMARY KEY AUTO_INCREMENT,
                                                serviceId INT,
                                                bundleId INT,
                                                UNIQUE KEY(serviceId, bundleId),
    FOREIGN KEY (serviceId) REFERENCES Service(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (bundleId) REFERENCES Bundle(id) ON UPDATE CASCADE ON DELETE CASCADE
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS OptionalProduct (
                                               id INT PRIMARY KEY AUTO_INCREMENT,
                                               title VARCHAR(50) NOT NULL ,
    monthlyFee INT NOT NULL
    ) AUTO_INCREMENT = 1;

-- This table specifies the available OptionalProduct(s) for a Bundle --
CREATE TABLE IF NOT EXISTS AvailableOptionalsForBundle (
                                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                                           optionalId INT,
                                                           bundleId INT,
                                                           UNIQUE KEY(optionalId, bundleId),
    FOREIGN KEY (optionalId) REFERENCES OptionalProduct(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (bundleId) REFERENCES Bundle(id) ON UPDATE CASCADE ON DELETE CASCADE
    ) AUTO_INCREMENT = 1;

-- This table specifies the chosen OptionalProduct(s) for an Order --
CREATE TABLE IF NOT EXISTS ChosenOptionalsInOrder (
                                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                                      orderId INT,
                                                      optionalId INT,
                                                      FOREIGN KEY (orderId) REFERENCES `Order`(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (optionalId) REFERENCES OptionalProduct(id) ON UPDATE CASCADE ON DELETE SET NULL
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS Alert (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     userId INT,
                                     nickname VARCHAR(25),
    amountLastRejection INT NOT NULL,
    dateLastRejection DATE NOT NULL,
    FOREIGN KEY (userId) REFERENCES User(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (nickname) REFERENCES User(nickname) ON UPDATE CASCADE ON DELETE CASCADE
    ) AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS ServiceActivationSchedule (
                                                         id INT PRIMARY KEY AUTO_INCREMENT,
                                                         userId INT,
                                                         orderId INT,
                                                         activationDate DATE NOT NULL,
                                                         deactivationDate DATE NOT NULL,
                                                         FOREIGN KEY (userId) REFERENCES User(id) ON UPDATE CASCADE ON DELETE CASCADE, -- ? --
    FOREIGN KEY (orderId) REFERENCES `Order`(id) ON UPDATE CASCADE ON DELETE CASCADE -- ? --
    ) AUTO_INCREMENT = 1;


/*                                      Materialized Views for Sales Report                                      */

-- Purchases per package --
CREATE TABLE IF NOT EXISTS PurchasesPerPackage (
                                                   bundleId INT PRIMARY KEY,
                                                   purchaseCount INT DEFAULT 0
);

/*
Adds tuple in PPP table as soon as the first valid
order relative to the bundle is issued and updates count
  */
DELIMITER //
CREATE TRIGGER UpdatePurchaseCount
    AFTER UPDATE ON `Order`
    FOR EACH ROW
    BEGIN
        IF NEW.valid = 1 THEN
            IF NEW.bundleId NOT IN (SELECT bundleId FROM PurchasesPerPackage) THEN
                INSERT INTO PurchasesPerPackage (bundleId, purchaseCount)
                VALUES (NEW.bundleId, 0);
            END IF;
        UPDATE PurchasesPerPackage SET purchaseCount = purchaseCount + 1
        WHERE PurchasesPerPackage.bundleId = NEW.bundleId;
        END IF;
    END //
DELIMITER ;

-- Purchases per package and validity period --
CREATE TABLE IF NOT EXISTS PurchasePerPackageValidityPeriod (
                                                                bundleId INT NOT NULL,
                                                                validityId INT NOT NULL,
                                                                purchaseCount INT DEFAULT 0,
                                                                UNIQUE KEY(bundleId, validityId)
    );

/*
Adds tuple in PPP VP table as soon as the first valid
order relative to the bundle is issued and updates count
relative to validity period
*/
DELIMITER //
CREATE TRIGGER UpdatePurchaseCountValidity
    AFTER UPDATE ON `Order`
    FOR EACH ROW
    BEGIN
        IF NEW.valid = 1 THEN
            IF (NEW.bundleId, NEW.validityPeriodId) NOT IN (SELECT bundleId, validityId FROM PurchasePerPackageValidityPeriod) THEN
                INSERT INTO PurchasePerPackageValidityPeriod (bundleId, validityId, purchaseCount)
                VALUES (NEW.bundleId, NEW.validityPeriodId, 0);
            END IF;
        UPDATE PurchasePerPackageValidityPeriod SET purchaseCount = purchaseCount + 1
        WHERE (PurchasePerPackageValidityPeriod.bundleId = NEW.bundleId AND PurchasePerPackageValidityPeriod.validityId = NEW.validityPeriodId);
        END IF;
    END //
DELIMITER ;

-- Total value per package with and without optional products --
CREATE TABLE IF NOT EXISTS TotValuePerPackageSold (
                                                      bundleId INT PRIMARY KEY,
                                                      totValue INT DEFAULT 0,
                                                      totValueNoOptionals INT DEFAULT 0
);

/*
Adds tuple in TV PPS table and updates count
considering both optional product and not
*/
DELIMITER //
CREATE TRIGGER UpdateTotValuePerPackage
    AFTER UPDATE ON `Order`
    FOR EACH ROW
    BEGIN
        IF NEW.valid = 1 THEN
            IF NEW.bundleId NOT IN (SELECT bundleId FROM TotValuePerPackageSold) THEN
                INSERT INTO TotValuePerPackageSold (bundleId, totValue, totValueNoOptionals)
                VALUES (NEW.bundleId, 0, 0);
            END IF;
        UPDATE TotValuePerPackageSold SET totValue = totValue + NEW.totCost
        WHERE TotValuePerPackageSold.bundleId = NEW.bundleId;
        UPDATE TotValuePerPackageSold SET totValueNoOptionals = totValueNoOptionals + ((SELECT months FROM ValidityPeriod vp WHERE vp.id = NEW.validityPeriodId) *
                                                                                       (SELECT costPerMonth FROM ValidityPeriod vp WHERE vp.id = NEW.validityPeriodId))
        WHERE TotValuePerPackageSold.bundleId = NEW.bundleId;
        END IF;
    END //
DELIMITER ;

-- Average number of optional products per package --
CREATE TABLE IF NOT EXISTS AverageNumOptionalsPerPackage (
                                                             bundleId INT PRIMARY KEY,
                                                             averageNumOptionals FLOAT DEFAULT 0
);

/*
Adds tuple in ANO PP table as soon as the first valid
order relative to the bundle is issued and updates average
number of optionals per package
  */
DELIMITER //
CREATE TRIGGER UpdateAvgNumOptionalsPerPackage
    AFTER UPDATE ON `Order`
    FOR EACH ROW
    BEGIN
        DECLARE chosenOptionals FLOAT;
        DECLARE bundleSales FLOAT;
        IF NEW.valid = 1 THEN
            SET chosenOptionals = CAST((SELECT COUNT(*) FROM ChosenOptionalsInOrder co WHERE co.orderId = NEW.id) AS FLOAT);
            SET bundleSales = CAST((SELECT COUNT(*) FROM `Order` o WHERE (o.bundleId = NEW.bundleId AND o.valid = 1)) AS FLOAT);
            IF NEW.bundleId NOT IN (SELECT bundleId FROM AverageNumOptionalsPerPackage) THEN
                INSERT INTO AverageNumOptionalsPerPackage (bundleId, averageNumOptionals)
                VALUES (NEW.bundleId, 0);
            END IF;
            UPDATE AverageNumOptionalsPerPackage SET averageNumOptionals = averageNumOptionals + ((chosenOptionals - averageNumOptionals) / bundleSales)
            WHERE AverageNumOptionalsPerPackage.bundleId = NEW.bundleId;
        END IF;
    END //
DELIMITER ;

-- Best seller optional product --
CREATE TABLE IF NOT EXISTS BestSellerOptional (
                                                  optionalId INT PRIMARY KEY,
                                                  totRevenue INT DEFAULT 0
);

/*
Adds tuple in BS Optional table as soon as the
first is sold, then updates according to valid orders
The best seller will be queried in a later query
  */
DELIMITER //
CREATE TRIGGER UpdateBestSellerOptional
    AFTER UPDATE ON `Order`
    FOR EACH ROW
    BEGIN
        DECLARE done INT DEFAULT FALSE;
        DECLARE chosenId INT;
        DECLARE cur CURSOR FOR (SELECT optionalId FROM ChosenOptionalsInOrder co WHERE co.orderId = NEW.id);
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
        IF NEW.valid = 1 THEN
            OPEN cur;
                chosen_loop: LOOP
                    FETCH cur INTO chosenId;
                    IF done THEN
                      LEAVE chosen_loop;
                    END IF;
                    IF chosenId NOT IN (SELECT optionalId FROM BestSellerOptional) THEN
                        INSERT INTO BestSellerOptional (optionalId, totRevenue)
                        VALUES (chosenId, 0);
                    END IF;
                    UPDATE BestSellerOptional SET totRevenue = totRevenue + ((SELECT monthlyFee FROM OptionalProduct op WHERE op.id = chosenId) *
                                                                             (SELECT months FROM ValidityPeriod vp WHERE vp.id = NEW.validityPeriodId ))
                    WHERE BestSellerOptional.optionalId = chosenId;
                END LOOP;
            CLOSE cur;
        END IF;
    END //
DELIMITER ;

/**
  Adds 1 to the failed order counter
 */
DELIMITER //
CREATE TRIGGER AddFailedOrderToUser
    AFTER UPDATE ON `Order`
    FOR EACH ROW
    BEGIN
       IF NEW.valid = 0 THEN
           UPDATE `User` u SET u.failedPayments = u.failedPayments + 1
           WHERE u.id = NEW.clientId;
       END IF;
    END //
DELIMITER ;

/**
  This trigger populates Alert after 3 failed payments.
  If the user later has other failed payments it doesn't update
  unless he resolved some payments (<3) and then he reaches 3 again.
 */
DELIMITER //
CREATE TRIGGER PopulateAlert
    AFTER UPDATE ON `User`
    FOR EACH ROW
    BEGIN
        IF (NEW.failedPayments >= 3 AND OLD.failedPayments < 3) THEN
            INSERT INTO Alert (userId, nickname, amountLastRejection, dateLastRejection)
            VALUES (NEW.id,
                    NEW.nickname,
                    (SELECT totCost FROM `Order` o WHERE o.clientId = NEW.id ORDER BY o.issueTime DESC LIMIT 1),
                    (SELECT issueTime FROM `Order` o WHERE o.clientId = NEW.id ORDER BY o.issueTime DESC LIMIT 1));
        END IF;
    END //
DELIMITER ;
