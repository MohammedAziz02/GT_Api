package com.ensah.dto;
import com.ensah.domain.Terrain;
import com.ensah.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDateTime date_reservation;
    private LocalDateTime date_match;
    private String against_who;

//    private Terrain terrain;

//    private User user;
}
