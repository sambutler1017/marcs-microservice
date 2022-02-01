@NAME(userProfileFields)
	up.id, up.first_name, up.last_name, up.email, up.web_role_id,
	up.notifications_enabled, up.hire_date, up.insert_date

@NAME(storeFields)
	st.id AS 'store_id', st.name AS 'store_name', st.regional_id, st.manager_id

@NAME(userStatusFields)
	us.request_id, us.app_access, us.account_status, us.updated_user_id

@NAME(getStores)
	SELECT 
		@INCLUDE(storeFields)
	FROM
		stores st
	@WHERE(:regionalId:) 
		st.regional_id = :regionalId:
	@AND(:managerId:) 
		st.manager_id = :managerId:
	@AND(:id: || :name:)
		(
			@IF(:id:)
				st.id LIKE :id:
			@IF(:name:)
				@OR(:name:)
				st.name LIKE :name:
		)

@NAME(getStoreById)
	SELECT 
		@INCLUDE(storeFields)
	FROM
		stores st
	WHERE
		st.id = :id:

@NAME(getRegionalOfStore)
	SELECT 
		@INCLUDE(userProfileFields),
		@INCLUDE(storeFields),
		@INCLUDE(userStatusFields)
	FROM
		stores st
		LEFT JOIN user_profile up ON st.regional_id = up.id
		JOIN web_role wr ON up.web_role_id = wr.id
		JOIN user_status us ON up.id = us.user_id
	WHERE
		st.id = :storeId:

@NAME(getManagerOfStoreById)
	SELECT 
		@INCLUDE(userProfileFields),
		@INCLUDE(storeFields),
		@INCLUDE(userStatusFields)
	FROM
		stores st
		LEFT JOIN user_profile up ON st.manager_id = up.id
		JOIN web_role wr ON up.web_role_id = wr.id
		JOIN user_status us ON up.id = us.user_id
	WHERE
		st.id = :storeId: