package com.marcs.app.notifications.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.marcs.app.notifications.client.domain.Notification;
import com.marcs.common.enums.NotificationType;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Mapper class to map a Store Object {@link Notification}
 * 
 * @author Sam Butler
 * @since December 25, 2021
 */
@Service
public class NotificationMapper implements RowMapper<Notification> {
    public static NotificationMapper NOTIFICATION_MAPPER = new NotificationMapper();

    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();

        notification.setId(rs.getInt("id"));
        notification.setType(NotificationType.valueOf(rs.getString("notification_type")));
        notification.setLinkId(rs.getInt("link_id"));
        notification.setInsertDate(LocalDate.parse(rs.getDate("insert_date").toString()));
        notification.setReceiverId(rs.getInt("receiver_id"));
        notification.setRead(rs.getBoolean("read_flag"));

        return notification;
    }
}
