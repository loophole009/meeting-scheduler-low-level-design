package com.oracle.ischeduler.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Meeting {

    private final String id;

    private Interval interval;
    private String subject;
    private Interviewer interviewer;
    private Candidate candidate;

    /**
     * Constructor to create a meeting for the given start, end time and room.
     *
     * @param interval Interval
     * @param interviewer Interviewer
     * @param candidate Candidate
     * @param subject String
     */
    public Meeting(Interval interval, Interviewer interviewer, Candidate candidate, String subject) {
        this.id = UUID.randomUUID().toString();
        this.subject = subject;
        this.interval = interval;
        this.interviewer = interviewer;
        this.candidate = candidate;
    }
}