CREATE OR REPLACE TRIGGER BRG_VMBG_KLANTEN_ARNG_10
BEFORE UPDATE
ON KLANTEN
FOR EACH ROW
BEGIN
	IF (NOT (:new.TITEL BETWEEN 1 AND 6))
	THEN RAISE_APPLICATION_ERROR(-20000, 'BRG_VMBG_KLANTEN_ARNG_10 was triggered');
	END IF
END BRG_VMBG_KLANTEN_ARNG_10