package com.marcs.app.notifications.dao;

import static com.marcs.app.notifications.mapper.NotificationMapper.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.app.notifications.client.domain.request.NotificationGetRequest;
import com.marcs.common.abstracts.BaseDao;
import com.marcs.common.date.TimeZoneUtil;
import com.marcs.sql.SqlParamBuilder;

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
        MapSqlParameterSource params = SqlParamBuilder.with(request).withParam(ID, request.getId())
                .withParam(RECEIVER_ID, request.getReceiverId()).withParam(READ_FLAG, request.getRead())
                .withParamTextEnumCollection(NOTIFICATION_TYPE, request.getType()).build();

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
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(READ_FLAG, true).withParam(ID, id).build();
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
        MapSqlParameterSource params = SqlParamBuilder.with().withParam(NOTIFICATION_TYPE, n.getType())
                .withParam(RECEIVER_ID, n.getReceiverId()).withParam(LINK_ID, n.getLinkId()).build();

        post(getSql("createNotification"), params, keyHolder);

        n.setId(keyHolder.getKey().intValue());
        n.setInsertDate(LocalDateTime.now(TimeZoneUtil.SYSTEM_ZONE));
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
        delete(getSql("deleteNotification"), parameterSource(ID, id));
    }
}
