-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.0.0__Add_User_Profile_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_profile (
  id                    INT          unsigned NOT NULL AUTO_INCREMENT,
  first_name            VARCHAR(128)          NOT NULL,
  last_name             VARCHAR(128)          NOT NULL DEFAULT ' ',
  email                 VARCHAR(128)                   DEFAULT NULL,
  store_id              varchar(32)                    DEFAULT NULL,
  web_role_id           INT          unsigned NOT NULL DEFAULT 1,
  email_reports_enabled TINYINT(4)   unsigned NOT NULL DEFAULT 0,
  last_login_date       DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  hire_date             DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  insert_date           DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION