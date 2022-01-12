package com.marcs.app.notifications.dao;

import static com.marcs.app.notifications.mapper.NotificationMapper.NOTIFICATION_MAPPER;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.common.abstracts.AbstractSqlDao;

import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for requests
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
@Repository
public class NotificationDao extends AbstractSqlDao {

    /**
     * This will get a list of notifications that the user has. The request will be
     * refined down to only notifications that are to them specifically, except for
     * ADMIN and SITE_ADMIN roles since they can approve any request (New User or
     * Vacations)
     * 
     * @param request The request with how to filter the request.
     * @return List of {@link Notification} objects.
     * @throws Exception If no data is returned
     */
    public List<Notification> getNotifications(NotificationGetRequest request) throws Exception {
        return sqlClient.getPage(getSql("getNotifications"), params("notificationId", request.getId())
                .addValue("type", request.getType()).addValue("receiverId", request.getReceiverId())
                .addValue("read", request.getRead()),
                NOTIFICATION_MAPPER);
    }

    /**
     * This will mark the passed in notification id as read. If the notification id
     * does not exist then it will throw an exception.
     * 
     * @param id The id to mark as read.
     * @return {@link Notification} object.
     * @throws Exception
     */
    public void markNotificationRead(int id) throws Exception {
        sqlClient.update(getSql("markNotificationRead"), params("read", true).addValue("id", id));
    }

    /**
     * This will create a new notification that needs inserted. It will contain the
     * type, link id, and who is receiving the notification.
     * 
     * @param n The notification that needs inserted.
     * @return {@link Notification} That is created.
     * @throws Exception
     */
    public Notification createNotification(Notification n) throws Exception {
        Optional<Integer> createId = sqlClient.post(getSql("createNotification"), params("type", n.getType().toString())
                .addValue("receiverId", n.getReceiverId()).addValue("linkId", n.getLinkId()));

        if (createId.isPresent()) {
            n.setId(createId.get());
            n.setInsertDate(LocalDate.now());
        } else {
            throw new Exception("Could not create notification!");
        }

        return n;
    }

    /**
     * This will delete the notification for the given id. If the id does not exist
     * to be deleted then it will return an exception.
     * 
     * @param id The id to be deleted
     * @throws Exception
     */
    public void deleteNotification(int id) throws Exception {
        sqlClient.delete(getSql("deleteNotification"), params("id", id));
    }
}
