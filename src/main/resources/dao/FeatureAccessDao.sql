@NAME(getFeatureAccess)
    SELECT 
    	fa.id,
	    fa.feature_application_text,
        fa.feature_name_text,
        CONCAT('', CASE WHEN wrfa.create THEN 'c' ELSE '' END,
                CASE WHEN wrfa.read      THEN 'r' ELSE '' END,
                CASE WHEN wrfa.update    THEN 'u' ELSE '' END,
                CASE WHEN wrfa.delete    THEN 'd' ELSE '' END
        ) AS 'access'
    FROM
        marcs_db.feature_access fa
            JOIN
        web_role_feature_access wrfa ON fa.id = wrfa.feature_id
    @WHERE(:webRole:)
        wrfa.web_role_id = :webRole: