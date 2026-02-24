package com.yuriolivs.notification_scheduler.service;

import com.yuriolivs.notification_scheduler.domain.schedule.interfaces.SchedulerServiceInterface;
import com.yuriolivs.notification_scheduler.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerService implements SchedulerServiceInterface {
    @Autowired
    private final ScheduleRepository repo;

    @Override
    public void checkScheduleStatus() {

    }

    @Override
    public void scheduleMessage() {

    }

    @Override
    public void searchScheduledMessages() {

    }

    @Override
    public void returnAllScheduledMessages() {

    }

    @Override
    public void cancelSchedule() {

    }
}
