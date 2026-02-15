package com.exed1on.websyshotel.dto.response;

import com.exed1on.websyshotel.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long roomId;
    private String roomName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private BigDecimal totalPrice;
    private BookingStatus status;
}
