SELECT * FROM tbsynorganizationjclass;

SELECT * FROM tborganization;

#同步organization操作
DELETE FROM tbsynorganizationjclass;

ALTER TABLE tbsynorganizationjclass AUTO_INCREMENT = 1;

INSERT INTO tbsynorganizationjclass (organizationCode) SELECT organizationCode FROM tborganization LIMIT 5;

#############
SELECT * FROM tbsynuserjclass;
SELECT * FROM tbusermain LIMIT 8,13;
#同步user操作
DELETE FROM tbsynuserjclass;
ALTER TABLE tbsynuserjclass AUTO_INCREMENT = 1;
INSERT INTO tbsynuserjclass (userId) SELECT id FROM tbusermain LIMIT 8,5;





