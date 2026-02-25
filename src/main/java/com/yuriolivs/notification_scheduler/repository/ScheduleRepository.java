package com.yuriolivs.notification_scheduler.repository;

import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduledNotification, UUID> {
    Optional<ScheduledNotification> findById(UUID id);
    List<ScheduledNotification> findAll();
    List<ScheduledNotification> findAllByDate(LocalDate date);
}
