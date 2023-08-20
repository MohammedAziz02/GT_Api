package com.ensah.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationDate {
    String academicEmail;
    String terrain;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
