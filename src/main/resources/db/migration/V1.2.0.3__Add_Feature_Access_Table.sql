-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.2.0.3__Add_Feature_Access_Table.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Issue: MARCS-2: Create Web Role Table 
-- Version: V1.2.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- MARCS-2: START
-- ---------------------------------------------------------------------------------

CREATE TABLE feature_access (
  id                       INT      unsigned NOT NULL AUTO_INCREMENT,
  feature_application_text VARCHAR(128)          NOT NULL,
  feature_name_text        VARCHAR(128)          NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------------
-- MARCS-2: END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION