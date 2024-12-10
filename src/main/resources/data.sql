INSERT INTO user_jpa_entity ("HEIGHT", "NEW_FLAG", "WEIGHT", "ID", "AUTH_ID", "AUTH_TYPE", "NICKNAME", "ROLE", "USERNAME")
VALUES (175, FALSE, 70, 1, 1001, 'KAKAO', 'john_doe', 'ROLE_USER', 'johndoe123');
insert into score_jpa_entity("ID","USER_ID","SCORE")
values(1,1,0);
INSERT INTO DAILY_RECORD_JPA_ENTITY ("EXERCISE_DATE", "ID", "USER_ID")
VALUES (DATE '2024-12-9', 1, 1);

INSERT INTO DAILY_RECORD_JPA_ENTITY ("EXERCISE_DATE", "ID", "USER_ID")
VALUES (DATE '2024-12-03', 2, 1);

INSERT INTO CATEGORY_JPA_ENTITY ("ID", "PART")
VALUES (1, 'BACK'), (2, 'CHEST'), (3, 'SHOULDER'), (4, 'LEG'), (5, 'FULL_BODY'),
       (6, 'ABS'), (7, 'ARM'), (8, 'HIP');

INSERT INTO EXERCISE_JPA_ENTITY ("CATEGORY_ID", "ID", "USER_ID", "DELETE_DELIMITER", "NAME")
VALUES (1, 201, 1, 'DELETE', 'Running'),
       (2, 202, 1, 'IN_USER', 'Cycling'),
       (3, 203, 1, 'DELETE', 'Swimming'),
       (4, 204, 1, 'IN_USER', 'Yoga'),
       (5, 205, 1, 'DELETE', 'Weightlifting'),
       (6, 206, 1, 'IN_USER', 'Hiking'),
       (7, 207, 1, 'DELETE', 'Boxing'),
       (8, 208, 1, 'IN_USER', 'Climbing'),
       (1, 209, 1, 'DELETE', 'Jogging'),
       (2, 210, 1, 'IN_USER', 'Rowing');

INSERT INTO DAILY_EXERCISE_RECORD_JPA_ENTITY ("EXERCISE_SET", "IS_PROGRESS", "VOLUME", "WEIGHT", "DAILY_RECORD_ID", "EXERCISE_ID", "ID")
VALUES (3, 1, 100, 50, 1, 201, 301),
       (4, 0, 120, 60, 2, 202, 302),
       (2, 1, 80, 40, 1, 203, 303),
       (5, 1, 150, 70, 2, 204, 304),
       (3, 0, 110, 55, 1, 205, 305),
       (4, 1, 130, 65, 2, 206, 306),
       (2, 0, 90, 45, 1, 207, 307),
       (6, 1, 200, 80, 2, 208, 308),
       (3, 1, 95, 47, 1, 209, 309),
       (4, 0, 140, 70, 2, 210, 310);
