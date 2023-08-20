package com.ensah.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReservationDto {

    private LocalDateTime dateofmatch;
    private  String nameofterrain;
}
