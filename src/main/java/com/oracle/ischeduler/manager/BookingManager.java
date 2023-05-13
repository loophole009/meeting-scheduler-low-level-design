package com.oracle.ischeduler.manager;

import com.oracle.ischeduler.exception.NoInterviewerAvailableException;
import com.oracle.ischeduler.model.Meeting;
import com.oracle.ischeduler.model.Candidate;
import com.oracle.ischeduler.model.Calender;
import com.oracle.ischeduler.model.Interval;
import com.oracle.ischeduler.model.Interviewer;
import com.oracle.ischeduler.strategy.BookingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BookingManager {

    private InterviewerManager interviewerManager;
    private BookingStrategy bookingStrategy;
    private Map<String, Calender> bookings;

    /**
     * Constructor for BookingManager class.
     *
     * @param interviewerManager InterviewerManager
     * @param bookingStrategy BookingStrategy
     */
    public BookingManager(InterviewerManager interviewerManager, BookingStrategy bookingStrategy) {
        this.bookings = new HashMap<>();
        this.interviewerManager = interviewerManager;
        this.bookingStrategy = bookingStrategy;
    }

    /**
     * Method to book interviewer for the given start and end time.
     *
     * @param startTime Integer
     * @param endTime Integer
     * @param subject String
     * @param candidateName String
     * @param candidateEmail String
     * @return
     */
    public Meeting bookInterviewer(int startTime, int endTime, String subject, String candidateName, String candidateEmail) {

        Meeting meeting = null;
        Interval interval = new Interval(startTime, endTime);
        Candidate candidate = new Candidate(candidateName, candidateEmail);

        List < Interviewer > interviewers = interviewerManager.getInterviewers();

        for (Interviewer interviewer: interviewers) {
            if (bookingStrategy.book(startTime, endTime, interviewer)) {
                meeting = new Meeting(interval, interviewer, candidate, subject);
                break;
            }
        }

        if (meeting == null) {
            throw new NoInterviewerAvailableException("No interviewer is available for the given time.");
        }

        if (!this.bookings.containsKey(meeting.getInterviewer().getId())) {
            this.bookings.put(meeting.getInterviewer().getId(), new Calender());
        }

        this.bookings.get(meeting.getInterviewer().getId()).getMeetings().add(meeting);

        return meeting;
    }

    /**
     * Fetch all meetings associated to a particular interviewer.
     *
     * @param interviewer Interviewer
     * @return
     */
    public List < Meeting > getMeetingForInterviewer(final Interviewer interviewer) {
        return this.bookings.getOrDefault(interviewer.getId(), new Calender()).getMeetings();
    }


    /**
     * Fetch availability of a particular interviewer.
     *
     * @param interviewer Interviewer
     * @return
     */
    public List < Interval > getAvailabilityOfInterviewer(final Interviewer interviewer) {

        List < Interval > res = new ArrayList < > ();
        List < Meeting > listMeeting = this.bookings.getOrDefault(interviewer.getId(), new Calender()).getMeetings();
        if (listMeeting == null || listMeeting.size() == 0) {
            return res;
        }

        Collections.sort(listMeeting, new Comparator < Meeting > () {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return m1.getInterval().getStartTime() == m2.getInterval().getStartTime() ? m1.getInterval().getEndTime() - m2.getInterval().getEndTime() : m1.getInterval().getStartTime() - m2.getInterval().getStartTime();
            }
        });

        PriorityQueue < Integer > endTime = new PriorityQueue < > (Collections.reverseOrder());
        endTime.add(listMeeting.get(0).getInterval().getEndTime());

        for (int i = 1; i < listMeeting.size(); i++) {
            if (endTime.size() > 0 && endTime.peek() < listMeeting.get(i).getInterval().getStartTime()) {
                res.add(new Interval(endTime.peek(), listMeeting.get(i).getInterval().getStartTime()));
            }
            endTime.add(listMeeting.get(i).getInterval().getEndTime());
        }
        return res;
    }
}