package com.ensah.service;
import com.ensah.dto.ReservationDto;
import com.ensah.payload.request.ReservationRequest;
import org.springframework.http.ResponseEntity;


public interface ReservationService {
    public ResponseEntity<ReservationDto> reservate(ReservationRequest reservation);
}
