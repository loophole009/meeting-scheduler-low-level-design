package com.oracle.ischeduler.manager;

import com.oracle.ischeduler.exception.InvalidTimeException;
import com.oracle.ischeduler.exception.NoInterviewerAvailableException;
import com.oracle.ischeduler.model.Meeting;
import com.oracle.ischeduler.model.Interviewer;
import com.oracle.ischeduler.strategy.BookingStrategy;
import com.oracle.ischeduler.strategy.TimeBookingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingManagerTest {

  BookingManager bookingManager;

  @BeforeEach
  void setUp() {

    Interviewer interviewer1 = new Interviewer("Picasso");
    Interviewer interviewer2 = new Interviewer("Albert");
    Interviewer interviewer3 = new Interviewer("Pamela");

    InterviewerManager interviewerManager = new InterviewerManager();
    interviewerManager.createInterviewer(interviewer1);
    interviewerManager.createInterviewer(interviewer2);
    interviewerManager.createInterviewer(interviewer3);

    BookingStrategy bookingStrategy = new TimeBookingStrategy();

    bookingManager = new BookingManager(interviewerManager, bookingStrategy);
  }

  @Test
  void test_bookRoomWithInvalidTime() {

    // Then.
    assertThrows(
        InvalidTimeException.class,
        () -> {
          // When.
          bookingManager.bookInterviewer(20, 5, "subject", "name", "email");
        });

    // Then.
    assertThrows(
        InvalidTimeException.class,
        () -> {
          // When.
          bookingManager.bookInterviewer(3, 2, "subject", "name", "email");
        });
  }

  @Test
  void test_bookRoomWheninterviewerIsAvailable() {
    // System.out.println(Bookings.getBookings().size());

    // When.
    Meeting meeting1 = bookingManager.bookInterviewer(3, 7, "subject", "name", "email");
    // Then.
    assertNotNull(meeting1);

    // When.
    Meeting meeting2 = bookingManager.bookInterviewer(1, 5, "subject", "name", "email");
    // Then.
    assertNotNull(meeting2);

    // When.
    Meeting meeting3 = bookingManager.bookInterviewer(2, 5, "subject", "name", "email");
    // Then.
    assertNotNull(meeting3);

    // System.out.println(Bookings.getBookings().size());
  }

  @Test
  void test_bookWhenWhenNointerviewerIsAvailable() {

    // System.out.println(Bookings.getBookings().size());

    // Given.
    Meeting meeting1 = bookingManager.bookInterviewer(1, 9, "subject", "name", "email");
    Meeting meeting2 = bookingManager.bookInterviewer(7, 12, "subject", "name", "email");
    Meeting meeting3 = bookingManager.bookInterviewer(3, 9, "subject", "name", "email");

    // At this point, no interviewer is available for the same time,
    // so an exception is thrown that no interviewer is available for that time.

    // Then.
    assertThrows(
        NoInterviewerAvailableException.class,
        () -> {
          // When.
          Meeting meeting4 = bookingManager.bookInterviewer(2, 12, "subject", "name", "email");
        });
  }

  @Test
  void test_bookingHistoryForInterviewer() {

    // Given.
    Meeting meeting1 = bookingManager.bookInterviewer(1, 3, "subject", "name", "email"); // Interviewer1 is booked.
    Meeting meeting2 =
        bookingManager.bookInterviewer(2, 7, "subject", "name", "email"); // Interviewer1 is booked since it's free for given time.
    Meeting meeting3 =
        bookingManager.bookInterviewer(5, 9, "subject", "name", "email"); // Interviewer1 is booked since it's free for given time.

    // Then.
    assertEquals(
        1,
        // When.
        bookingManager.getMeetingForInterviewer(meeting2.getInterviewer()).size());

    // Then.
    assertEquals(
        2,
        // When.
        bookingManager.getMeetingForInterviewer(meeting1.getInterviewer()).size());

    // Then.
    assertEquals(
        2,
        // When.
        bookingManager.getMeetingForInterviewer(meeting3.getInterviewer()).size());
  }

  @Test
  void test_AvailabiltyOfInterviewer() {

    // Given.
    Meeting meeting1 = bookingManager.bookInterviewer(1, 3, "subject", "name", "email"); // Interviewer1 is booked.
    Meeting meeting2 =
        bookingManager.bookInterviewer(4, 5, "subject", "name", "email"); // Interviewer1 is booked since it's free for given time.
    Meeting meeting3 =
        bookingManager.bookInterviewer(6, 9, "subject", "name", "email"); // Interviewer1 is booked since it's free for given time.

    // Then.
    assertEquals(
        2,
        // When.
        bookingManager.getAvailabilityOfInterviewer(meeting3.getInterviewer()).size());
  }
}

