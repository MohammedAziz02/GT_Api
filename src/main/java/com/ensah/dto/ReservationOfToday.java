package com.ensah.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationOfToday {

    private LocalDateTime today00;
    private LocalDateTime today23;
    private String terrainame;
}
