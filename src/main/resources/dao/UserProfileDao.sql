@NAME(getUserById)
	SELECT 
	    up.id,
	    up.first_name,
	    up.last_name,
	    up.email,
	    up.web_role_id,
		up.notifications_enabled,
	    up.hire_date,
		up.insert_date,
		us.app_access,
		us.account_status,
		st.id AS 'store_id',
		st.name AS 'store_name'
	FROM
	    user_profile up
			LEFT JOIN
		stores st ON up.store_id = st.id
			JOIN
		user_status us ON up.id = us.user_id
	WHERE up.id = :id:

@NAME(getUsers)
	SELECT 
		up.id,
		up.first_name,
		up.last_name,
		up.email,
		up.notifications_enabled,
		up.web_role_id,
		up.hire_date,
		up.insert_date,
		us.app_access,
		us.account_status,
		s.id AS 'store_id',
		s.name AS 'store_name'
	FROM
		user_profile up
			JOIN
		web_role wr ON up.web_role_id = wr.id
			LEFT JOIN
		stores s ON up.store_id = s.id
			JOIN
		user_status us ON up.id = us.user_id
	@WHERE(:id:)
		up.id = :id:
	@AND(:excludedUserIds:)
		up.id != :excludedUserIds:
	@AND(:regionalId:)
		s.regional_id = :regionalId:
	@AND(:email:)
		up.email = :email:
	@AND(:webRole:)
		wr.text_id = :webRole:
	@AND(:notificationsEnabled:)
		up.notifications_enabled = :notificationsEnabled:
	@AND(:accountStatus:)
		us.account_status = :accountStatus:
	@AND(:storeId: || :firstName: || :lastName:)
		(
			@IF(:storeId:)
				s.id LIKE :storeId:
			@IF(:firstName:)
				@OR(:storeId:)
				up.first_name LIKE :firstName:
			@IF(:lastName:)
				@OR(:storeId: || :firstName:)
				up.last_name LIKE :lastName:
			@IF(:storeName:)
				@OR(:storeId: || :firstName: || :lastName:)
				s.name LIKE :storeName:
		)
	ORDER BY s.name

@NAME(getApplications)
	SELECT 
		a.id AS 'app_id',
		a.app_name AS 'app_name',
		wraa.access AS 'access',
		a.enabled AS 'enabled'
	FROM
		user_profile up
			JOIN
		web_role_app_access wraa ON up.web_role_id = wraa.web_role_id
			JOIN
		application a ON wraa.app_id = a.id
    @WHERE(:id:)
        up.id = :id:
	ORDER BY a.id

@NAME(getRegionalOfStore)
	SELECT 
		up.id,
		up.first_name,
		up.last_name,
		up.email,
		wr.id AS 'web_role_id',
		NULL AS 'store_id',
		NULL AS 'store_name',
		us.app_access,
		up.notifications_enabled,
		us.account_status,
		up.hire_date,
		up.insert_date
	FROM
		stores st
			RIGHT JOIN
		user_profile up ON st.manager_id = up.id
			JOIN
		web_role wr ON up.web_role_id = wr.id
			LEFT JOIN
		stores s ON up.store_id = s.id
			JOIN
		user_status us ON up.id = us.user_id
	WHERE
		st.id = :storeId:

@NAME(getManagerOfStoreById)
	SELECT 
		up.id,
		up.first_name,
		up.last_name,
		up.email,
		wr.id AS 'web_role_id',
		NULL AS 'store_id',
		NULL AS 'store_name',
		us.app_access,
		up.notifications_enabled,
		us.account_status,
		up.hire_date,
		up.insert_date
	FROM
		stores st
			RIGHT JOIN
		user_profile up ON st.manager_id = up.id
			JOIN
		web_role wr ON up.web_role_id = wr.id
			LEFT JOIN
		stores s ON up.store_id = s.id
			JOIN
		user_status us ON up.id = us.user_id
	WHERE
		st.id = :storeId:

@NAME(insertUser)
	@IF(:storeId: && :email:)
		INSERT INTO user_profile (`first_name`,`last_name`,`email`,`store_id`,`web_role_id`,`hire_date`)
		VALUES (:firstName:, :lastName:, :email:, :storeId:, :webRoleId:, :hireDate:)

	@IF(:storeId: && !:email:)
		INSERT INTO user_profile (`first_name`,`last_name`,`store_id`,`web_role_id`,`hire_date`)
		VALUES (:firstName:, :lastName:, :storeId:, :webRoleId:, :hireDate:)

	@IF(!:storeId: && :email:)
		INSERT INTO user_profile (`first_name`,`last_name`,`email`,`web_role_id`,`hire_date`)
		VALUES (:firstName:, :lastName:, :email:, :webRoleId:, :hireDate:)

	@IF(!:storeId: && !:email:)
		INSERT INTO user_profile (`first_name`,`last_name`,`web_role_id`,`hire_date`)
		VALUES (:firstName:, :lastName:, :webRoleId:, :hireDate:)

@NAME(deleteUser)
	DELETE FROM user_profile
	WHERE
    	id = :id:

@NAME(updateUserProfile)
    UPDATE user_profile 
	SET
		@IF(:email:)
			email = :email:,
		@IF(:storeId:)
			store_id = :storeId:,	
		first_name = :firstName:,
    	last_name = :lastName:,
		web_role_id = :webRole:,
		notifications_enabled = :notificationsEnabled:,
		hire_date = :hireDate:
	WHERE
		id = :id:

@NAME(updateUserRole)
	UPDATE user_profile
	SET
		web_role_id = :roleId:
	WHERE
		id = :id: