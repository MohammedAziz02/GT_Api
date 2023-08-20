package com.ensah.service;
import com.ensah.dto.DeleteReservationDto;
import com.ensah.dto.ReservationDto;
import com.ensah.dto.ReservationOfToday;
import com.ensah.dto.UserReservationDate;
import com.ensah.payload.request.ReservationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;


public interface ReservationService {
    public ResponseEntity<ReservationDto> reservate(ReservationRequest reservation);
    public ResponseEntity<List<LocalDateTime>> findAllDatesofNocurrentUser(String email,String nameterrain);

    public ResponseEntity<List<LocalDateTime>> findAllDatesofcurrentUser(String email,String nameterrain);


    public ResponseEntity<Integer> deleteReservation(DeleteReservationDto deleteReservationDto);

    public ResponseEntity<Long> getReservationLoggedUserfortoday(UserReservationDate userReservationDate);

    public ResponseEntity<List<ReservationDto>> getMatchesofToday(ReservationOfToday today);

}

