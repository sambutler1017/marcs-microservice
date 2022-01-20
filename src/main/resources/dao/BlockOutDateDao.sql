@NAME(getBlockOutDates)
    SELECT 
        id, start_date, end_date, insert_user_id, insert_date
    FROM
        block_out_dates
    @WHERE(:id:)
        id = :id:
    @AND(:insertUserId:)
        insert_user_id = :insertUserId:

@NAME(insertBlockOutDate)
    INSERT INTO block_out_dates (start_date, end_date, insert_user_id)
    VALUES(:startDate:, :endDate:, :insertUserId:)

@NAME(deleteBlockOutDate)
    DELETE FROM block_out_dates 
    WHERE
        id = :id:

