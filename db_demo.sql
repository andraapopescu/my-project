(`id`,`address`,`birthday`,`email`,`employment_date`,`first_name`,`last_name`,`phone`,`salary`)

EMPLOYEE

INSERT INTO `demo`.`employee` VALUES
(1,'MSD AP Company, 123 asd Street','1980-07-20','admin@hrmsd.ro','1980-07-21','admin','admin','112331',1111);
INSERT INTO `demo`.`employee` VALUES
(2,'12 Vasile Alecsandri Street, 2/20','2010-01-20','lupu.larisa@hrmsd.ro','1990-10-14','Lupu','Larisa-Maria','07227410',7500);
INSERT INTO `demo`.`employee` VALUES
(3,'4C Magheru Street, 1/36','1994-05-25','manolache.dianora@hrmsd.ro','2015-07-01','Manolache','Dianora','07896521',5900);
INSERT INTO `demo`.`employee` VALUES
(4,'43B Vasile Milea Street, 4/02','1989-10-02','tudor.mihai@hrmsd.ro','2017-01-01','Tudor','Mihai','07213698',6800);
INSERT INTO `demo`.`employee` VALUES
(5,'9 Valea Cascadelor Street, A/25','1975-11-30','tanase.alin@hrmsd.ro','2015-07-01','Tanase','Alin','07412369',8000);

SKILL

INSERT INTO `demo`.`skill` VALUES
(1, 'java');
INSERT INTO `demo`.`skill` VALUES
(2, 'cassandra');
INSERT INTO `demo`.`skill` VALUES
(3, 'react js');
INSERT INTO `demo`.`skill` VALUES
(4, 'sql');
INSERT INTO `demo`.`skill` VALUES
(5, 'hystrix');

EMPLOYEE_SKILL
(`id`,`level`,`id_employee`,`id_skill`)

