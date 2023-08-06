
INSERT INTO gift_certificate (certificate_name, description,price, duration)
VALUES ('test_name1','test_description1', 10,1);

INSERT INTO gift_certificate (certificate_name, description,price, duration)
VALUES ('test_name2','test_description2', 10,1);

INSERT INTO gift_certificate (certificate_name, description,price, duration)
VALUES ('test_name3','test_description3', 10,1);

INSERT INTO tag (tag_name)
VALUES ('#test1');
INSERT INTO tag (tag_name)
VALUES ('#test2');
INSERT INTO tag (tag_name)
VALUES ('#test3');
INSERT INTO tag (tag_name)
VALUES ('#test4');
INSERT INTO tag (tag_name)
VALUES ('#test5');

INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1,1);
INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1,2);
INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (2,3);


INSERT INTO user_account (user_name, user_password, user_email,is_active)
VALUES ('user1','test','user1@example.com','ACTIVE');

INSERT INTO orders(user_account_id,cost)
VALUES(1,10);

