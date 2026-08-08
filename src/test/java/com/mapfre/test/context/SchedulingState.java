package com.mapfre.test.context;

import com.mapfre.models.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public class SchedulingState {
    private LocalDate targetDate;
    private List<TimeSlot> unavailableSlots;
    private List<TimeSlot> availableSlots;
    private TimeSlot scheduledSlot;

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public List<TimeSlot> getUnavailableSlots() {
        return unavailableSlots;
    }

    public void setUnavailableSlots(List<TimeSlot> unavailableSlots) {
        this.unavailableSlots = unavailableSlots;
    }

    public List<TimeSlot> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<TimeSlot> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public TimeSlot getScheduledSlot() {
        return scheduledSlot;
    }

    public void setScheduledSlot(TimeSlot scheduledSlot) {
        this.scheduledSlot = scheduledSlot;
    }
}
