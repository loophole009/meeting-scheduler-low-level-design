package com.oracle.ischeduler.model;

import com.oracle.ischeduler.exception.InvalidTimeException;

// import java.util.Date;

import lombok.Getter;

@Getter
public class Interval {
    private int startTime;
    private int endTime;

    public Interval(int startTime, int endTime)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        if (this.startTime >= this.endTime) {
            throw new InvalidTimeException("Start time should be less than End time.");
          }
    }
}
