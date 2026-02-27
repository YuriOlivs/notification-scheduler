package com.yuriolivs.notification_scheduler.controller;

import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleResponseDTO;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification_scheduler.service.SchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class SchedulerController {
    @Autowired
    private final SchedulerService service;

    private ResponseEntity<ScheduleResponseDTO> checkScheduleStatus(
            @RequestParam UUID id
    ) {
        ScheduledNotification response = service.checkScheduleStatus(id);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    private ResponseEntity<ScheduleResponseDTO> scheduleMessage(
            @RequestBody @Valid ScheduleRequestDTO dto
            ) {
        ScheduledNotification response = service.scheduleMessage(dto);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    private ResponseEntity<ScheduleResponseDTO> cancelSchedule(
            @RequestParam UUID id
    ) {
        ScheduledNotification response = service.cancelSchedule(id);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    private ResponseEntity<ScheduleResponseDTO> returnAllScheduledMessages() {
        return ResponseEntity.ok().build();
    }
}
