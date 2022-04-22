package com.marcs.app.notifications.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.common.abstracts.AbstractMapper;
import com.marcs.common.enums.NotificationType;

import org.springframework.stereotype.Service;

/**
 * Mapper class to map a Store Object {@link Notification}
 * 
 * @author Sam Butler
 * @since December 25, 2021
 */
@Service
public class NotificationMapper extends AbstractMapper<Notification> {
    public static NotificationMapper NOTIFICATION_MAPPER = new NotificationMapper();

    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();

        notification.setId(rs.getInt(ID));
        notification.setType(NotificationType.valueOf(rs.getString(NOTIFICATION_TYPE)));
        notification.setLinkId(rs.getInt(LINK_ID));
        notification.setInsertDate(rs.getTimestamp(INSERT_DATE).toLocalDateTime());
        notification.setReceiverId(rs.getInt(RECEIVER_ID));
        notification.setRead(rs.getBoolean(READ_FLAG));

        return notification;
    }
}
