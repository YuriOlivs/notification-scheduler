package com.yuriolivs.notification_scheduler.controller;

import com.yuriolivs.notification_scheduler.domain.schedule.dto.ScheduleResponseDTO;
import com.yuriolivs.notification_scheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {
    @Autowired
    private SchedulerService service;

    private ResponseEntity<ScheduleResponseDTO> checkScheduleStatus() {
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<ScheduleResponseDTO> scheduleMessage() {
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<ScheduleResponseDTO> cancelSchedule() {
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<ScheduleResponseDTO> returnAllScheduledMessages() {
        return ResponseEntity.ok().build();
    }
}
