-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.3.0.6__Add_User_Availability_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.3.0.6
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_availability (
  id           INT         unsigned NOT NULL AUTO_INCREMENT,
  user_id      INT         unsigned NOT NULL,
  week_day     VARCHAR(45)          NOT NULL,
  start_time   DATETIME             NOT NULL,
  end_time     DATETIME             NOT NULL,
  insert_date  DATETIME             NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION