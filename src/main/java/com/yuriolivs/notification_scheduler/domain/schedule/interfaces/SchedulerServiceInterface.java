package com.yuriolivs.notification_scheduler.domain.schedule.interfaces;

public interface SchedulerServiceInterface {
    void checkScheduleStatus();
    void scheduleMessage();
    void searchScheduledMessages();
    void returnAllScheduledMessages();
    void cancelSchedule();
}
