-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.1.0.2__Add_User_Vacations_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.1.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE user_vacations (
  id          INT           unsigned NOT NULL AUTO_INCREMENT,
  user_id     INT           unsigned NOT NULL,
  start_date  DATETIME               NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  end_date    DATETIME               NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  status      varchar(45)            NOT NULL DEFAULT 'PENDING',
  notes       varchar(4096)                   DEFAULT NULL,
  insert_date DATETIME               NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION