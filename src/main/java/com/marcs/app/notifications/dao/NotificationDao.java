package com.marcs.app.notifications.dao;

import static com.marcs.app.notifications.mapper.NotificationMapper.NOTIFICATION_MAPPER;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.sql.SqlParamBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Class that handles all the dao calls to the database for requests
 * 
 * @author Sam Butler
 * @since December 21, 2021
 */
@Repository
public class NotificationDao extends BaseDao {

    @Autowired
    public NotificationDao(DataSource source) {
        super(source);
    }

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
        SqlParamBuilder builder = SqlParamBuilder.with(request).withParam("notificationId", request.getId())
                .withParamTextEnumCollection("type", request.getType()).withParam("receiverId", request.getReceiverId())
                .withParam("read", request.getRead());
        MapSqlParameterSource params = builder.build();

        return getPage(getSql("getNotifications", params), params, NOTIFICATION_MAPPER);
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
        SqlParamBuilder builder = SqlParamBuilder.with().withParam("read", true).withParam("id", id);
        MapSqlParameterSource params = builder.build();
        update(getSql("markNotificationRead"), params);
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParamBuilder builder = SqlParamBuilder.with().withParam("type", n.getType())
                .withParam("receiverId", n.getReceiverId()).withParam("linkId", n.getLinkId());
        MapSqlParameterSource params = builder.build();
        post(getSql("createNotification"), params, keyHolder);

        n.setId(keyHolder.getKey().intValue());
        n.setInsertDate(LocalDate.now());
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
        delete(getSql("deleteNotification"), parameterSource("id", id));
    }
}
