package com.mapfre.models;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeSlot {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    public LocalTime getEndTime() {
        return end.toLocalTime();
    }

    @Override
    public String toString() {
        return start.toLocalTime() + " - " + end.toLocalTime();
    }
}
