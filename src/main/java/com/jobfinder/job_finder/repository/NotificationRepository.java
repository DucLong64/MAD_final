package com.jobfinder.job_finder.repository;

import com.jobfinder.job_finder.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository  extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_IdOrderByCreatedAtDesc(Long userId);
    List<Notification> findByUser_IdAndStatusFalseOrderByCreatedAtDesc(Long userId);
}
