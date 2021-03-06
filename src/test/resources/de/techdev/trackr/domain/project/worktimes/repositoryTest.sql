-- data for the worktime repository test.
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (0, 0, 'Sun Alley', '31', '15489', 'Munich', 'Deutschland');

INSERT INTO company (id, version, companyId, name, address_id, timeForPayment) VALUES (0, 0, 1000, 'webshop Ltd.', 0, 30);

INSERT INTO project (id, version, identifier, name, company_id, volume, fixedPrice) VALUES (0, 0, '1000.2014.1', 'Webshop - Checkout Development', 0, 60, 15000);

INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement) VALUES (0, 0, 'employee@techdev.de', 'John', 'Johnson', 'Software Engineer', 80, 10000, 'BERLIN', '2014-02-01', 30);

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (0, 0, 0, 0, '2014-03-04', '11:00:00', '18:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (1, 0, 0, 0, '2014-03-04', '12:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (2, 0, 0, 0, '2014-03-05', '12:00:00', '15:00:00');