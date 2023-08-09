package com.ensah.payload.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private LocalDateTime dateofreservation;
    private String terrain;
    private String againstwho;
}
