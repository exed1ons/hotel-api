package com.exed1on.websyshotel.service;

import com.exed1on.websyshotel.dto.request.CreateBookingRequest;
import com.exed1on.websyshotel.dto.response.BookingResponse;
import com.exed1on.websyshotel.entity.Booking;
import com.exed1on.websyshotel.entity.Room;
import com.exed1on.websyshotel.enums.BookingStatus;
import com.exed1on.websyshotel.exception.InvalidBookingException;
import com.exed1on.websyshotel.exception.ResourceNotFoundException;
import com.exed1on.websyshotel.mapper.BookingMapper;
import com.exed1on.websyshotel.repository.BookingRepository;
import com.exed1on.websyshotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(CreateBookingRequest request) {
        if (request.getCheckOut().isBefore(request.getCheckIn()) ||
            request.getCheckOut().isEqual(request.getCheckIn())) {
            throw new InvalidBookingException("Check-out date must be after check-in date");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new InvalidBookingException("Room is not available for the selected dates");
        }

        long numberOfNights = ChronoUnit.DAYS.between(request.getCheckIn(), request.getCheckOut());
        BigDecimal totalPrice = room.getPrice().multiply(BigDecimal.valueOf(numberOfNights));

        Booking booking = bookingMapper.toEntity(request);
        booking.setRoom(room);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.AWAITING_GUEST);

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse updateStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(updatedBooking);
    }
}
