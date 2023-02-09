-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.2__Add_Web_Role_Feature_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: MARCS-2: Create Web Role Feature Access Table 
-- Version: V1.2.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- MARCS-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE web_role_feature_access (
  web_feature_id INT        unsigned NOT NULL AUTO_INCREMENT,
  feature_id     INT        unsigned NOT NULL,
  web_role_id    INT        unsigned NOT NULL,
  `create`       TINYINT(4) unsigned NOT NULL DEFAULT 0,
  `read`         TINYINT(4) unsigned NOT NULL DEFAULT 0,
  `update`       TINYINT(4) unsigned NOT NULL DEFAULT 0,
  `delete`       TINYINT(4) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (web_feature_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- MARCS-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION