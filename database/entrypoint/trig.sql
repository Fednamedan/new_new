DELIMITER ;;
CREATE DEFINER=`test`@`%` TRIGGER `update_birthdate` AFTER INSERT ON `zakazi` FOR EACH ROW BEGIN
 UPDATE sotrudniki
 SET data_rozjden = CURDATE()
 WHERE idsotrudniki = NEW.sotrudniki_idsotrudniki;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`test`@`%` PROCEDURE `GetMaxTowars`()
BEGIN
   SELECT towars.nazvanie
   FROM towars
   INNER JOIN towars_has_zakazi ON towars.idtowars = towars_has_zakazi.towars_idtowars
   WHERE towars_has_zakazi.zakazi_idzakazi = (
       SELECT MAX(zakazi_idzakazi)
       FROM towars_has_zakazi
   );
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`test`@`%` PROCEDURE `removeOrder`(IN productId INT, IN orderId INT)
BEGIN
  DECLARE quantity INT;
  SELECT kolichestvo INTO quantity FROM towars_has_zakazi WHERE towars_idtowars = productId AND zakazi_idzakazi = orderId;
  IF quantity > 1 THEN
     UPDATE towars_has_zakazi SET kolichestvo = kolichestvo - 1 WHERE towars_idtowars = productId AND zakazi_idzakazi = orderId;
  ELSEIF quantity = 1 THEN
     DELETE FROM towars_has_zakazi WHERE towars_idtowars = productId AND zakazi_idzakazi = orderId;
  END IF;
END ;;
DELIMITER ;