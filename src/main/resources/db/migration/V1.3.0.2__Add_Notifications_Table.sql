-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.3.0.2__Add_Notifications_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.3.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

CREATE TABLE notifications (
  id                INT          unsigned NOT NULL AUTO_INCREMENT,
  notification_type VARCHAR(128)                   DEFAULT NULL,
  receiver_id       INT          unsigned NOT NULL,
  read_flag         TINYINT(4)   unsigned NOT NULL DEFAULT 0,
  link_id           INT          unsigned NOT NULL,
  insert_date       DATETIME              NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION