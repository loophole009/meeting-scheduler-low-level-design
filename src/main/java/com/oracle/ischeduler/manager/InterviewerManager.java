package com.oracle.ischeduler.manager;

import com.oracle.ischeduler.model.Interviewer;
import lombok.NonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InterviewerManager {

    private Set < Interviewer > interviewers;

    public InterviewerManager() {
        this.interviewers = new HashSet < > ();
    }

    /**
     * Create a new interviewer for meeting.
     *
     * @param interviewer Interviewer
     * @return
     */
    public boolean createInterviewer(@NonNull final Interviewer interviewer) {
        return this.interviewers.add(interviewer);
    }

    /**
     * Helper method to return interviewer in the natural sorting order by name.
     *
     * @return
     */
    public List < Interviewer > getInterviewers() {
        return this.interviewers.stream()
            .sorted(Comparator.comparing(Interviewer::getName))
            .collect(Collectors.toUnmodifiableList());
    }
}