@NAME(insertUserPassword)
	INSERT INTO user_credentials(`user_id`,`password`,`salt`)
	VALUES (:userId:, :password:, :salt:)

@NAME(updateUserPassword)
    UPDATE user_credentials 
	SET 
    	password = :password:,
		salt = :salt:
	WHERE
		user_id = :id: