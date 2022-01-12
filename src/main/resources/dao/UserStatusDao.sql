@NAME(getUserStatusById)
    SELECT *
    FROM
        user_status
    WHERE user_id = :userId:

@NAME(insertUserStatus)
    @IF(:appAccess: && :updatedUserId:)
        INSERT INTO user_status (`user_id`,`account_status`,`app_access`, `updated_user_id`)
        VALUES(:userId:, :accountStatus:, :appAccess:, :updatedUserId:)

    @IF(!:appAccess: && :updatedUserId:)
        INSERT INTO user_status (`user_id`,`account_status`, `updated_user_id`)
        VALUES(:userId:, :accountStatus:, :updatedUserId:)

    @IF(:appAccess: && !:updatedUserId:)
        INSERT INTO user_status (`user_id`,`account_status`,`app_access`)
        VALUES(:userId:, :accountStatus:, :appAccess:)

    @IF(!:appAccess: && !:updatedUserId:)
        INSERT INTO user_status (`user_id`,`account_status`)
        VALUES(:userId:, :accountStatus:)

@NAME(updateUserStatus)
    UPDATE user_status
    SET
        @IF(:updatedUserId:)
            updated_user_id = :updatedUserId:,
        account_status = :accountStatus:,
        app_access = :appAccess:
    WHERE user_id = :userId:
