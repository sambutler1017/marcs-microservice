-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.3.0.0__Add_Block_Out_Dates_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.3.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE block_out_dates (
  id             INT      unsigned NOT NULL AUTO_INCREMENT,
  start_date     DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  end_date       DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  insert_user_id INT      unsigned          DEFAULT NULL,
  insert_date    DATETIME                   DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION