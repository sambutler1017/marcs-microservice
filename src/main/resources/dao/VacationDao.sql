@NAME(getVacations)
	SELECT 
		v.id,
		v.user_id,
		v.start_date,
		v.end_date,
		v.insert_date,
		v.status,
		v.notes,
		st.id AS 'store_id',
		st.regional_id,
		CONCAT(up.first_name, ' ', up.last_name) AS name
	FROM
		user_vacations v
			JOIN
		user_profile up ON v.user_id = up.id
			LEFT JOIN
		stores st ON up.store_id = st.id
	@AND(:userId: || :regionalId: || :status: || :startDate:)
		(
			@IF(:userId:)
				v.user_id LIKE :userId:
			@IF(:regionalId:)
				@OR(:userId:)
				st.regional_id = :regionalId:
			@IF(:status:)
				@OR(:userId: || :regionalId:)
				v.status LIKE :status:
			@IF(:startDate: && true)
				@AND(:userId: || :regionalId: || :status:)
				start_date >= (DATE_ADD(CURDATE(), INTERVAL 5 DAY) - WEEKDAY(CURDATE())) AND start_date <= DATE_ADD((DATE_ADD(CURDATE(), INTERVAL 5 DAY) - WEEKDAY(CURDATE())), INTERVAL 6 DAY)
		)
	ORDER BY start_date ASC

@NAME(getUserVacations)
	SELECT 
		v.id,
		v.user_id,
		v.start_date,
		v.end_date,
		v.insert_date,
		v.status,
		v.notes,
		st.id AS 'store_id',
		st.regional_id,
		CONCAT(up.first_name, ' ', up.last_name) AS name
	FROM
		user_vacations v
			JOIN
		user_profile up ON v.user_id = up.id
			LEFT JOIN
		stores st ON up.store_id = st.id
	@WHERE(:id:)
		v.id = :id:
	@AND(:userId:)
		v.user_id = :userId:
	ORDER BY start_date ASC

@NAME(deleteVacations)
	DELETE FROM user_vacations 
	@WHERE(:id:)
		id = :id:
	@AND(:userId:)
		user_id = :userId:

@NAME(insertVacation)
	@IF(:status:)
		INSERT INTO user_vacations (`user_id`,`start_date`,`end_date`, `status`)
		VALUES (:userId:, :startDate:, :endDate:, :status:)
	@IF(!:status:)
		INSERT INTO user_vacations (`user_id`,`start_date`,`end_date`)
		VALUES (:userId:, :startDate:, :endDate:)

@NAME(updateVacationInfoById)
	UPDATE user_vacations
	SET
		@IF(:status: && !:notes:)
			status    = :status:
		@IF(!:status: && :notes:)
			notes     = :notes:
		@IF(:status: && :notes:)
			status    = :status:,
			notes     = :notes:
	WHERE id = :id:

@NAME(updateVacationDatesById)
	UPDATE user_vacations
	SET
		start_date = :startDate:,
		end_date = :endDate:
	WHERE id = :id:

