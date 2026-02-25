package com.yuriolivs.notification_scheduler.domain.schedule.interfaces;

import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleResponseDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SchedulerServiceInterface {
    ScheduledNotification checkScheduleStatus(UUID id);
    ScheduledNotification scheduleMessage(ScheduleRequestDTO dto);
    List<ScheduledNotification> findAllScheduledMessages();
    List<ScheduledNotification> findAllScheduledMessagesByDate(LocalDate date);
    ScheduledNotification cancelSchedule(UUID id);
}
