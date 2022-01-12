@NAME(getNotifications)
    SELECT 
        id,
        notification_type,
        receiver_id,
        read_flag,
        link_id,
        insert_date
    FROM
        notifications
    @WHERE(:notificationId:)
        id = :notificationId:
    @AND(:receiverId:)
        receiver_id = :receiverId:
    @AND(:read:)
        read_flag = :read:
    @AND(:type:)
        notification_type = :type:
    ORDER BY read_flag, insert_date DESC

@NAME(markNotificationRead)
    UPDATE notifications SET read_flag = :read:
    WHERE id = :id:

@NAME(createNotification)
    INSERT INTO notifications (`notification_type`,`receiver_id`,`link_id`)
    VALUES (:type:, :receiverId:, :linkId:)

@NAME(deleteNotification)
    DELETE FROM notifications
    WHERE id = :id: