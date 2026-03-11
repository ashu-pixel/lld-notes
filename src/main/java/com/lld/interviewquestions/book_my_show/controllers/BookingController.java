package com.lld.interviewquestions.book_my_show.controllers;

import java.util.List;
import java.util.UUID;

import com.lld.interviewquestions.book_my_show.entities.Booking;
import com.lld.interviewquestions.book_my_show.entities.Show;
import com.lld.interviewquestions.book_my_show.entities.User;
import com.lld.interviewquestions.book_my_show.service.BookingService;

public class BookingController {

    private final BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService();
    }

    public Booking createBooking(User user, Show show, List<Integer> seats) {
        Booking booking = bookingService.book(user, show, seats);
        return booking;
    }

    public Booking getBooking(UUID bookingId) {
        return bookingService.getBooking(bookingId);
    }

    public List<Booking> getBookingsForUser(User user) {
        return bookingService.getBookingsForUser(user);
    }
}
