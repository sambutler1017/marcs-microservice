@NAME(authenticateUser)
	SELECT 
		up.id,
		up.first_name,
		up.last_name,
		up.email,
		up.web_role_id,
		up.managers_only,
		up.notifications_enabled,
		up.hire_date,
		up.insert_date,
		us.app_access,
		us.account_status,
		st.id AS 'store_id',
		st.name AS 'store_name'
	FROM
		user_profile up
			JOIN
		user_credentials uc ON up.id = uc.user_id
			LEFT JOIN
		stores st ON up.store_id = st.id
			JOIN
		user_status us ON up.id = us.user_id
	WHERE
		up.email = :email:
	AND
		uc.password = :password:
	AND
		us.account_status = 'APPROVED'

@NAME(getUserAuthenticationSalt)
	SELECT
		uc.password,
		uc.salt
	FROM
		user_profile up
			JOIN
		user_credentials uc ON up.id = uc.user_id
	WHERE
		up.email = :email: