
INSERT INTO Store (id, title) VALUES (1,'Spar'), (2, 'Mercator'), (3, 'Hofer'), (4, 'Woolworths');

INSERT INTO Product(id, title, description, category, favourite) VALUES (1, 'Kava CaffeBar', 'Mleta pražena 100g', 'Kava', true), (2, 'Kava Toriavit', 'Mleta pražena 100g', 'Kava', false);
INSERT INTO Product(id, title, description, category, favourite) VALUES (3, 'Kruh srnjakov', 'Hleb 500g', 'Kruh', false), (4, 'Rokometaški kruh ', 'Hleb 500g', 'Kruh', true), (5, 'Bloomer', 'Hleb 500g', 'Kruh', false);
INSERT INTO Product(id, title, description, category, favourite) VALUES (6, 'Divjačinska klobasa', 'Pakirano 350g', 'Suhomesnato', false), (7, 'Poletna klobasa', '350g', 'Suhomesnato', false), (8, 'White pepper salami', '350g', 'Suhomesnato', true);

-- kava
INSERT INTO StoreProduct(PRODUCT_ID, STORE_ID, eur, gbp) VALUES (1, 1, 1.55, 0.0), (1, 2, 1.70, 0.0), (1, 3, 1.85, 0.0); --slo trgovine
INSERT INTO StoreProduct(PRODUCT_ID, STORE_ID, eur, gbp, origPriceEur) VALUES (2, 4, 0.0, 2.89, false); --eng

--kruh
INSERT INTO StoreProduct(PRODUCT_ID, STORE_ID, eur, gbp) VALUES (3, 1, 2.5, 0.0), (3, 2, 2.30, 0.0), (4, 3, 2.65, 0.0); --slo trgovine
INSERT INTO StoreProduct(PRODUCT_ID, STORE_ID, eur, gbp, origPriceEur) VALUES (5, 4, 0.0, 3.30, false); --eng


--salama
INSERT INTO StoreProduct(PRODUCT_ID, STORE_ID, eur, gbp) VALUES (6, 1, 8.99, 0.0), (7, 2, 9.50, 0.0), (6, 3, 8.99, 0.0); --slo trgovine
INSERT INTO StoreProduct(PRODUCT_ID, STORE_ID, eur, gbp, origPriceEur) VALUES (8, 4, 0.0, 16.00, false); --eng




--INSERT INTO Product (title, description, category, favourite) VALUES ('Kava Barcafe', 'Mleta prazena kava. 100g', 'coffe', FALSE);

--INSERT INTO Product (id, title, description, category, favourite) VALUES (123, 'Pašteta', 'Gavrilovič. 75g', 'pate', FALSE);

