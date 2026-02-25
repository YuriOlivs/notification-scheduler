package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification_scheduler.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.notification_scheduler.domain.schedule.interfaces.SchedulerServiceInterface;
import com.yuriolivs.notification_scheduler.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SchedulerService implements SchedulerServiceInterface {
    @Autowired
    private final ScheduleRepository repo;

    @Autowired
    private final NotificationClient client;

    @Override
    public ScheduledNotification checkScheduleStatus(UUID id) {
        Optional<ScheduledNotification> existing = repo.findById(id);
        if (existing.isEmpty()) throw new HttpNotFoundException("Schedule not found.");

        return existing.get();
    }

    @Override
    public ScheduledNotification scheduleMessage(ScheduleRequestDTO dto) {
        Optional<ScheduledNotification> existing = repo.findByIdempotencyKey(dto.idempotencyKey());
        if (existing.isPresent())
            return existing.get();

        NotificationResponseDTO savedNotification = client
                .save(dto.notification());

        ScheduledNotification scheduledNotification = new ScheduledNotification(
                dto.idempotencyKey(),
                savedNotification.id(),
                savedNotification.recipient(),
                savedNotification.channel(),
                true,
                ScheduleStatus.SCHEDULED,
                dto.date(),
                dto.time()
        );

        return repo.save(scheduledNotification);
    }

    @Override
    public List<ScheduledNotification> findAllScheduledMessages() {
        return repo.findAll();
    }

    @Override
    public List<ScheduledNotification> findAllScheduledMessagesByDate(LocalDate date) {
        return repo.findAllByDate(date);
    }

    @Override
    public ScheduledNotification cancelSchedule(UUID id) {
        Optional<ScheduledNotification> existing = repo.findById(id);
        if (existing.isEmpty()) throw new HttpNotFoundException("Schedule not found.");

        ScheduledNotification notification = existing.get();
        notification.setIsActive(false);
        return repo.save(notification);
    }
}
