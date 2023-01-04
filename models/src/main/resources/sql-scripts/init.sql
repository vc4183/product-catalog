
INSERT INTO Store (id, title) VALUES (1,'Spar'), (2, 'Mercator'), (3, 'Hofer'), (4, 'Woolworths');

INSERT INTO Product(id, title, description, category, favourite) VALUES (1, 'Kava CaffeBar', 'Mleta pražena 100g', 'kava', true), (2, 'Kava Toriavit', 'Mleta pražena 100g', 'kava', false);
INSERT INTO Product(id, title, description, category, favourite) VALUES (3, 'Kruh srnjakov', 'Hleb 500g', 'kruh', false), (4, 'Rokometaški kruh ', 'Hleb 800g', 'kruh', false);
INSERT INTO Product(id, title, description, category, favourite) VALUES (5, 'Divjačinska klobasa', 'Pakirano 350g', 'suhomesnato', false), (6, 'Poletna klobasa', '450g', 'suhomesnato', false);


INSERT INTO StoreProduct(STORE_ID, PRODUCT_ID, eur, gbp) VALUES (1,1, 1.55, 0.0), (2, 1, 1.70, 0.0), (3,1, 1.85, 0.0);
INSERT INTO StoreProduct(STORE_ID, PRODUCT_ID, eur, gbp, origPriceEur) VALUES (4, 2, 0.0, 2.4, false)



--INSERT INTO Product (title, description, category, favourite) VALUES ('Kava Barcafe', 'Mleta prazena kava. 100g', 'coffe', FALSE);

--INSERT INTO Product (id, title, description, category, favourite) VALUES (123, 'Pašteta', 'Gavrilovič. 75g', 'pate', FALSE);

