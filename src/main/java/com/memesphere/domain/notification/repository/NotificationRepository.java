package com.memesphere.domain.notification.repository;

import com.memesphere.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserId(Long userId);

    @Query("SELECT c.price FROM Notification n " +
            "JOIN n.memeCoin m " +
            "JOIN m.chartDataList c " +
            "WHERE n.id = :notificationId " +
            "ORDER BY c.createdAt DESC LIMIT 1")
    Double findLatestPriceByNotificationId(@Param("notificationId") Long notificationId);
}
