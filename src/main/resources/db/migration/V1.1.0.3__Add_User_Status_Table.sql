-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.0.3__Add_User_Status_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: MARCS-2: Create User Status Table 
-- Version: V1.1.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- MARCS-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_status (
  request_id      INT          unsigned NOT NULL AUTO_INCREMENT,
  user_id         INT          unsigned NOT NULL,
  account_status  VARCHAR(128)          NOT NULL DEFAULT 'PENDING',
  app_access      TINYINT(4)   unsigned NOT NULL DEFAULT 0,
  updated_user_id INT          unsigned          DEFAULT NULL,
  PRIMARY KEY (request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ---------------------------------------------------------------------------------
-- MARCS-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION