@NAME(getStores)
	SELECT 
		*
	FROM
		stores
	@WHERE(:regionalId:) 
		regional_id = :regionalId:
	@AND(:managerId:) 
		manager_id = :managerId:
	@AND(:id: || :name:)
		(
			@IF(:id:)
				id LIKE :id:
			@IF(:name:)
				@OR(:name:)
				name LIKE :name:
		)

@NAME(getStoreById)
	SELECT 
		*
	FROM
		stores
	WHERE
		id = :id:;