-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Script: V1.4.0.0__Add_Table_Constraints.sql
-- Author: Sam Butler
-- Date: April 24, 2022
-- Version: V1.4.0
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

-- ---------------------------------------------------------------------------------
-- START
-- ---------------------------------------------------------------------------------

-- ------------------------------------------------------------
-- Indexes
-- ------------------------------------------------------------


-- User Profile Table
CREATE UNIQUE INDEX user_profile_AK1 ON user_profile(email);
CREATE INDEX user_profile_IDX1 ON user_profile(web_role_id);
CREATE INDEX user_profile_IDX2 ON user_profile(store_id);

-- User Vacations Table
CREATE INDEX user_vacations_IDX1 ON user_vacations(user_id);

-- User Status Table
CREATE INDEX user_status_IDX1 ON user_status(user_id);
CREATE INDEX user_status_IDX2 ON user_status(updated_user_id);

-- User Request Table
CREATE INDEX user_request_IDX1 ON user_request(user_id);

-- Web Role App Access Table
CREATE INDEX web_role_app_access_IDX1 ON web_role_app_access(web_role_id);
CREATE INDEX web_role_app_access_IDX2 ON web_role_app_access(app_id);

-- Web Role Feature Access Table
CREATE INDEX web_role_feature_access_IDX1 ON web_role_feature_access(web_role_id);
CREATE INDEX web_role_feature_access_IDX2 ON web_role_feature_access(feature_id);

-- Block Out Date Table
CREATE INDEX block_out_dates_IDX1 ON block_out_dates(insert_user_id);

-- Notifications Table
CREATE INDEX notifications_IDX1 ON notifications(receiver_id);

-- Stores Table
CREATE UNIQUE INDEX stores_AK1 ON stores(regional_manager_id,manager_id);
CREATE INDEX stores_IDX1 ON stores(regional_manager_id);
CREATE INDEX stores_IDX2 ON stores(manager_id);

-- Schedules Table
CREATE INDEX schedules_IDX1 ON schedules(store_id);

-- User Schedule Table
CREATE INDEX user_schedule_IDX1 ON user_schedule(schedule_id);
CREATE INDEX user_schedule_IDX2 ON user_schedule(user_id);

-- User Availability Table
CREATE INDEX user_availability_IDX2 ON user_availability(user_id);

-- ------------------------------------------------------------
-- Foreign Keys
-- ------------------------------------------------------------


-- User Profile Table
ALTER TABLE user_profile 
  ADD CONSTRAINT stores__user_profile__FK1 FOREIGN KEY(store_id) REFERENCES stores(id)   
    ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE user_profile 
  ADD CONSTRAINT web_role__user_profile__FK2 FOREIGN KEY(web_role_id) REFERENCES web_role(id) 
    ON UPDATE CASCADE;

-- User Credentials Table
ALTER TABLE user_credentials 
  ADD CONSTRAINT user_profile__user_credentials__FK1 FOREIGN KEY(user_id) REFERENCES user_profile(id)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- User Vacations Table
ALTER TABLE user_vacations
  ADD CONSTRAINT user_profile__user_vacations__FK1 FOREIGN KEY(user_id) REFERENCES user_profile(id)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- User Status Table
ALTER TABLE user_status 
  ADD CONSTRAINT user_profile__user_status__FK1 FOREIGN KEY(user_id) REFERENCES user_profile(id)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE user_status
  ADD CONSTRAINT user_profile__user_status__FK2 FOREIGN KEY(updated_user_id) REFERENCES user_profile(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

-- User Request Table
ALTER TABLE user_request 
  ADD CONSTRAINT user_profile__user_request__FK1 FOREIGN KEY(user_id) REFERENCES user_profile(id)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- Web Role App Access Table
ALTER TABLE web_role_app_access 
  ADD CONSTRAINT user_profile__web_role_app_access__FK1 FOREIGN KEY(web_role_id) REFERENCES web_role(id)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE web_role_app_access 
  ADD CONSTRAINT user_profile__web_role_app_access__FK2 FOREIGN KEY(app_id) REFERENCES application(id)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- Web Role Feature Access Table
ALTER TABLE web_role_feature_access 
  ADD CONSTRAINT user_profile__web_role_feature_access__FK1 FOREIGN KEY(web_role_id) REFERENCES web_role(id)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE web_role_feature_access 
  ADD CONSTRAINT user_profile__web_role_feature_access__FK2 FOREIGN KEY(feature_id) REFERENCES feature_access(id)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- Block Out Dates Table
ALTER TABLE block_out_dates 
  ADD CONSTRAINT user_profile__block_out_dates__FK1 FOREIGN KEY(insert_user_id) REFERENCES user_profile(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

-- Stores Table
ALTER TABLE stores
  ADD CONSTRAINT user_profile__stores__FK1 FOREIGN KEY(regional_manager_id) REFERENCES user_profile(id) 
    ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE stores
  ADD CONSTRAINT user_profile__stores__FK2 FOREIGN KEY(manager_id) REFERENCES user_profile(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

-- Schedules Table
ALTER TABLE schedules
  ADD CONSTRAINT stores__schedules__FK1 FOREIGN KEY (store_id) REFERENCES stores (id) 
    ON DELETE CASCADE ON UPDATE CASCADE;

-- User Schedule Table
ALTER TABLE user_schedule
  ADD CONSTRAINT user_profile__user_schedule__FK1 FOREIGN KEY(user_id) REFERENCES user_profile(id) 
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE user_schedule
  ADD CONSTRAINT schedules__user_schedule__FK2 FOREIGN KEY(schedule_id) REFERENCES schedules(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

-- User Availability Table
ALTER TABLE user_availability
  ADD CONSTRAINT user_profile__user_availability__FK1 FOREIGN KEY (user_id) REFERENCES user_profile(id)  
    ON DELETE CASCADE ON UPDATE CASCADE;
-- ---------------------------------------------------------------------------------
-- END
-- ---------------------------------------------------------------------------------

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- END OF SCRIPT VERSION