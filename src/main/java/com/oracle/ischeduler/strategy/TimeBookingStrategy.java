package com.oracle.ischeduler.strategy;

import com.oracle.ischeduler.model.Interviewer;

/** Book meeting based on time comparison. */
public class TimeBookingStrategy implements BookingStrategy {

    @Override
    public Boolean book(final int startTime, final int endTime, final Interviewer interviewer) {

        Integer prev = interviewer.getBookedIntervals().floorKey(startTime);
        Integer next = interviewer.getBookedIntervals().ceilingKey(startTime);
        if ((prev == null || startTime >= interviewer.getBookedIntervals().get(prev)) && (next == null || endTime <= next)) {

            interviewer.getBookedIntervals().put(startTime, endTime);
            return true;
        }
        return false;
    }
}
