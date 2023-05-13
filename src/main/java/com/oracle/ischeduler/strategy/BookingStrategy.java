package com.oracle.ischeduler.strategy;

import com.oracle.ischeduler.model.Interviewer;

public interface BookingStrategy {
    Boolean book(int startTime, int endTime, Interviewer interviewer);
}