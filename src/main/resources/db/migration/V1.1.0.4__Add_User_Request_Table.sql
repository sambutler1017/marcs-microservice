-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.0.4__Add_User_Request_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: MARCS-2: Create User Request Table 
-- Version: V1.1.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- MARCS-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_request (
  request_id  INT          unsigned NOT NULL AUTO_INCREMENT,
  user_id     INT          unsigned NOT NULL,
  type        VARCHAR(128)          NOT NULL,
  status      VARCHAR(45)           NOT NULL DEFAULT 'PENDING',
  notes       VARCHAR(4096)                  DEFAULT NULL,
  data        LONGTEXT              CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  insert_date DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (request_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ---------------------------------------------------------------------------------
-- MARCS-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION