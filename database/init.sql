
create database if not exists testt;

use testt;

CREATE TABLE `klient` (
  `idklient` int NOT NULL AUTO_INCREMENT,
  `fio` varchar(45) DEFAULT NULL,
  `gorod` varchar(45) DEFAULT NULL,
  `telefon` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `userrole` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idklient`)
);



INSERT INTO `klient` VALUES (1,'Крутов А.О.','Москва','43279842','log','pas','1'),(2,'Мехов К.А.','Нижний Новгород','2342424','qwerty','qwerty','2'),(3,'Андреев М.О.','Волгоград','3243242','test','test','1'),(4,'mikl','Москва','32732','mikl','mikl','1'),(5,'mikl','Москва','32732','mikl','mikl','1'),(6,'mikl','Москва','32732','mikl','mikl','1'),(7,'test','test','324234','test','test','1'),(8,'vasilyu','ncsjd','42134','vaska','1111','1'),(9,'koltest','fadsf','213213','1','1','1'),(10,'adv','ads','4321','2','2','1'),(11,'adv','ads','4321','2','2','1'),(12,'adv','ads','4321','2','2','1'),(13,'adklsf','zdsv','342342','3','3','1'),(14,'dsf,ads','asdf','214312','4','4','1'),(15,'валоыв','dskmfdf','93847298','10','10','1'),(16,'валоыв','dskmfdf','93847298','10','10','1'),(17,'валоыв','dskmfdf','93847298','10','10','1'),(18,'lkzc','dlksa','312','20','20','1');



CREATE TABLE `sotrudniki` (
  `idsotrudniki` int NOT NULL AUTO_INCREMENT,
  `fio` varchar(45) DEFAULT NULL,
  `gorod` varchar(45) DEFAULT NULL,
  `pasport` int DEFAULT NULL,
  `data_rozjden` date DEFAULT NULL,
  `telefon` varchar(45) DEFAULT NULL,
  `doljnost` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idsotrudniki`)
);


INSERT INTO `sotrudniki` VALUES (1,'Ломов М.К.','Волгоград',328947,'1998-12-11','23213','доставщик'),(2,'Никитенко М.Е.','Нижний Новгород',327894,'2023-11-24','213123','доставщик'),(3,'Васильчук К.О.','Москва',327846,'2023-11-23','12312','доставщик'),(4,'Вернов А.Н.','Новосибирск',234682,'2023-11-24','123213','доставщик');

CREATE TABLE `towars` (
  `idtowars` int NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(45) DEFAULT NULL,
  `marka` varchar(45) DEFAULT NULL,
  `cena` int DEFAULT NULL,
  `kod_tipa` int DEFAULT NULL,
  `photo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtowars`)
) ;


INSERT INTO `towars` VALUES (1,'хлеб','Хлебный Дом',25,1111,'1.jpg'),(2,'крупа гречневая','Щебекенская',80,1112,'2.jpg'),(3,'колбаса','вязонка',200,1113,'3.jpg'),(4,'яблоки','Гренни Смит',68,1114,'4.jpg');





CREATE TABLE `zakazi` (
  `idzakazi` int NOT NULL AUTO_INCREMENT,
  `adres_pokupatela` varchar(45) DEFAULT NULL,
  `data_razmesheniya` date DEFAULT NULL,
  `klient_idklient` int NOT NULL,
  `sotrudniki_idsotrudniki` int NOT NULL,
  PRIMARY KEY (`idzakazi`),
  KEY `fk_zakazi_klient1_idx` (`klient_idklient`),
  KEY `fk_zakazi_sotrudniki1_idx` (`sotrudniki_idsotrudniki`),
  CONSTRAINT `fk_zakazi_klient1` FOREIGN KEY (`klient_idklient`) REFERENCES `klient` (`idklient`),
  CONSTRAINT `fk_zakazi_sotrudniki1` FOREIGN KEY (`sotrudniki_idsotrudniki`) REFERENCES `sotrudniki` (`idsotrudniki`)
);



INSERT INTO `zakazi` VALUES (1,'пр. Гагарина','2023-11-21',1,2),(2,'hhjjf','2023-11-21',1,2),(3,'test','2023-11-21',1,4),(4,'ijoijoij','2023-11-21',1,2),(7,'testtest',NULL,1,2),(8,'testtesttest',NULL,1,2),(9,'ds',NULL,1,2),(10,'Гагаринский',NULL,7,4),(11,'prospect',NULL,8,3),(12,'ghfdj',NULL,9,2),(13,'.\';',NULL,1,2),(14,'adfs',NULL,12,2),(15,'qdw',NULL,12,2),(16,'aefs',NULL,13,2),(17,'kop',NULL,1,4),(18,'igvy',NULL,1,2),(19,'sdf',NULL,14,2),(20,'gagar',NULL,14,4),(22,'esf',NULL,14,2),(23,'esf',NULL,14,2),(24,'цуыва',NULL,14,2),(25,'ацф',NULL,14,3),(26,'dsnfkj',NULL,14,3),(27,'2134',NULL,14,2),(28,'dfs',NULL,13,3),(29,'djai',NULL,1,2),(30,'dfafdsa',NULL,1,4),(31,'yghyhhh',NULL,1,3),(32,'cwdkl',NULL,1,4),(33,'test',NULL,14,3),(34,'kdonmzcv ',NULL,13,2),(35,'dfds',NULL,9,2),(36,'fmosd',NULL,17,3),(45,'klmdcs',NULL,18,4),(46,'klmdcs',NULL,18,4),(47,'лдьсяч',NULL,17,2),(48,'длфавыь',NULL,1,2);



CREATE TABLE `towars_has_zakazi` (
  `towars_idtowars` int NOT NULL,
  `zakazi_idzakazi` int NOT NULL,
  `cena` int DEFAULT NULL,
  `kolichestvo` int DEFAULT NULL,
  PRIMARY KEY (`towars_idtowars`,`zakazi_idzakazi`),
  KEY `fk_towars_has_zakazi_zakazi1_idx` (`zakazi_idzakazi`),
  KEY `fk_towars_has_zakazi_towars_idx` (`towars_idtowars`),
  CONSTRAINT `fk_towars_has_zakazi_towars` FOREIGN KEY (`towars_idtowars`) REFERENCES `towars` (`idtowars`),
  CONSTRAINT `fk_towars_has_zakazi_zakazi1` FOREIGN KEY (`zakazi_idzakazi`) REFERENCES `zakazi` (`idzakazi`)
);




INSERT INTO `towars_has_zakazi` VALUES (1,29,25,1),(1,31,25,2),(1,33,25,2),(1,46,25,1),(2,32,80,1),(2,34,80,2),(3,36,200,1),(3,46,200,2),(4,30,68,2);



