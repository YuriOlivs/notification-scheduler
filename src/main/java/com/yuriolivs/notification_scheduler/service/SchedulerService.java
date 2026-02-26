package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification.shared.domain.notification.NotificationMessage;
import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification_scheduler.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.notification_scheduler.domain.schedule.interfaces.SchedulerServiceInterface;
import com.yuriolivs.notification_scheduler.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class SchedulerService implements SchedulerServiceInterface {
    @Autowired
    private final ScheduleRepository repo;

    @Autowired
    private final NotificationClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
                dto.dateTime()
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

    public List<NotificationMessage> findNotificationsToBeProcessed(
            LocalDateTime startOfDay,
            LocalDateTime now
    ) {
        List<NotificationMessage> notificationsToBeSent = new ArrayList<>();

        List<ScheduledNotification> scheduledNotifications = repo
                .findByStatusAndIsActiveTrueAndScheduledAtBetween(
                        ScheduleStatus.SCHEDULED,
                        startOfDay,
                        now
                );

        List<UUID> ids = scheduledNotifications
                .stream()
                .map(ScheduledNotification::getNotificationId)
                .toList();

        SchedulePayloadRequestDTO request = new SchedulePayloadRequestDTO(ids);

        ScheduledPayloadResponseDTO response = client.getNotificationPayload(request);

        for (SchedulePayloadDTO payload : response.list()) {
            Map<String, String> map = objectMapper.readValue(
                    payload.payload(),
                    new TypeReference<Map<String, String>>() {}
            );

            NotificationMessage send = new NotificationMessage(
                    payload.id(),
                    NotificationPriority.SCHEDULED,
                    map,
                    payload.channel()
            );

            notificationsToBeSent.add(send);
        }

        return notificationsToBeSent;
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
