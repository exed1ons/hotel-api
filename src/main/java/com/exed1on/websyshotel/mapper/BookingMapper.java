package com.exed1on.websyshotel.mapper;

import com.exed1on.websyshotel.dto.request.CreateBookingRequest;
import com.exed1on.websyshotel.dto.response.BookingResponse;
import com.exed1on.websyshotel.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getFirstName(),
                booking.getLastName(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getRoom().getId(),
                booking.getRoom().getName(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getTotalPrice(),
                booking.getStatus()
        );
    }

    public Booking toEntity(CreateBookingRequest request) {
        Booking booking = new Booking();
        booking.setFirstName(request.getFirstName());
        booking.setLastName(request.getLastName());
        booking.setEmail(request.getEmail());
        booking.setPhone(request.getPhone());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        return booking;
    }
}
